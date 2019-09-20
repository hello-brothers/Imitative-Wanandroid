package imitative.lh.com.wanandroid.network.exception;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public class ApiException extends Exception{
    private int exceptionCode;
    private String displayMessage;

    public ApiException(int exceptionCode, String displayMessage) {
        this.exceptionCode = exceptionCode;
        this.displayMessage = displayMessage;
    }

    public ApiException(String message, int exceptionCode, String displayMessage) {
        super(message);
        this.exceptionCode = exceptionCode;
        this.displayMessage = displayMessage;
    }

    public int getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
