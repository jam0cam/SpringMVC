package com.example.dao;

import com.example.model.Match;
import com.example.model.Person;
import com.example.model.Player;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return (Player)sqlMapClientTemplate.queryForObject("sql.getPlayerById", id);
    }

    public String insertRegistration(Player command) {
        sqlMapClientTemplate.insert("sql.insertRegistration", command);
        return command.getId();
    }


    public String insertMatch(Match command) {
        sqlMapClientTemplate.insert("sql.insertMatch", command);
        return command.getId();
    }


    public Player getByEmailAndPassword(String email, String password) {
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("password", password);

        Player rval = (Player)sqlMapClientTemplate.queryForObject("sql.getByEmailAndPassword", params);

        return rval;
    }

    public Player getByEmail(String email) {
        return (Player)sqlMapClientTemplate.queryForObject("sql.getByEmail", email);
    }

    public List<Player> getPlayers() {
        return (List<Player>)sqlMapClientTemplate.queryForList("sql.getPlayers");
    }

    public List<Match> getMatchesByPlayer(String playerId) {
        return (List<Match>)sqlMapClientTemplate.queryForList("sql.getMatchesByPlayer", playerId);
    }

    public List<Match> getMatchesByPlayerByDateDesc(String playerId) {
        return (List<Match>)sqlMapClientTemplate.queryForList("sql.getMatchesByPlayerByDateDesc", playerId);
    }


    public void afterPropertiesSet() throws Exception {
        this.sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
    }
}
