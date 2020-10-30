package conductor.connect.probate.Controllers;

import conductor.connect.probate.DTO.RequestDTO;
import conductor.connect.probate.DTO.VideoDTO;
import conductor.connect.probate.Models.BaseModel;
import conductor.connect.probate.Models.Video;
import conductor.connect.probate.Services.RequestService;
import conductor.connect.probate.Services.VideoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/video")
public class VideoController extends BaseController<VideoDTO, Video> {

    private VideoService videoService;

    private RequestService requestService;

    public VideoController(VideoService videoService, RequestService requestService) {
        super(videoService, VideoController.class, requestService);
        this.videoService = videoService;
        this.requestService = requestService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> getContent(@PathVariable String id) throws IOException {
        RequestDTO request = requestService.findById(Integer.parseInt(id));
        if(request != null && request.data != null) {

            BaseModel video = request.data.stream().findFirst().get();
            Path path = Paths.get(video.getPath());
            byte[] videoBytes = Files.readAllBytes(path);
            String contentType = Files.probeContentType(path);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("content-type", contentType);
            httpHeaders.setContentLength(videoBytes.length);

            return new ResponseEntity<byte[]>(videoBytes, httpHeaders, HttpStatus.OK);
        }
        return null;
    }
}
