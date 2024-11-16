package com.example.service;

import java.util.*;

import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
public class MessageService {



    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;



    public List<Message> getMessages(){
        return messageRepository.findAll();
    }

    public Message addMessage(Message message){
        messageRepository.save(message);
        return message;
    }


    public Message getMessageById(int messageId){
        List<Message> messageList = messageRepository.findAll();
        for(Message m: messageList){
            if(m.getMessageId().equals(messageId)){
                return m;
            }
        }
        return null;
    }

    public int deleteMessageById(int messageId){
        List<Message> messageList = messageRepository.findAll();

        for(Message m: messageList){
            if(m.getMessageId() == (messageId)){
                messageRepository.delete(m);
                return 1;
            }
        }
        return 0;
    }

    public void updateMessageById(Message message,String messageText){
        message.setMessageText(messageText);
        messageRepository.save(message);
    }

    public List<Message> getMessagesByUserId(int userId){
        List<Message> userMessages = new ArrayList<>();

        List<Message> currentMessages = messageRepository.findAll();

        for(Message m: currentMessages){
            if(m.getPostedBy() == userId){
                userMessages.add(m);
            }
        }
        return userMessages;
    }
}
