package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQInitializer {
//Precisamos do Initializar para que seja criado a fila e o binding....

    private final RabbitAdmin rabbitAdmin;

    //Anotação para que esse método seja executado assim que a classe for criada
    @PostConstruct
    public void init() {
        rabbitAdmin.initialize();
    }
}
