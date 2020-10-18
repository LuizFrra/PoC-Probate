package conductor.connect.probate.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import conductor.connect.probate.DTO.AudioDTO;
import conductor.connect.probate.Models.Audio;
import conductor.connect.probate.Models.Request;
import conductor.connect.probate.Models.Status;
import conductor.connect.probate.Repositories.AudioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AudioService extends BaseService<AudioDTO, Audio> {

    private final Logger logger = LoggerFactory.getLogger(AudioService.class);

    public AudioService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, AudioRepository audioRepository) {
        super(rabbitTemplate, AudioService.class, objectMapper, "youtube-audio", audioRepository);
    }

    @Override
    public Audio associateRequestAndModel(AudioDTO dto, Request request) {
        return new Audio(dto.url.split("=")[1], Status.PUBLISHED, "", request);
    }

}
