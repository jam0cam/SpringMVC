package com.example.controller;

import com.example.dao.Dao;
import com.example.model.LeaderBoardItem;
import com.example.model.Match;
import com.example.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jitse
 * Date: 12/4/13
 * Time: 10:25 AM
 */
@Controller
@RequestMapping("/tt")
public class TTController {
    @Autowired
    Dao dao;

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
