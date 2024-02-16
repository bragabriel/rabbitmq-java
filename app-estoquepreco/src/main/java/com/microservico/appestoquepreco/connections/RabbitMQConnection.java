package com.microservico.appestoquepreco.connections;

import constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {

    private static final String NOME_EXCHANGE = "amq.direct";
    private AmqpAdmin amqpAdmin;

    //AmqpAdmin: Interface responsável por conectar o RabbitMQ e fazer a conexão das filas
    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    //Criar as Filas
    private Queue fila(String nomeFila){
        return new Queue(nomeFila, true, false, false);
    }

    //Criar as Exchanges
    private DirectExchange trocaDireta(){
        return new DirectExchange(NOME_EXCHANGE);
    }

    //Relacionar as Filas com as Exchanges
    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
    }

    @PostConstruct // -> Assim que o spring rodar o app, vai executar também o método
    private void adiciona(){ //Método para utilizar as funções e criar as filas
        Queue filaEstoque = this.fila(RabbitMQConstants.FILA_ESTOQUE);
        Queue filaPreco = this.fila(RabbitMQConstants.FILA_PRECO);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoEstoque = this.relacionamento(filaEstoque, troca);
        Binding ligacaoPreco = this.relacionamento(filaPreco, troca);

        //Criando as filas no RabbitMQ
        this.amqpAdmin.declareQueue(filaEstoque);
        this.amqpAdmin.declareQueue(filaPreco);

        //Criando a exchange
        this.amqpAdmin.declareExchange(troca);

        //Relacionamento
        this.amqpAdmin.declareBinding(ligacaoEstoque);
        this.amqpAdmin.declareBinding(ligacaoPreco);
    }
}
