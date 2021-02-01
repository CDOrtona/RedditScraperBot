import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class GetImage {

    public static <jsonObject> void getRedditPic(ImageInfo imageInfo) throws IOException {

        String finalUrl = null;
        String url = imageInfo.getSubreddit();
        int numImgs = imageInfo.getNumImgs();

        if (url != null) {
            finalUrl = "https://www.reddit.com/r/" + url + "/new.json?limit=" + numImgs;
        } else {
            finalUrl = "https://www.reddit.com/r/cats/new.json?limit=1";
        }

        URL redditUrl = new URL(finalUrl);
        URLConnection urlConnection = redditUrl.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        urlConnection.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)urlConnection.getContent()));

        String line;
        StringBuffer stringBuffer = new StringBuffer();

        while ((line = bufferedReader.readLine()) != null){
            stringBuffer.append(line);
        }

        //Json tree
        JsonElement jsonElement = JsonParser.parseString(stringBuffer.toString());

        if(jsonElement.isJsonObject()){
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray children = data.getAsJsonArray("children");
            JsonObject childrenObj = children.get(0).getAsJsonObject();
            JsonObject data1 = childrenObj.getAsJsonObject("data");
            System.out.println(data1.get("subreddit"));

            imageInfo.setSubreddit(data1.get("subreddit").toString());
            imageInfo.setUpvotes(data1.get("ups").getAsInt());
            imageInfo.setAuthor(data1.get("author").toString());
            imageInfo.setTitle(data1.get("title").toString());
            imageInfo.setUrl(data1.get("url").toString());

            //print URL of the image retrieved
            System.out.println(imageInfo.getUrl());

        }
    }
}
