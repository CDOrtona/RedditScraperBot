import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

class GetRedditJson {

    static ArrayList<MediaInfo> getRedditPic(MediaInfo mediaInfo) throws IOException {

        String finalUrl;
        String url = mediaInfo.getSubreddit();
        int numImgs = mediaInfo.getNumImgs();

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

        return JsonRedditParser.parseRedditJson(stringBuffer.toString(), numImgs);


    }
}
