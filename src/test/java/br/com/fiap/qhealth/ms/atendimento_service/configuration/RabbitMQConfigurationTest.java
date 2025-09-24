package br.com.fiap.qhealth.ms.atendimento_service.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testes unitários para a classe RabbitMQConfiguration.
 * Garante que todos os beans de Fila, Exchange e Binding são criados corretamente.
 */
@SpringBootTest(classes = RabbitMQConfiguration.class) // Carrega apenas esta classe de configuração
class RabbitMQConfigurationTest {

    @Autowired
    private ApplicationContext context; // Permite-nos inspecionar os beans criados

    @Test
    void deveCarregarContextoComConfiguracaoRabbitMQ() {
        // Este teste passa simplesmente se a aplicação conseguir carregar o contexto
        // com a classe de configuração, o que valida a sintaxe e a injeção de dependências.
        assertThat(context).isNotNull();
    }

    @Test
    void deveCriarBeanParaTodasAsFilas() {
        // Verifica se cada bean de Queue foi criado
        assertThat(context.getBean("novoAtendimentoQueue", Queue.class)).isNotNull();
        assertThat(context.getBean("atendimentoUbs1Usual", Queue.class)).isNotNull();
        assertThat(context.getBean("atendimentoUbs1Preferencial", Queue.class)).isNotNull();
        assertThat(context.getBean("atendimentoUbs2Usual", Queue.class)).isNotNull();
        assertThat(context.getBean("atendimentoUbs2Preferencial", Queue.class)).isNotNull();
    }

    @Test
    void deveConfigurarNomesCorretosParaAsFilas() {
        Queue ubs1Usual = context.getBean("atendimentoUbs1Usual", Queue.class);
        assertThat(ubs1Usual.getName()).isEqualTo(RabbitMQConfiguration.QUEUE_ATENDIMENTO_UBS_1_USUAL);

        Queue ubs1Preferencial = context.getBean("atendimentoUbs1Preferencial", Queue.class);
        assertThat(ubs1Preferencial.getName()).isEqualTo(RabbitMQConfiguration.QUEUE_ATENDIMENTO_UBS_1_PREFERENCIAL);

        Queue ubs2Usual = context.getBean("atendimentoUbs2Usual", Queue.class);
        assertThat(ubs2Usual.getName()).isEqualTo(RabbitMQConfiguration.QUEUE_ATENDIMENTO_UBS_2_USUAL);

        Queue ubs2Preferencial = context.getBean("atendimentoUbs2Preferencial", Queue.class);
        assertThat(ubs2Preferencial.getName()).isEqualTo(RabbitMQConfiguration.QUEUE_ATENDIMENTO_UBS_2_PREFERENCIAL);
    }

    @Test
    void deveCriarBeanParaExchange() {
        TopicExchange exchange = context.getBean("exchange", TopicExchange.class);
        assertThat(exchange).isNotNull();
        assertThat(exchange.getName()).isEqualTo(RabbitMQConfiguration.EXCHANGE_NAME);
    }

    @Test
    void deveCriarTodosOsBindings() {
        // Verifica se cada bean de Binding foi criado
        assertThat(context.getBean("atendimentoUbs1UsualBinding")).isInstanceOf(Binding.class);
        assertThat(context.getBean("atendimentoUbs1PreferencialBinding")).isInstanceOf(Binding.class);
        assertThat(context.getBean("atendimentoUbs2UsualBinding")).isInstanceOf(Binding.class);
        assertThat(context.getBean("atendimentoUbs2PreferencialBinding")).isInstanceOf(Binding.class);
    }

    @Test
    void deveCriarBeanParaMessageConverter() {
        // Verifica se o conversor de mensagens JSON foi criado
        assertThat(context.getBean("jsonMessageConverter")).isInstanceOf(MessageConverter.class);
    }
}