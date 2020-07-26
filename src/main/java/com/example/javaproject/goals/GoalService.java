package com.example.javaproject.goals;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GoalService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<goals> list() {
        String sql = "SELECT * FROM goals";
        List<goals> listSale = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(goals.class));
        return listSale;
    }

    public void save(goals goal) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("goals").usingColumns("GoalId", "title", "details","eta","createDate","updateDate");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(goal);
        insertActor.execute(param);
    }

    public goals get(Integer GoalId) {
        String sql = "SELECT * FROM Table goals WHERE GoalId = :GoalId";
        Object[] args = {GoalId};
        goals goal = jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(goals.class));
        return goal;
    }

    public goals delete(Integer GoalId) {
        String sql = "SELECT * FROM Table goals WHERE GoalId = :GoalId";
        Object[] args = {GoalId};
        goals goal = jdbcTemplate.queryForObject(sql, args,
                BeanPropertyRowMapper.newInstance(goals.class));

        String delsql = "DELETE FROM Table goals WHERE GoalId = :GoalId";
        jdbcTemplate.update(delsql, GoalId);
        return goal;
    }

    public void update(goals goal, Integer GoalId) {
        String sql = "UPDATE Table goals SET title=:title, details=:details, eta=:eta, createDate=createDate, updateDate=updateDate WHERE GoalId=:GoalId";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(goal);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        template.update(sql, param);
    }
}
