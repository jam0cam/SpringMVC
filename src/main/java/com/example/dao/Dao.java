package com.example.dao;

import com.example.controller.Util;
import com.example.model.Match;
import com.example.model.Person;
import com.example.model.Player;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Controller
@RequestMapping("/service")
public class Dao implements InitializingBean {
    private SqlMapClientTemplate sqlMapClientTemplate;

    @Autowired
    private SqlMapClient sqlMapClient;

    public Person getByResultId(String id) {
        return (Person)sqlMapClientTemplate.queryForObject("sql.getById", id);
    }

    public Player getPlayerById(String id) {
        Player p = (Player)sqlMapClientTemplate.queryForObject("sql.getPlayerById", id);
        try {
            p.setAvatarUrl("http://www.gravatar.com/avatar/" + Util.md5(p.getEmail()) + ".png");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return p;
    }

    public String insertRegistration(Player command) {
        sqlMapClientTemplate.insert("sql.insertRegistration", command);
        return command.getId();
    }


    public String insertMatch(Match command) {
        sqlMapClientTemplate.insert("sql.insertMatch", command);
        return command.getId();
    }

    public String insertPendingMatch(Match command) {
        sqlMapClientTemplate.insert("sql.insertPendingMatch", command);
        return command.getId();
    }


    public Player getByEmailAndPassword(String email, String password) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("password", password);

        Player rval = (Player)sqlMapClientTemplate.queryForObject("sql.getByEmailAndPassword", params);

        try {
            rval.setAvatarUrl("http://www.gravatar.com/avatar/" + Util.md5(rval.getEmail()) + ".png");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return rval;
    }

    public void declineMatch(String pendingId) {
        sqlMapClientTemplate.update("sql.declineMatch", pendingId);
    }

    public Player getByEmail(String email) {
        Player rval = (Player)sqlMapClientTemplate.queryForObject("sql.getByEmail", email);
        try {
            rval.setAvatarUrl("http://www.gravatar.com/avatar/" + Util.md5(rval.getEmail()) + ".png");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return rval;
    }

    public List<Player> getPlayers() {
        List<Player> list = (List<Player>)sqlMapClientTemplate.queryForList("sql.getPlayers");

        for (Player p : list) {
            try {
                p.setAvatarUrl("http://www.gravatar.com/avatar/" + Util.md5(p.getEmail()) + ".png");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return list;
    }

    public List<String> getNamesList() {
        return (List<String>)sqlMapClientTemplate.queryForList("sql.getNames");
    }

    public List<String> getEmailList() {
        return (List<String>)sqlMapClientTemplate.queryForList("sql.getEmails");
    }

    public List<Match> getMatchesByPlayer(String playerId) {
        return (List<Match>)sqlMapClientTemplate.queryForList("sql.getMatchesByPlayer", playerId);
    }

    public List<Match> getPendingMatchesByPlayer(String playerId) {
        return (List<Match>)sqlMapClientTemplate.queryForList("sql.getPendingMatchesByPlayer", playerId);
    }

    public List<Match> getMatchesByPlayerByDateDesc(String playerId) {
        return (List<Match>)sqlMapClientTemplate.queryForList("sql.getMatchesByPlayerByDateDesc", playerId);
    }

    public void afterPropertiesSet() throws Exception {
        this.sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
    }
}
