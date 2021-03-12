package rabbit.scheduled.consumer;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public Declarables topicBindings() {
        Queue topicQueue = new Queue("topic.queue", true);

        TopicExchange topicExchange = new TopicExchange("topic.exchange", true, false);

        return new Declarables(topicQueue, topicExchange,
                BindingBuilder
                .bind(topicQueue)
                .to(topicExchange)
                .with("*.important.*"));
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ScheduledListener listener) {
        return new MessageListenerAdapter(listener, "receiveMessageFromTopic");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("topic.queue");
        container.setMessageListener(listenerAdapter);
        return container;
    }


}
