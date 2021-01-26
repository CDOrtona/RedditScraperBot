import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class RedditPics extends TelegramLongPollingBot {
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
            switch (message.getText().toLowerCase()) {
                case "/start" :
                    sendMsg(message, "Please choose an option:");
                case "display images" :
                    sendMsg(message, "What is the Subreddit do you want to receive Pictures from?");

            }
        }

    }

    public ReplyKeyboardMarkup setKeyboard(SendMessage sendMessage){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        //
        return replyKeyboardMarkup;
    }

    public void sendMsg(Message message, String text){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);

        try {
            execute(sendMessage);
            setKeyboard(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

}
