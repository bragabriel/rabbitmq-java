package com.microsservico.consumidorestoque.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.RabbitMQConstants;
import dto.EstoqueDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EstoqueConsumer {

  //Ao startar a aplicação, o RabbitMQ vai ficar em Listener esperando uma mensagem/fila
  @RabbitListener(queues = RabbitMQConstants.FILA_ESTOQUE)
  private void consumidor(String mensagem) throws JsonProcessingException, InterruptedException {
    EstoqueDto estoqueDto = new ObjectMapper().readValue(mensagem, EstoqueDto.class);

    System.out.println(estoqueDto.codigoProduto);
    System.out.println(estoqueDto.quantidade);
    System.out.println("------------------------------------");

    //Teste para Handler de Error
    //throw new IllegalArgumentException("Argumento inválido!");
  }
}
