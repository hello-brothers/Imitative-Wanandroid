package imitative.lh.com.wanandroid.network.base;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe: 规定服务器数据返回的同一格式
 */
public class BaseResponse<T> {

    public static final int CODE_SUCCESS = 0;
    private int errorCode;

    private T data;

    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
