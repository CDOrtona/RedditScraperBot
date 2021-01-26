import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImage {

    public static void getRedditPic() throws IOException {

        URL redditUrl = new  URL("https://www.reddit.com/r/cat/top.json?limit=1");
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


    }
}
