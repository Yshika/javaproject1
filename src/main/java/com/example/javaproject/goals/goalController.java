package com.example.javaproject.goals;
import java.io.IOException;
import java.util.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class goalController {
    @Autowired
    private final GoalService service;

    Logger logger= LoggerFactory.getLogger(goalController.class);


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
        logger.trace("All Goals Accessed");
        return goal;
    }
    @Parameter(name = "fileName",description = "Name of the file",required = true)
    @Parameter(name = "fileContent",description = "Content of the file",required = true)
    @Operation(summary = "Create a new Goal",description ="Rest Endpoint that returns a object after creating a goal")

    @PostMapping(value = "/goals")
    public String createGoal(@RequestBody goals goal) {

        service.save(goal);
        return "Goal has been created Successfully";
    }
    @Operation(summary = "Update a content of Goal by GoalId")
    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.PUT)
    public String updateGoal(@RequestBody goals newgoal, @PathVariable Integer GoalId){
        System.out.println(newgoal);
        service.update(newgoal,GoalId);
        return "Goal Updated Successfully";
    }
    @Operation(summary = "Delete a content of Goal by GoalId")
    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.DELETE)
    public goals deleteGoal(@PathVariable Integer GoalId){
        goals goal=service.delete(GoalId);
        return goal;
    }
}
