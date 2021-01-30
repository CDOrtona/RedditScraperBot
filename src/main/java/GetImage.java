import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GetImage {

    public static void getRedditPic(ImageInfo imageInfo) throws IOException {

        String finalUrl = null;
        String url = imageInfo.getSubreddit();
        int numImgs = imageInfo.getNumImgs();

        if (url != null) {
            finalUrl = "https://www.reddit.com/r/" + url + "/top.json?limit=" + numImgs;
        } else {
            finalUrl = "https://www.reddit.com/r/cats/top.json?limit=1";
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
            //debug
            System.out.println(line);
        }

        //JsonElement jsonElement = JsonParser.parseString(stringBuffer.toString());

        /*JSONObject jsonObject = new JSONObject(stringBuffer);
        JSONObject data = jsonObject.getJSONObject("listing");
        JSONArray children = data.getJSONArray("children");
        JSONObject data1 = children.getJSONObject(2);

        imageInfo.setUrl(data1.getString("URL"));
        imageInfo.setTitle(data1.getString("title"));
        imageInfo.setAuthor(data1.getString("author"));
        imageInfo.setUpvotes(data1.getInt("score"));

        //debug
        //System.out.println(data1.getString("URL")); */




    }
}
