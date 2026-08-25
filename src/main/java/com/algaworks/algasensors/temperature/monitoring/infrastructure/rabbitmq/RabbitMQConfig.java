package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "temperature-monitoring.process-temperature.v1.q";

    //Esse método é para desserealizar o json dentro do objeto, irá transformar no objeto TemperatureLogData, caso contrario deria erro de desserialização
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    //Retiramos o bean que já tem no temperature-processing, deixamos o método como referencia
    // a essa exchange pra conseguir realizar a configuração do binding, caso tivesse o @Bean o serviço que consome os dados tentaria criar a exchange...
    //Uma exchange (ponto de troca) no RabbitMQ é um componente que recebe as mensagens enviadas pelos produtores (publishers) e decide para qual fila (queue) elas devem ser encaminhadas
    public FanoutExchange exchange() {
        return ExchangeBuilder.fanoutExchange("temperature-processing.temperature-received.v1.e").build();
    }

    //Aqui deixamos o @Bean pois o consumidor irá criar o Binding...
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange());
    }

}
