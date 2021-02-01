public class ImageInfo {

    private String url;
    private String subreddit;
    private String author;
    private String title;
    private int upvotes;
    private Boolean is_video;
    private int numImgs;

    void setUrl(String url){
        this.url = url;
    }

    void setTitle(String title){
        this.title = title;
    }

    void setSubreddit(String subreddit){
        this.subreddit= subreddit;
    }

    void setAuthor (String author){
        this.author = author;
    }
    void setUpvotes(int upvotes){
        this.upvotes =  upvotes;
    }
    void setNumImgs(String numImgs){
        this.numImgs = Integer.parseInt(numImgs);
    }

    String getUrl(){
        return this.url;
    }

    String getTitle(){
        return this.title;
    }

    String getSubreddit(){
        return this.subreddit;
    }

    String getAuthor() {
        return this.author;
    }

    int getNumImgs(){
        return this.numImgs;
    }

    int getUpvotes(){
        return this.upvotes;
    }


}
