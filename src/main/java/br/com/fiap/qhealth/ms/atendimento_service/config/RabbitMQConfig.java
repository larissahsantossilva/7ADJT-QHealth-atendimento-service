package br.com.fiap.qhealth.ms.atendimento_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Constantes para a fila de NOVO ATENDIMENTO
    public static final String QUEUE_NOVO_ATENDIMENTO = "novo.atendimento.queue";
    public static final String ROUTING_KEY_NOVO_ATENDIMENTO = "novo.atendimento";

    // --- NOVAS CONSTANTES ADICIONADAS ---
    // Constantes para a fila de ATENDIMENTO REALIZADO
    public static final String QUEUE_ATENDIMENTO_UBS_1_USUAL = "atendimento.ubs-1-c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6";
    public static final String ROUTING_KEY_ATENDIMENTO_UBS_1_USUAL = "atendimento.ubs-1-c1b2a3d4-e5f6-a7b8-c9d0-a1b2c3d4e5f6";

    // --- NOVAS CONSTANTES ADICIONADAS ---
    // Constantes para a fila de ATENDIMENTO REALIZADO
    public static final String QUEUE_ATENDIMENTO_UBS_1_PREFERENCIAL = "atendimento.ubs-1-preferencial-d2c3b4e5-f6a7-b8c9-d0e1-b2c3d4e5f6a7";
    public static final String ROUTING_KEY_ATENDIMENTO_UBS_1_PREFERENCIAL = "atendimento.ubs-d2c3b4e5-f6a7-b8c9-d0e1-b2c3d4e5f6a7";

    // --- NOVAS CONSTANTES ADICIONADAS ---
    // Constantes para a fila de ATENDIMENTO REALIZADO
    public static final String QUEUE_ATENDIMENTO_UBS_2_USUAL = "atendimento.ubs-2-e3d4c5f6-a7b8-c9d0-e1f2-c3d4e5f6a7b8";
    public static final String ROUTING_KEY_ATENDIMENTO_UBS_2_USUAL = "atendimento.ubs-2-e3d4c5f6-a7b8-c9d0-e1f2-c3d4e5f6a7b8";

    // --- NOVAS CONSTANTES ADICIONADAS ---
    // Constantes para a fila de ATENDIMENTO REALIZADO
    public static final String QUEUE_ATENDIMENTO_UBS_2_PREFERENCIAL = "atendimento.ubs-2-preferencial-f4e5d6a7-b8c9-d0e1-f2a3-d4e5f6a7b8c9";
    public static final String ROUTING_KEY_ATENDIMENTO_UBS_2_PREFERENCIAL = "atendimento.ubs-2-preferencial-f4e5d6a7-b8c9-d0e1-f2a3-d4e5f6a7b8c9";

    // A Exchange pode ser a mesma para ambos, para centralizar o roteamento
    public static final String EXCHANGE_NAME = "atendimento.exchange";

    @Bean
    public Queue novoAtendimentoQueue() {
        return new Queue(QUEUE_NOVO_ATENDIMENTO, true);
    }

    @Bean
    public Queue atendimentoUbs1Usual() {
        return new Queue(QUEUE_ATENDIMENTO_UBS_1_USUAL, true);
    }

    @Bean
    public Queue atendimentoUbs1Preferencial() {
        return new Queue(QUEUE_ATENDIMENTO_UBS_1_PREFERENCIAL, true);
    }

    @Bean
    public Queue atendimentoUbs2Usual() {
        return new Queue(QUEUE_ATENDIMENTO_UBS_2_USUAL, true);
    }

    @Bean
    public Queue atendimentoUbs2Preferencial() {
        return new Queue(QUEUE_ATENDIMENTO_UBS_2_PREFERENCIAL, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding novoAtendimentoBinding(Queue novoAtendimentoQueue, TopicExchange exchange) {
        return BindingBuilder.bind(novoAtendimentoQueue).to(exchange).with(ROUTING_KEY_NOVO_ATENDIMENTO);
    }

    // --- CORREÇÃO AQUI ---
    @Bean
    public Binding atendimentoUbs1UsualBinding(Queue atendimentoUbs1Usual, TopicExchange exchange) {
        return BindingBuilder.bind(atendimentoUbs1Usual).to(exchange).with(ROUTING_KEY_ATENDIMENTO_UBS_1_USUAL);
    }

    // --- CORREÇÃO AQUI ---
    @Bean
    public Binding atendimentoUbs1PreferencialBinding(Queue atendimentoUbs1Preferencial, TopicExchange exchange) {
        return BindingBuilder.bind(atendimentoUbs1Preferencial).to(exchange).with(ROUTING_KEY_ATENDIMENTO_UBS_1_PREFERENCIAL);
    }

    // --- CORREÇÃO AQUI ---
    @Bean
    public Binding atendimentoUbs2UsualBinding(Queue atendimentoUbs2Usual, TopicExchange exchange) {
        return BindingBuilder.bind(atendimentoUbs2Usual).to(exchange).with(ROUTING_KEY_ATENDIMENTO_UBS_2_USUAL);
    }

    // --- CORREÇÃO AQUI ---
    @Bean
    public Binding atendimentoUbs2PreferencialBinding(Queue atendimentoUbs2Preferencial, TopicExchange exchange) {
        return BindingBuilder.bind(atendimentoUbs2Preferencial).to(exchange).with(ROUTING_KEY_ATENDIMENTO_UBS_2_PREFERENCIAL);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}