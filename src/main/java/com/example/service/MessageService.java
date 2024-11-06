package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;

@Service
public class MessageService {
    


    @Autowired
    public MessageService(){};



    public List<Message> getMessages(){
        List<Message> messages = new ArrayList<>();
        return messages;
    }

    
}
