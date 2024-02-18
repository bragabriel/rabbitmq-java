package com.microsservico.consumidorestoque.consumer;

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;

//Define uma Estratégia de Erro Customizada
public class CustomErrorStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
    @Override
    public boolean isFatal(Throwable t){ // Verifica se é uma exceção fatal
        System.out.println(new String(((ListenerExecutionFailedException) t).getFailedMessage().getBody()));

        return t.getCause() instanceof IllegalArgumentException;
    }
}
