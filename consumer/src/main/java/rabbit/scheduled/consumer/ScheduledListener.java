package rabbit.scheduled.consumer;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledListener {

//    @RabbitListener(queues = { "topic.queue" })
    @SuppressWarnings("unused")
    public void receiveMessageFromTopic(String message) {
        System.out.println("Received message from topic queue: " + message);
    }

    @Autowired
    SimpleMessageListenerContainer container;

    @Scheduled(cron = "0 0/1 * * * *")
    void stop() {
        if (container.isActive()) {
            System.out.println("Stopping the listener container");
            container.stop();
        }
    }

    @Scheduled(cron = "30 0/1 * * * *")
    void start() {
        if (!container.isActive()) {
            System.out.println("Starting the listener container");
            container.start();
        }
    }

}
