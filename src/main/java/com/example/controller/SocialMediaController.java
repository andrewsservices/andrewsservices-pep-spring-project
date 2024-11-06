package com.example.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.Message;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@RequestMapping("messages")
public class SocialMediaController {

    private MessageService messageService;

    public SocialMediaController(MessageService messageService){
        this.messageService = messageService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<Message> getMessagesList(){
        return messageService.getMessages();
    }

}
