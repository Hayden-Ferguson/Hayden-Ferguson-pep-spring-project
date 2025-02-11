package com.example.service;

import com.example.repository.MessageRepository;
import com.example.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.io.IOException;

@Service
@Transactional
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }


    public Message addMessage(Message message){
        if (message.getMessageText().length()<1 || message.getMessageText().length()>255) return null;
        return messageRepository.save(message);
    }


    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }


    public Message getMessageById(Integer messageId){
        return messageRepository.findMessageByMessageId(messageId);
    }


    public Boolean deleteMessage(int messageId){
        try{ //threw exceptions before debugging
            if (getMessageById(messageId)!=null){
                messageRepository.deleteByMessageId(messageId);
                return true;
            }
            return false;
        }
        catch(Exception e) {
            return false;
        }
    }

    public Message updateMessage(int messageId, String messageText){
        Message message = getMessageById(messageId);
        if (message!=null && messageText.length()>0 && messageText.length()<256){
            message.setMessageText(messageText);
            return messageRepository.save(message);
        }
        return null;
    }
    
}
