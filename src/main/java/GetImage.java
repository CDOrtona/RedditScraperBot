import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImage {

    public static void getRedditPic(String url) throws IOException {

        String finalUrl = null;

        if(url != null){
            finalUrl = "https://www.reddit.com/r/" + url + "top.json?limit=1";
        } else {
            finalUrl = "https://www.reddit.com/r/cat/top.json?limit=1";
        }

        URL redditUrl = new  URL(finalUrl);
        //I need an inputStream object, hence I use the .openStream() method for the URL class
        InputStreamReader inputStream = new InputStreamReader(redditUrl.openStream());
        //bufferedReader works with any inputstream, for example System.in, FileReader and so on
        //it'll then buffer that inputstream
        BufferedReader bufferedReader = new BufferedReader(inputStream);
        String line;
        StringBuffer stringBuffer = new StringBuffer();


        while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
            //debug
            System.out.println(line);
        }

        bufferedReader.close();

        jsonFetch(stringBuffer);


    }

    private static void jsonFetch(StringBuffer stringBuffer){

        JSONObject jsonObject = new JSONObject(stringBuffer);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray children = data.getJSONArray("children");
        JSONObject data1 = children.getJSONObject(2);
        


    }


}
