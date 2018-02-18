package amazaingkoolapps.gmail.com.vr;

/**
 * Created by Dell on 2/18/2018.
 */

public class Recording {
    private String Uri, fileName;
    private boolean isPlaying = false;


    public Recording(String uri, String fileName, boolean isPlaying) {
        Uri = uri;
        this.fileName = fileName;
        this.isPlaying = isPlaying;
    }

    public String getUri() {
        return Uri;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing){
        this.isPlaying = playing;
    }
}
