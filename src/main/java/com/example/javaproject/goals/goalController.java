package com.example.javaproject.goals;
import java.util.*;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(
        info = @Info(
                title = "API for Goal",
                version = "1.0",
                description = "CRUD Goal API",
                license = @License(name = "Apache 2.0", url = "http://foo.bar"),
                contact = @Contact(url = "#", name = "Yshika", email = "yshika.agarwal@geminisolutions.com")
        )
)
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
        logger.info("Request received to fetch All Goals...");
        List<goals> listGoals = service.list();
        logger.info("All Goals fetched, returning data");
        return listGoals;
    }
    @Operation(summary = "Get goal by goalId",
            responses = {
                    @ApiResponse(description = "The goal",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = goals.class))),
                    @ApiResponse(responseCode = "400", description = "Goal not found")})
    @GetMapping("/goals/{GoalId}")
    public ResponseEntity<Object> getGoalById(@PathVariable Integer GoalId){
        logger.info("Request received to fetch goal with goalId "+GoalId);
        goals goal=service.get(GoalId);
        if(goal==null){
            return new ResponseEntity<Object>("Goal doesn't exist", HttpStatus.OK);
        }
        logger.info("Fetched Goal Accessed");
        return new ResponseEntity<Object>(goal,HttpStatus.OK);
    }
    @Parameter(name = "GoalName",description = "Id of the Goal",required = true)
    @Parameter(name = "GoalContent",description = "Content of the Goal",required = true)
    @Operation(summary = "Create a new Goal",description ="Rest Endpoint that returns a object after creating a goal")

    @PostMapping(value = "/goals")
    public ResponseEntity<Object>  createGoal(@RequestBody goals goal) {
        logger.info("Request received to create a new goal with goalId "+goal.getGoalId());
        if(service.check(goal.getGoalId())==null) {
            service.save(goal);
            logger.info("Goal Created Successfully");
            return new ResponseEntity<Object>("Goal Created successfully", new HttpHeaders(), HttpStatus.CREATED);
        } else {
            logger.info("Goal Already Exists, Operation Unsuccessful");
            return new ResponseEntity<Object>("Goal Creation Unsuccessful", new HttpHeaders(), HttpStatus.OK);
        }

    }
    @Operation(summary = "Update a content of Goal by GoalId", responses = {
            @ApiResponse(
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = goals.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
            @ApiResponse(responseCode = "404", description = "Goal not found"),
            @ApiResponse(responseCode = "405", description = "Validation exception") })
    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.PUT)
    public ResponseEntity<Object>  updateGoal(@RequestBody goals newgoal, @PathVariable Integer GoalId){
        logger.info("Request received to update goal having goalId "+GoalId);
        if(service.get(GoalId)!=null) {
            service.update(newgoal,GoalId);
            logger.info("Goal Updated Successfully");
            return new ResponseEntity<Object>("Goal Updated Successfully",HttpStatus.OK);
        } else {
            logger.info("Operation Unsuccessful");
            return new ResponseEntity<Object>("Goal Update Unsuccessful", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
    }
    @Operation(summary = "Delete a content of Goal by GoalId")
    @RequestMapping(value = "/goals/{GoalId}",method = RequestMethod.DELETE)
    public ResponseEntity<Object>  deleteGoal(@PathVariable Integer GoalId){
        logger.info("Request received to delete goal");
        if(service.get(GoalId)!=null) {
            goals goal= service.delete(GoalId);
            logger.info("Goal Deleted Successfully");
            return new ResponseEntity<Object>(goal,HttpStatus.OK);
        } else {
            logger.info("Operation Unsuccessful");
            return new ResponseEntity<Object>("Goal Deletion Unsuccessful", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        }
}
