package conductor.connect.probate.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import conductor.connect.probate.DTO.BaseDTO;
import conductor.connect.probate.Models.BaseModel;
import conductor.connect.probate.Models.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public abstract class BaseService<D extends BaseDTO, M extends BaseModel>  {

    private final RabbitTemplate rabbitTemplate;

    private final String QUEUE_NAME;

    private final Logger logger;

    private final ObjectMapper objectMapper;

    Class T;

    private final CrudRepository<M, Object> repository;

    protected BaseService(RabbitTemplate rabbitTemplate, Class T, ObjectMapper objectMapper, String QUEUE_NAME, CrudRepository repository) {
        this.rabbitTemplate = rabbitTemplate;
        this.T = T;
        this.objectMapper = objectMapper;
        this.QUEUE_NAME = QUEUE_NAME;
        this.logger = LoggerFactory.getLogger(T);
        this.repository = repository;
    }


    public Optional<M> findById(Object id) {
        return repository.findById(id);
    }

    public M update(M data) {
        return repository.save(data);
    }

    public void publishMessage(D dto) {
        String serializedObject = "";
        try {
            serializedObject = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("Sending Message to RabbitMQ !" + T.getName());
        rabbitTemplate.convertAndSend("", QUEUE_NAME, serializedObject);
        logger.info("Message Published !" + T.getName());
    }

    public M create(D dto, Request request) {
        return repository.save(associateRequestAndModel(dto, request));
    }

    public abstract M associateRequestAndModel(D dto, Request request);

}
