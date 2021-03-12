package rabbit.scheduled.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScheduledProducer {

    private final RabbitTemplate template;

    AtomicInteger counter = new AtomicInteger(0);

    ScheduledProducer(RabbitTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedRate = 2000)
    void ping() {
        template.convertAndSend("topic.exchange", "very.important.key", "Message #" + counter.incrementAndGet());
    }
}
