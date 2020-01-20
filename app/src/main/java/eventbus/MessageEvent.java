package eventbus;

/**
 * Created by wuyue on 2019/11/5.
 * description:
 */
public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
