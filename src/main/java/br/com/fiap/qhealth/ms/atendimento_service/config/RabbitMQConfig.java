package br.com.fiap.qhealth.ms.atendimento_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "fila-novo-atendimento";
    public static final String EXCHANGE_NAME = "exchange-principal";
    public static final String ROUTING_KEY = "atendimento.routing.key";

    @Bean
    public Queue queue() {
        // Declara uma fila durável (não se perde se o RabbitMQ reiniciar)
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        // Declara uma Topic Exchange, que é muito flexível para roteamento
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        // Cria a ligação (binding) entre a fila e a exchange usando uma routing key
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
