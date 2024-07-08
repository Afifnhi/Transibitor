package transibitorpack;

import javafx.scene.media.Media;

import java.io.File;

public class VideoModel {
    private File videoFile;
    private Media media;

    public VideoModel(File videoFile) {
        this.videoFile = videoFile;
        this.media = new Media(videoFile.toURI().toString());
    }

    public File getVideoFile() {
        return videoFile;
    }

    public Media getMedia() {
        return media;
    }

    public void setVideoFile(File videoFile) {
        this.videoFile = videoFile;
        this.media = new Media(videoFile.toURI().toString());
    }
}
