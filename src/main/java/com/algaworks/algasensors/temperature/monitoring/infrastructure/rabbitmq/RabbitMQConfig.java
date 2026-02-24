package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable("temperature-monitoring.process-temperature.v1.q").build();
    }

    //Retiramos o bean que já tem no temperature-processing, deixamos o método como referencia
    // a essa exchange pra conseguir realizar a configuração do binding
    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange("temperature-processing.tempertura-received.v1.e").build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange());
    }

}
