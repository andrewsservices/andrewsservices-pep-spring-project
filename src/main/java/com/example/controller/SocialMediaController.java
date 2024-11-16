package com.example.controller;

import java.util.*;

import javax.naming.AuthenticationException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;


    public SocialMediaController(){}

    @RequestMapping(path="messages", method = RequestMethod.GET)
    public @ResponseBody List<Message> getMessagesList(){
        return messageService.getMessages();
    }

    @RequestMapping(path="accounts", method = RequestMethod.GET)
    public @ResponseBody List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @PostMapping(path="register")
    public @ResponseBody ResponseEntity<Account> register(@RequestBody Account account){
        String userName = account.getUsername();
        String password = account.getPassword();
        List<Account> accounts = accountService.getAccounts();

        for(Account a: accounts){
            if(a.getUsername().equals(userName) || password.length() < 4){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<>(accountService.register(userName,password),HttpStatus.OK);
    }



    @RequestMapping(path="login",method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account) throws ResourceNotFoundException{
        String userName = account.getUsername();
        String password = account.getPassword();

        return new ResponseEntity<>(accountService.login(userName,password),HttpStatus.OK);
    }


    @PostMapping("messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        List<Account> accounts = accountService.getAccounts();
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            for(Account a: accounts){
                System.out.println(message.getPostedBy() + " " + a.getAccountId());
                if(message.getPostedBy().equals(a.getAccountId())){
                    
                    return new ResponseEntity<>(messageService.addMessage(message),HttpStatus.OK);
                }

            }
        }


        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




    @RequestMapping(path="messages/{messageId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int messageId){

        return new ResponseEntity<>(messageService.getMessageById(messageId),HttpStatus.OK);
    }



    @DeleteMapping(path="messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
        int affectedRows = messageService.deleteMessageById(messageId);
        if(affectedRows == 0){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.ok(affectedRows);
    }




    @PatchMapping(path="messages/{messageId}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message message){

        List<Message> messages = messageService.getMessages();
        String messageText = message.getMessageText();
        if(messageText.isEmpty() || messageText.length() > 255){
            return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
        }
        for(Message m: messages){
            if(m.getMessageId() == messageId){
                messageService.updateMessageById(m,messageText);
                return new ResponseEntity<>(1,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(path="accounts/{accountId}/messages", method = RequestMethod.GET)
    public @ResponseBody List<Message> getMessagesByUserId(@PathVariable int accountId){

        return messageService.getMessagesByUserId(accountId);
    }


}



