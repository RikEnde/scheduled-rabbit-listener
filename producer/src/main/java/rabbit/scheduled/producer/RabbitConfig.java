package rabbit.scheduled.producer;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
}
