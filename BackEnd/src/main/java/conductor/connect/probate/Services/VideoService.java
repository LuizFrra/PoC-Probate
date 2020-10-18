package conductor.connect.probate.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import conductor.connect.probate.DTO.VideoDTO;
import conductor.connect.probate.Models.Request;
import conductor.connect.probate.Models.Status;
import conductor.connect.probate.Models.Video;
import conductor.connect.probate.Repositories.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VideoService extends BaseService<VideoDTO, Video> {

    private final Logger logger = LoggerFactory.getLogger(VideoService.class);

    public VideoService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, VideoRepository videoRepository) {
        super(rabbitTemplate, VideoService.class, objectMapper, "youtube-video", videoRepository);
    }

    @Override
    public Video associateRequestAndModel(VideoDTO dto, Request request) {
        return new Video(dto.url.split("=")[1], Status.PUBLISHED,"", request);
    }
}
