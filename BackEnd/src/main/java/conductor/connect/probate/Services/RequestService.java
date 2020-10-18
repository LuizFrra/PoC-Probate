package conductor.connect.probate.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import conductor.connect.probate.DTO.RequestDTO;
import conductor.connect.probate.Models.Audio;
import conductor.connect.probate.Models.Request;
import conductor.connect.probate.Models.Video;
import conductor.connect.probate.Repositories.RequestRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    protected RequestService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    public Request create() {
        Request request = new Request();
        Request requestSaved = this.requestRepository.save(request);
        return requestSaved;
    }

    @Transactional
    public RequestDTO findById(int id) {
        Optional<Request> requestOp = requestRepository.findById(id);

        Request request;

        if(requestOp.isPresent())
            request = requestOp.get();
        else
            return null;

        Set<Video> videos = request.getVideos().stream().collect(Collectors.toSet());
        Set<Audio> audios = request.getAudios().stream().collect(Collectors.toSet());

        RequestDTO requestDTO = new RequestDTO();

        if(videos.size() != 0)
            requestDTO.data = videos;
        else
            requestDTO.data = audios;

        requestDTO.requestId = request.id;
        return requestDTO;
    }

}
