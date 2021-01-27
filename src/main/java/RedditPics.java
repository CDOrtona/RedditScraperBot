import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.rmi.MarshalException;
import java.util.ArrayList;


public class RedditPics extends TelegramLongPollingBot {
    public String getBotUsername() {
        return "RedditDailyPics_bot";
    }

    public String getBotToken() {
        return "1557782545:AAH-ron27SSMo4PWS9zUgXCOUHuAxb5e8KE";
    }

    public void onUpdateReceived(Update update) {

        Message message;
        String choosenSub = null;

        if(update.hasMessage() && update.getMessage().hasText()){
            message = update.getMessage();
            switch (message.getText().toLowerCase()) {
                case "settings" :
                    chooseSettings(message);
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



    private void chooseSettings(Message message){

        ImageInfo imageInfo = new ImageInfo();

        String subreddit;
        int numImages;
        sendMsg(message, "Enter the name of the subreddit you wish to get Pictures from");


    }

    private void showImages(String choosenSub) throws IOException {

        GetImage.getRedditPic(choosenSub);
        SendPhoto photo = new SendPhoto();
    }



}
