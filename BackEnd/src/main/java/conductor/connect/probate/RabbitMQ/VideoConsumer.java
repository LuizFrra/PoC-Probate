package conductor.connect.probate.RabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioVideoFormat;
import com.github.kiulian.downloader.model.formats.Format;
import conductor.connect.probate.DTO.VideoDTO;
import conductor.connect.probate.Models.Audio;
import conductor.connect.probate.Models.Status;
import conductor.connect.probate.Models.Video;
import conductor.connect.probate.Services.VideoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class VideoConsumer {

    private final ObjectMapper objectMapper;

    @Autowired
    public VideoService videoService;

    public VideoConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"youtube-video"})
    public void receivedMessageFromYouTubeVideo(String message) throws IOException, YoutubeException {

        System.out.println(message);

        VideoDTO videoDTO = objectMapper.readValue(message, VideoDTO.class);

        Optional<Video> videoOp = videoService.findById(videoDTO.url.split("=")[1]);

        Video video = videoOp.get();

        video.setStatus(Status.PROCESSING);
        videoService.update(video);

        YoutubeDownloader downloader = new YoutubeDownloader();

        String videoId = video.getId();

        YoutubeVideo youtubeVideo = downloader.getVideo(videoId);

        List<AudioVideoFormat> videoFormats = youtubeVideo.videoWithAudioFormats();

        File outPutDir = new File("L:\\Windows Folders\\Desktop\\PoC-Probate\\Videos");

        Format format = videoFormats.get(0);

        System.out.println("Download Started !");

        video.setStatus(Status.DOWNLOADING);
        videoService.update(video);

        File file = youtubeVideo.download(format, outPutDir);

        video.path = file.getPath();
        video.setStatus(Status.DONE);
        videoService.update(video);

        System.out.println("Download Ended !");
    }
}
