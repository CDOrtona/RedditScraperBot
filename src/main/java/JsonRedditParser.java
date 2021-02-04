import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

class JsonRedditParser {

    static ArrayList<MediaInfo> parseRedditJson(String jsonData, int numImgs) {

        ArrayList<MediaInfo> mediaInfoList = new ArrayList<>();

        //Json tree
        JsonElement jsonElement = com.google.gson.JsonParser.parseString(jsonData);

        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonObject data = jsonObject.getAsJsonObject("data");
            int subAvailable = data.get("dist").getAsInt();

            if (subAvailable != 0) {

                JsonArray children = data.getAsJsonArray("children");

                for (int i = 0; i < numImgs; i++) {

                    mediaInfoList.add(new MediaInfo());

                    JsonObject childrenObj = children.get(i).getAsJsonObject();
                    JsonObject data1 = childrenObj.getAsJsonObject("data");

                    mediaInfoList.get(i).setSubreddit(data1.get("subreddit").toString());
                    mediaInfoList.get(i).setUpvotes(data1.get("ups").getAsInt());
                    mediaInfoList.get(i).setAuthor(data1.get("author").toString());
                    mediaInfoList.get(i).setTitle(data1.get("title").toString());
                    mediaInfoList.get(i).setUrl(data1.get("url").toString());
                    mediaInfoList.get(i).setIsVideo(data1.get("is_video").getAsBoolean());


                    //print URL of the image retrieved
                    System.out.println(mediaInfoList.get(i).getUrl());
                    //this prints whether the media is a video or not
                    System.out.println("The media is a video? " + mediaInfoList.get(i).hasVideo());
                }
            }
            else {
                return null;
            }
        }

        return mediaInfoList;
    }

}
