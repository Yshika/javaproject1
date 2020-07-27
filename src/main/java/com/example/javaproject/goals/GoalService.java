package com.example.javaproject.goals;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GoalService {
    Logger logger= LoggerFactory.getLogger(goalController.class);
    //an instance of JDBC template for interacting with database

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //method to fetch all goals
    public List<goals> list() {
        logger.info("Running sql query");
        String sql = "SELECT * FROM goals";
        List<goals> listGoal = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(goals.class));
        return listGoal;
    }
    //method to add a goal in database
    public void save(goals goal){
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("goals").usingColumns("GoalId", "title", "details","eta","createDate","updateDate");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(goal);
        insertActor.execute(param);
    }
    //method to fetch a goal having corresponding GoalId
    public goals get(Integer GoalId) throws EmptyResultDataAccessException,GoalsNotFoundException{
        try {
            String sql = "SELECT * FROM goals WHERE GoalId = ?";
            Object[] args = {GoalId};
            logger.info("Running sql query");
            goals goal = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(goals.class));
            return goal;
        }
        catch (Exception e){
            logger.info("Goal with id {} doesn't exists",GoalId);
            throw new GoalsNotFoundException("Goal Details Doesn't Exits");
        }
    }
    //method to check if a goal exists
    public goals check(Integer GoalId) {
        try{
            String sql = "SELECT * FROM goals WHERE GoalId = ?";
            Object[] args = {GoalId};
            logger.info("Running sql query to check if goal exists");
            goals goal = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(goals.class));
            return goal;
        }
        catch(Exception e){
            return null;
        }
    }
    //method to delete a goal corresponding to a GoalId
    public goals delete(Integer GoalId) throws GoalsNotFoundException {
        try{
            String sql = "SELECT * FROM goals WHERE GoalId = ?";
            Object[] args = {GoalId};
            goals goal = jdbcTemplate.queryForObject(sql, args,
                    BeanPropertyRowMapper.newInstance(goals.class));

            String del = "DELETE FROM goals WHERE GoalId = ?";
            logger.info("Running sql query");
            jdbcTemplate.update(del, GoalId);
            return goal;
        }
        catch (Exception e){
            logger.info("Goal with id {} doesn't exists",GoalId);
            throw new GoalsNotFoundException("Goal Details Doesn't Exits");
        }
    }
    //method to update a goal with corresponding GoalId
    public void update(goals goal, Integer GoalId) throws GoalsNotFoundException {
        try{
            String sql = "UPDATE goals SET title=:title, details=:details, eta=:eta, createDate=createDate, updateDate=updateDate WHERE GoalId=:GoalId";
            BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(goal);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
            logger.info("Running sql query");
            template.update(sql, param);
        }
        catch(Exception e){
            logger.info("Goal with id {} doesn't exists",GoalId);
            throw new GoalsNotFoundException("Goal Details Can't Be Updated");
        }
    }
}