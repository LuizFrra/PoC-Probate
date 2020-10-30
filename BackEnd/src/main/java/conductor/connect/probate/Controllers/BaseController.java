package conductor.connect.probate.Controllers;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.github.kiulian.downloader.model.formats.Format;
import conductor.connect.probate.DTO.BaseDTO;
import conductor.connect.probate.Models.BaseModel;
import conductor.connect.probate.Models.Request;
import conductor.connect.probate.Services.BaseService;
import conductor.connect.probate.Services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class BaseController<D extends BaseDTO, M extends BaseModel> {

    private final BaseService<D, M> baseService;

    private Logger logger;

    private final RequestService requestService;

    public BaseController(BaseService baseService, Class T, RequestService requestService) {
        this.baseService = baseService;
        this.logger = LoggerFactory.getLogger(T);
        this.requestService = requestService;
    }

    @PostMapping
    public Mono<D> downloadContent(@RequestBody D dto) {
        logger.info("Creating Request !");
        Request request = requestService.create();
        logger.info("Message Received By Controller !");
        dto.requestId = request.id;
        baseService.create(dto, request);
        baseService.publishMessage(dto);
        logger.info("Message Published !");
        return Mono.just(dto);
    }

    @GetMapping("/semrabbit/{id}")
    public Mono<String> downloadContentSemRabbit(@PathVariable String id) throws YoutubeException, IOException {

        YoutubeDownloader downloader = new YoutubeDownloader();

        YoutubeVideo youtubeVideo = downloader.getVideo(id);

        List<AudioVideoFormat> audioVideoFormats = youtubeVideo.videoWithAudioFormats();

        File outPutDir = new File("L:\\Windows Folders\\Desktop\\PoC-Probate\\Audios");

        Format format = audioVideoFormats.get(0);

        System.out.println("Download Started !");

        File file = youtubeVideo.download(format, outPutDir);

        System.out.println("Download Ended !");

        return Mono.just(file.getAbsolutePath());
    }

}
