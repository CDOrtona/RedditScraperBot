import java.net.URL;

public class ImageInfo {

    private URL url;
    private String title;
    private String subreddit;
    private int upvotes;
    private Boolean is_video;

    public void setUrl(URL url){
        this.url = url;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setSubreddit(String subreddit){
        this.subreddit= subreddit;
    }

    public URL getUrl(){
        return this.url;
    }

    public String title(){
        return this.title;
    }

    public String getSubreddit(){
        return this.subreddit;
    }


}
