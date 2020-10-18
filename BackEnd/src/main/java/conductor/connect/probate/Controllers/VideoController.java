package conductor.connect.probate.Controllers;

import conductor.connect.probate.DTO.VideoDTO;
import conductor.connect.probate.Models.Video;
import conductor.connect.probate.Services.RequestService;
import conductor.connect.probate.Services.VideoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/video")
public class VideoController extends BaseController<VideoDTO, Video> {

    public VideoController(VideoService videoService, RequestService requestService) {
        super(videoService, VideoController.class, requestService);
    }

}
