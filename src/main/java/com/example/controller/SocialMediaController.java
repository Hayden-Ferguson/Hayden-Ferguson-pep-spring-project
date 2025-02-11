package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

//import io.javalin.Javalin;
//import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.SocialMediaApp;
import static org.springframework.boot.SpringApplication.run;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
//@Controller
//@RequestMapping
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }


    @PostMapping(value = "/register")
    public ResponseEntity postAccount(@RequestBody Account account){
        if(accountService.checkAccountExists(account)) return ResponseEntity.status(409).body("Username already exists");
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            return ResponseEntity.status(200).body(addedAccount);
        }else{
            return ResponseEntity.status(400).body("Bad Request");
        }
    }


    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody Account account){
        Account actualAccount = accountService.login(account);
        if(actualAccount!=null){
            return ResponseEntity.status(200).body(actualAccount);
        }else{
            return ResponseEntity.status(401).body("Invalid Login");
        }
    }


    @PostMapping(value = "/messages")
    public ResponseEntity postMessage(@RequestBody Message message){
        if(accountService.checkId(message.getPostedBy())) { //should be done in service layer if possible
            Message actualMessage = messageService.addMessage(message);
            if(actualMessage!=null){
                return ResponseEntity.status(200).body(actualMessage);
            }else{
                return ResponseEntity.status(400).body("Bad Request");
            }
        }
        return ResponseEntity.status(400).body("Bad Request");
    }


    @GetMapping(value = "/messages")
    public ResponseEntity getAllMessage(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }


    @GetMapping(value = "/messages/{message_id}")
    public ResponseEntity getMessageById(@PathVariable Integer message_id){
        Message message = messageService.getMessageById(message_id);
        return ResponseEntity.status(200).body(message);
    }


    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity deleteMessage(@PathVariable Integer message_id){
        if(messageService.deleteMessage(message_id)){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }

}
