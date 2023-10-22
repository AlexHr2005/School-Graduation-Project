package elsys.project.modules.sms;

public interface SmsRetriever {
    void onMessageReceived(String message);
}
