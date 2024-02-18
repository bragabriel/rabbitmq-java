package com.microsservico.consumidorestoque.config;


import com.microsservico.consumidorestoque.consumer.CustomErrorStrategy;
import com.microsservico.consumidorestoque.exceptions.TratamentoErrorHandler;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class RabbitMQConfig {
    @Bean
    public RabbitListenerContainerFactory<DirectMessageListenerContainer> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {

        //Criando um listener
        DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();

        //Conexão com o RabbitMQ
        factory.setConnectionFactory(connectionFactory);

        //Confirmação de leitura da mensagems de forma automática (ACK)
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);

        //Setando o Prefetch Count
        factory.setPrefetchCount(4);

        //Definindo nosso ErrorHandler
        factory.setErrorHandler(new TratamentoErrorHandler());

        //Segunda versão de tratamento de erro
        //factory.setErrorHandler(errorHandler());

        return factory;
    }

    @Bean
    public FatalExceptionStrategy customErrorStrategy(){
        return new CustomErrorStrategy();
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(customErrorStrategy());
    }
}
