package conductor.connect.probate.RabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import com.github.kiulian.downloader.model.formats.Format;
import conductor.connect.probate.DTO.AudioDTO;
import conductor.connect.probate.Models.Audio;
import conductor.connect.probate.Models.Status;
import conductor.connect.probate.Services.AudioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class AudioConsumer {

    private final ObjectMapper objectMapper;

    public AudioConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public AudioService audioService;

    @RabbitListener(queues = {"youtube-audio"}, concurrency = "1")
    public void receivedMessageFromYouTubeAudio(String message) throws IOException, YoutubeException {

        System.out.println(message);

        AudioDTO audioDTO = objectMapper.readValue(message, AudioDTO.class);

        Optional<Audio> audioOp = audioService.findById(audioDTO.url.split("=")[1]);

        Audio audio = audioOp.get();

        audio.setStatus(Status.PROCESSING);
        audioService.update(audio);

        YoutubeDownloader downloader = new YoutubeDownloader();

        String audioId = audio.getId();

        YoutubeVideo youtubeVideo = downloader.getVideo(audioId);

        List<AudioFormat> audioFormats = youtubeVideo.audioFormats();

        File outPutDir = new File("L:\\Windows Folders\\Desktop\\PoC-Probate\\Audios");

        Format format = audioFormats.get(0);

        System.out.println("Download Started !");

        audio.setStatus(Status.DOWNLOADING);
        audioService.update(audio);

        File file = youtubeVideo.download(format, outPutDir);

        audio.path = file.getPath();
        audio.setStatus(Status.DONE);
        audioService.update(audio);

        System.out.println("Download Ended !");

    }
}
