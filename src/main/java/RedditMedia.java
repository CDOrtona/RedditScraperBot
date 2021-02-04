import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;



public class RedditMedia extends TelegramLongPollingBot {

    private boolean flagSub;
    private boolean flagNum;
    private MediaInfo mediaInfo = new MediaInfo();

    public String getBotUsername() {
        return "RedditDailyPics_bot";
    }

    public String getBotToken() {
        return "1557782545:AAH-ron27SSMo4PWS9zUgXCOUHuAxb5e8KE";
    }

    public void onUpdateReceived(Update update) {

        Message message;

        if(update.hasMessage() && update.getMessage().hasText()){

            message = update.getMessage();
            if(!flagNum && flagSub){
                mediaInfo.setSubreddit(message.getText().toLowerCase());
                flagSub = false;
                try {
                    switchCase(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //debug
                System.out.println(message.getText());

            }
            else if(flagNum && !flagSub){
                mediaInfo.setNumImgs(message.getText());
                flagNum = false;
                try {
                    switchCase(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //debug
                System.out.println(message.getText());
            }

            else if(flagNum && flagSub){
                flagNum = false;
                flagSub = false;
                try {
                    switchCase(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //debug
                System.out.println(message.getText());
            }

            else {
                try {
                    switchCase(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    private void switchCase(Message message) throws IOException {
        switch (message.getText().toLowerCase()) {
            case "/start":
                String text = EmojiParser.parseToUnicode("Hey there :sunglasses:");
                lunchKeyboard(message, text);
                break;
            case "/help":
                sendMsg(message, "You can use me to get pictures from your favorite subreddit without having" +
                        " to open your Reddit app. :) ");
                break;
            case "media scraping" :
                mediaKeyboard(message);
                break;
            case "show images":
                showImages(mediaInfo, message);
                lunchKeyboard(message, EmojiParser.parseToUnicode(":point_down: Choose an option :point_down:"));
                break;
            case "contact me" :
                sendMsg(message, "If you have any question feel free to contact me here: RedditBot@cdmails.anonaddy.com");
                break;
            case "set subreddit":
                sendMsg(message, "Enter the subreddit name:");
                flagSub = true;
                break;
            case "number of images":
                sendMsg(message, "Enter the number of images to display:");
                flagNum = true;
                break;
        }
    }

    private void lunchKeyboard(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        setMainKeyboard(sendMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }


    private void setMainKeyboard(SendMessage sendMessage){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        firstRow.add("Media Scraping");
        firstRow.add("Download video");

        secondRow.add("Contact me");

        ArrayList<KeyboardRow> keyboardList = new ArrayList<>();
        keyboardList.add(firstRow);
        keyboardList.add(secondRow);

        replyKeyboardMarkup.setKeyboard(keyboardList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

    }

    private void sendMsg(Message message, String text){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }



    private void mediaKeyboard(Message message){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(EmojiParser.parseToUnicode(":black_small_square: Choose the Subreddit you want to scrape picures/videos from" + '\n'+
                                                            ":black_small_square: Choose the number of pictures/videos (100 max)" + '\n' + '\n' +
                                                            "Note that you'll not only be able to gather pictures and videos posted on the Subreddit " +
                                                            "you set above, but also posts."));

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();
        KeyboardRow thirdRow = new KeyboardRow();

        firstRow.add("Set Subreddit");

        secondRow.add("Number Of Images");

        thirdRow.add("Show Images");

        ArrayList<KeyboardRow> keyboardList = new ArrayList<>();
        keyboardList.add(firstRow);
        keyboardList.add(secondRow);
        keyboardList.add(thirdRow);

        replyKeyboardMarkup.setKeyboard(keyboardList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try{
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();

        }
    }

    private void showImages(MediaInfo mediaInfo, Message message) throws IOException {

        ArrayList<MediaInfo> mediaInfoList = GetRedditJson.getRedditPic(mediaInfo);

        if(mediaInfoList != null){

            System.out.println("The ArrayList of imageInfo has the following size: " + mediaInfoList.size());

            ArrayList<SendPhoto> sendPhotoList = new ArrayList<>();
            ArrayList<SendVideo> sendVideoList = new ArrayList<>();

            for(int i = 0; i < mediaInfoList.size(); i++){

                if(!mediaInfoList.get(i).hasVideo()){          //checks weather or not the retrieved post is a video

                    sendPhotoList.add(new SendPhoto());
                    sendPhotoList.get(i).setChatId(message.getChatId().toString());

                    sendPhotoList.get(i).setCaption("/r/" + mediaInfoList.get(i).getSubreddit().replaceAll("\"", "") + '\n' + "u/" + mediaInfoList.get(i).getAuthor().replaceAll("\"", "") + '\n' + '\n' +
                            mediaInfoList.get(i).getTitle().replaceAll("\"", "") + '\n' + '\n' +
                            mediaInfoList.get(i).getUpvotes() + "  " + EmojiParser.parseToUnicode(":thumbsup:") );


                    InputFile inputFile = new InputFile().setMedia(mediaInfoList.get(i).getUrl().replaceAll("\"", ""));
                    sendPhotoList.get(i).setPhoto(inputFile);

                    try{
                        execute(sendPhotoList.get(i));
                    } catch(TelegramApiException e) {
                        e.printStackTrace();
                        lunchKeyboard(message, EmojiParser.parseToUnicode(":warning: Couldn't retrieve this post"));
                    }

                } else if(mediaInfoList.get(i).hasVideo()) {

                    sendVideoList.add(new SendVideo());

                    sendVideoList.get(i).setChatId(message.getChatId().toString());
                    sendVideoList.get(i).setCaption("/r/" + mediaInfoList.get(i).getSubreddit().replaceAll("\"", "") + '\n' + "u/" + mediaInfoList.get(i).getAuthor().replaceAll("\"", "") + '\n' + '\n' +
                            mediaInfoList.get(i).getTitle().replaceAll("\"", "") + '\n' + '\n' +
                            mediaInfoList.get(i).getUpvotes() + "  " + EmojiParser.parseToUnicode(":thumbsup:") );

                    InputFile inputFile = new InputFile().setMedia(mediaInfoList.get(i).getUrl().replaceAll("\n", ""));
                    sendVideoList.get(i).setVideo(inputFile);

                    try {
                        execute(sendVideoList.get(i));
                    } catch(TelegramApiException e){
                        e.printStackTrace();
                        lunchKeyboard(message, EmojiParser.parseToUnicode(":warning: Couldn't retrieve this post"));
                    }

                }

            }
        }

        else {
            sendMsg(message, EmojiParser.parseToUnicode(":warning: invalid subreddit").toUpperCase());
        }

    }

}
