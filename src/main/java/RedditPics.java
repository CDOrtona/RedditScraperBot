import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.io.IOException;
import java.rmi.MarshalException;
import java.util.ArrayList;


public class RedditPics extends TelegramLongPollingBot {

    private boolean flagSub;
    private boolean flagNum;

    public String getBotUsername() {
        return "RedditDailyPics_bot";
    }

    public String getBotToken() {
        return "1557782545:AAH-ron27SSMo4PWS9zUgXCOUHuAxb5e8KE";
    }

    public void onUpdateReceived(Update update) {
//adsfasfasd
        
        Message message;
        String choosenSub = null;
        int numImages = 0;

        ImageInfo imageInfo = new ImageInfo(); //implementare assegnazione sub e numeroimaggini all'oggetto

        if(update.hasMessage() && update.getMessage().hasText()){

            message = update.getMessage();
            if(!flagNum && flagSub){
                choosenSub = message.getText();
                flagSub = false;
                switchCase(message);

                //debug
                System.out.println(choosenSub);

            }
            else if(flagNum && !flagSub){
                numImages = Integer.parseInt(message.getText());
                flagNum = false;
                switchCase(message);

                //debug
                System.out.println(numImages);
            }

            else
                switchCase(message);

        }

    }


    private void switchCase(Message message){
        switch (message.getText().toLowerCase()) {
            case "settings" :
                settingKeyboard(message);
                break;
            case "show images":

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
                sendMsg(message, "Enter the number of images to display");
                flagNum = true;
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

    private void showImages(String choosenSub) throws IOException {

        GetImage.getRedditPic(choosenSub);
        SendPhoto photo = new SendPhoto();
    }



}
