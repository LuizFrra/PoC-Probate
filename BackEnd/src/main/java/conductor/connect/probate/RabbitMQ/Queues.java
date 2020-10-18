package conductor.connect.probate.RabbitMQ;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Queues {

    @Bean
    public Queue audioQueue() {
        return new Queue("youtube-audio", true);
    }

    @Bean
    public Queue videoQueue() {
        return new Queue("youtube-video", true);
    }
}
