import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.ArrayList;



public class RedditPics extends TelegramLongPollingBot {

    private boolean flagSub;
    private boolean flagNum;
    private ImageInfo imageInfo = new ImageInfo();

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
                imageInfo.setSubreddit(message.getText().toLowerCase());
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
                imageInfo.setNumImgs(message.getText());
                flagNum = false;
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
            case "settings" :
                settingKeyboard(message);
                break;
            case "show images":
                showImages(imageInfo, message);
                break;
            case "contact me" :
                sendMsg(message, "If you have any question feel free to contact me here: RedditBot@cdmails.anonaddy.com");
                break;
            case "help":
                sendMsg(message, "You can use me to get pictures from your favorite subreddit without having" +
                        " to open your Reddit app. :) ");
                break;
            case "set subreddit":
                sendMsg(message, "Enter the subreddit name:");
                flagSub = true;
                break;
            case "number of images":
                sendMsg(message, "Enter the number of images to display:");
                flagNum = true;
                break;
            default:
                String text = "Hey there,  make sure to" + '\n' +
                        "check the \"Settings\" before using the bot." + '\n' +'\n' +
                        "You'll be able to set your subreddit preference there." ;

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
    }


    private void setMainKeyboard(SendMessage sendMessage){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        firstRow.add("Show Images");
        firstRow.add("Settings");

        secondRow.add("Contact me");
        secondRow.add("Help");

        ArrayList<KeyboardRow> keyboardList = new ArrayList<KeyboardRow>();
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



    private void settingKeyboard(Message message){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Settings:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow firstRow = new KeyboardRow();
        KeyboardRow secondRow = new KeyboardRow();

        firstRow.add("Set subreddit");

        secondRow.add("Number Of Images");

        ArrayList<KeyboardRow> keyboardList = new ArrayList<KeyboardRow>();
        keyboardList.add(firstRow);
        keyboardList.add(secondRow);

        replyKeyboardMarkup.setKeyboard(keyboardList);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        try{
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }


    }

    private void showImages(ImageInfo imageInfo, Message message) throws IOException {

        ArrayList<ImageInfo> imageInfoList = GetImage.getRedditPic(imageInfo);

        //debug
        System.out.println("The ArrayList of imageInfo has the following size: " + imageInfoList.size());

        ArrayList<SendPhoto> sendPhotoList = new ArrayList<>();

        for(int i = 0; i < imageInfoList.size(); i++){


            if(!imageInfoList.get(i).getIsVideo()){          //checks weather or not the retrieved post is a video, if it is then it's not sent as a message on telegram.
                                                            //The bot supports only pictures at the moment, support for videos will be added in the near future
                sendPhotoList.add(new SendPhoto());
                sendPhotoList.get(i).setChatId(message.getChatId().toString());

                sendPhotoList.get(i).setCaption("/r/" + imageInfoList.get(i).getSubreddit().replaceAll("\"", "") + '\n' + "u/" + imageInfoList.get(i).getAuthor().replaceAll("\"", "") + '\n' + '\n' +
                        imageInfoList.get(i).getTitle().replaceAll("\"", "") + '\n' + '\n' +
                        imageInfoList.get(i).getUpvotes() + "  " + EmojiParser.parseToUnicode(":thumbsup:") );


                InputFile inputFile = new InputFile().setMedia(imageInfoList.get(i).getUrl().replaceAll("\"", ""));
                sendPhotoList.get(i).setPhoto(inputFile);

                try{
                    execute(sendPhotoList.get(i));
                } catch(TelegramApiException e) {
                    e.printStackTrace();
                }

            } else {
                //debug: returns the url which isn't a picture
                System.out.println(imageInfoList.get(i).getUrl());
            }


        }

    }



}
