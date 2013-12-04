package com.example.dao;

import com.example.model.Match;
import com.example.model.Person;
import com.example.model.Player;
import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import java.util.List;

/**
 */
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

    public List<Player> getPlayers() {
        return (List<Player>)sqlMapClientTemplate.queryForList("sql.getPlayers");
    }

    public List<Match> getMatchesByPlayer(String playerId) {
        return (List<Match>)sqlMapClientTemplate.queryForList("sql.getMatchesByPlayer", playerId);
    }

    public void afterPropertiesSet() throws Exception {
        this.sqlMapClientTemplate = new SqlMapClientTemplate(sqlMapClient);
    }
}
