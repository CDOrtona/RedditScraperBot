import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class RedditPics extends TelegramLongPollingBot {
    public String getBotUsername() {
        return "RedditDailyPics_bot";
    }

    public String getBotToken() {
        return "1557782545:AAH-ron27SSMo4PWS9zUgXCOUHuAxb5e8KE";
    }

    public void onUpdateReceived(Update update) {

        Message message;
        SendMessage sendMessage = new SendMessage();

        ReplyKeyboardMarkup replyKeyboardMarkup = setKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        if(update.hasMessage() && update.getMessage().hasText()){
            String receivedText = update.getMessage().getText();
            switch (receivedText.toLowerCase()) {
                case "/start" :
                    sendMsg(message, receivedText);
            }
        }

    }

    public ReplyKeyboardMarkup setKeyboard(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        //
        return replyKeyboardMarkup;
    }

    public void sendMsg(Message message, String text){

    }

}
