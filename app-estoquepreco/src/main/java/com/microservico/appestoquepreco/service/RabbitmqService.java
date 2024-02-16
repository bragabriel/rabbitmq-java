package com.microservico.appestoquepreco.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    //Envia mensagem ao RabbitMQ
    public void enviaMensagem(String nomeFila, Object mensagem){ //Serializar para poder utilizar por qualquer microserviço (java, node, .net...)
        try{
            String mensagemJson = this.objectMapper.writeValueAsString(mensagem); //transformando em json
            this.rabbitTemplate.convertAndSend(nomeFila, mensagemJson);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
