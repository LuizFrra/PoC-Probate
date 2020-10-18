package conductor.connect.probate.Controllers;

import conductor.connect.probate.DTO.AudioDTO;
import conductor.connect.probate.Models.Audio;
import conductor.connect.probate.Services.AudioService;
import conductor.connect.probate.Services.RequestService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audio")
public class AudioController extends BaseController<AudioDTO, Audio> {

    public AudioController(AudioService audioService, RequestService requestService) {
        super(audioService, AudioController.class, requestService);
    }

}
