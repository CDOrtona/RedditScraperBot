import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class JsonRedditParser {

    static ArrayList<ImageInfo> parseRedditJson(String url, int numImgs) {

        ArrayList<ImageInfo> imageInfoList = new ArrayList<>();

        //Json tree
        JsonElement jsonElement = com.google.gson.JsonParser.parseString(url);

        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            JsonArray children = data.getAsJsonArray("children");

            for (int i = 0; i < numImgs; i++) {

                imageInfoList.add(new ImageInfo());

                JsonObject childrenObj = children.get(i).getAsJsonObject();
                JsonObject data1 = childrenObj.getAsJsonObject("data");

                imageInfoList.get(i).setSubreddit(data1.get("subreddit").toString());
                imageInfoList.get(i).setUpvotes(data1.get("ups").getAsInt());
                imageInfoList.get(i).setAuthor(data1.get("author").toString());
                imageInfoList.get(i).setTitle(data1.get("title").toString());
                imageInfoList.get(i).setUrl(data1.get("url").toString());
                imageInfoList.get(i).setIsVideo(data1.get("is_video").getAsBoolean());

                //print URL of the image retrieved
                System.out.println(imageInfoList.get(i).getUrl());
            }


        }

        return imageInfoList;
    }

}
