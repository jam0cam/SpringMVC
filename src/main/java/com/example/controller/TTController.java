package com.example.controller;

import com.example.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/player/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Player getPlayerById (@PathVariable("id") String id) {
        Player rval = dao.getPlayerById(id);

        return rval;
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public @ResponseBody
    List<Player> getPlayers () {
        List<Player> rval = dao.getPlayers();

        //blanking out password because we don't need it. This is for autocomplete only

        for (Player p : rval) {
            p.setPassword("");
        }

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

            stats.setGameWins(stats.getGameWins() + match.getP1Score());
            stats.setGameLosses(stats.getGameLosses() + match.getP2Score());

            if (match.getP1Score() > match.getP2Score()){
                stats.setMatchWins(stats.getMatchWins() + 1);
            } else {
                stats.setMatchLosses(stats.getMatchLosses() + 1);
            }
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
