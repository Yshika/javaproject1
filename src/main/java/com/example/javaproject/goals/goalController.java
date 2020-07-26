package com.example.javaproject.goals;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class goalController {
    @Autowired
    private final GoalService service;

    @RequestMapping("/goals")
    public String index(){
        return "Greetings!";
    }
    public goalController(GoalService service) {
        this.service = service;
    }

    @GetMapping("/goals")
    public List getAllGoals() {
        List<goals> listGoals = service.list();
        return listGoals;
    }
    @GetMapping("/goals/{GoalId}")
    public goals getGoalById(@PathVariable Integer GoalId){
        goals goal=service.get(GoalId);
        return goal;
    }

    @PostMapping(value = "/goals")
    public String createGoal(@RequestBody goals goal){
        service.save(goal);
        return "Goal has been created Successfully";
    }

    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.PUT)
    public String updateGoal(@RequestBody goals newgoal, @PathVariable Integer GoalId){
        System.out.println(newgoal);
        service.update(newgoal,GoalId);
        return "Goal Updated Successfully";
    }

    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.DELETE)
    public goals deleteGoal(@PathVariable Integer GoalId){
        goals goal=service.delete(GoalId);
        return goal;
    }
}
