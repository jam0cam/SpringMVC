package com.example.controller;

import com.example.exception.EmailExistsException;
import com.example.exception.InvalidUserNameOrPasswordException;
import com.example.model.*;
import com.example.postageapp.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: jitse
 * Date: 12/4/13
 * Time: 10:25 AM
 */
@Controller
@RequestMapping("/tt")
public class TTController extends BaseController{

    @Autowired
    private MailSender mailSender;

    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Player getPlayerById (@PathVariable("id") String id) throws NoSuchAlgorithmException {
        Player rval = dao.getPlayerById(id);
        return rval;
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public @ResponseBody
    List<Player> getPlayers () throws NoSuchAlgorithmException {
        List<Player> rval = dao.getPlayers();
        return rval;
    }

    @RequestMapping(value= "/saveMatch", method=RequestMethod.POST)
    @ResponseBody
    public String saveMatch(@RequestBody Match match) {

        if (StringUtils.hasText(match.getDateString())){
            match.setDate(new Date(match.getDateString()));
        }

        dao.insertMatch(match);
        return match.getId();
    }

    @RequestMapping(value= "/signin", method=RequestMethod.POST)
    @ResponseBody
    public Player signin(@RequestBody RegisterUser player) {
        Player p = dao.getByEmailAndPassword(player.getEmail(), player.getPassword());
        if (p==null) {
            throw new InvalidUserNameOrPasswordException();
        } else {
            return p;
        }
    }

    @RequestMapping(value= "/register", method=RequestMethod.POST)
    @ResponseBody
    public Player register(@RequestBody RegisterUser player) {
        Player p = dao.getByEmail(player.getEmail());
        if (p==null) {
            Player newPlayer = player.toPlayer();
            String id = dao.insertRegistration(newPlayer);
            newPlayer.setId(id);
            return newPlayer;
        } else {
            throw new EmailExistsException();
        }
    }

    @RequestMapping(value= "/confirmMatch", method=RequestMethod.POST)
    @ResponseBody
    public HttpResponse confirmMatch(@RequestBody ConfirmMatch confirmation) {
        Match pendingMatch = dao.getPendingMatch(confirmation.getPendingId());
        if (pendingMatch == null) {
            return new HttpResponse(200, "OK");
        }

        if (confirmation.isConfirmed()){
            dao.deletePending(confirmation.getPendingId());
            dao.insertMatch(pendingMatch);
        } else {
            //on a decline, we send an email to player "1" notifying that player 2 declined.
            Player player1 = dao.getPlayerById(pendingMatch.getP1().getId());
            Player player2 = dao.getPlayerById(pendingMatch.getP2().getId());

            if (player1 != null && player2 != null) {
                mailSender.sendMail(player1.getEmail(), "match declined", "Your match with " + player2.getName() + " has been declined.");
            }
            dao.declineMatch(confirmation.getPendingId());
        }

        return new HttpResponse(200, "OK");
    }

    @RequestMapping(value = "/pending/player/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Match> getPendingMatches(@PathVariable("id") String id) {
        List<Match> matches = dao.getPendingMatchesByPlayer(id);
        return matches;
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ProfileCommand getProfile(@PathVariable("id") String id) {
        ProfileCommand rval = new ProfileCommand();

        Player player = dao.getPlayerById(id);

        List<Match> matches = dao.getMatchesByPlayerByDateDesc(id);

        Stats stats = new Stats();

        for (Match match : matches) {
            if (!match.getP1().getId().equals(id)) {
                //we want to swap the info and always make p1 = player passed in
                match.swapPlayer();
            }
            match.setDateString(dateFormatter.format(match.getDate()));

            if (match.getP1Score() > match.getP2Score()){
                match.setStatus("W");
            } else {
                match.setStatus("L");
            }

            stats.setGameWins(stats.getGameWins() + match.getP1Score());
            stats.setGameLosses(stats.getGameLosses() + match.getP2Score());

            if (match.getP1Score() > match.getP2Score()){
                stats.setMatchWins(stats.getMatchWins() + 1);
            } else {
                stats.setMatchLosses(stats.getMatchLosses() + 1);
            }


        }

        stats.setTotalGames(stats.getGameWins() + stats.getGameLosses());
        stats.setTotalMatches(stats.getMatchWins() + stats.getMatchLosses());

        if (stats.getTotalGames() > 0) {
            stats.setGameWinPercentage(stats.getGameWins() * 100 /stats.getTotalGames());
        }

        if (stats.getTotalMatches() > 0) {
            stats.setMatchWinPercentage(stats.getMatchWins() * 100 / stats.getTotalMatches());
        }

        rval.setMatches(matches);
        rval.setStats(stats);
        rval.setPlayer(player);
        return rval;
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    @ResponseBody
    public List<LeaderBoardItem> getLeaderBoard() {
        List<LeaderBoardItem> rval = new ArrayList<LeaderBoardItem>();

        List<Player> players = dao.getPlayers();

        //for each player, get their matches
        for (Player p : players){
            List<Match> matches = dao.getMatchesByPlayer(p.getId());

            int wins = 0;
            int losses = 0;
            for (Match match : matches) {
                if (match.getP1().getId().equals(p.getId())) {
                    wins += match.getP1Score();
                    losses += match.getP2Score();
                } else {
                    wins += match.getP2Score();
                    losses += match.getP1Score();
                }
            }

            LeaderBoardItem item = new LeaderBoardItem();
            item.setMatchLosses(losses);
            item.setMatchWins(wins);
            item.setPlayer(p);
            item.setWinningPercentage(((double)(wins))/(losses+wins));

            insertLeaderBoardItemCorrectly(rval, item);
        }

        return rval;
    }



    /**
     * It inserts the item into the correct spot to produce a decreasing order of winning percentage
     * @param items
     * @param item
     */
    private void insertLeaderBoardItemCorrectly(List<LeaderBoardItem> items, LeaderBoardItem item) {

        if (items == null) return;

        if (items.isEmpty()) {
            items.add(item);
        } else {
            for (int i=0; i<items.size(); i++) {
                LeaderBoardItem lbi = items.get(i);
                if (lbi.getWinningPercentage() < item.getWinningPercentage()) {
                    items.add(i, item);
                    return;
                }
            }

            //if it gets here that means the item we are adding has the lowest percentage
            items.add(item);
        }
    }
}
