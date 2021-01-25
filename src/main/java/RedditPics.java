import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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

        

        ReplyKeyboardMarkup replyKeyboardMarkup = setKeyboard();

        if(update.hasMessage() && update.getMessage().hasText()){
            message = update.getMessage();
        }

    }

    public ReplyKeyboardMarkup setKeyboard(){

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    }

}
