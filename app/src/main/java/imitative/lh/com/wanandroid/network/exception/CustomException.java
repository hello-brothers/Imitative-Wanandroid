package imitative.lh.com.wanandroid.network.exception;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public class CustomException {



    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1003;

    public static Exception handleException(Throwable throwable) {
        ApiException exception = null;
        if (throwable instanceof JsonParseException || throwable instanceof ParseException || throwable instanceof JSONException){
            exception = new ApiException(PARSE_ERROR, throwable.getMessage());
        }else if (throwable instanceof ConnectException){
            exception = new ApiException(NETWORK_ERROR, throwable.getMessage());
        }else if (throwable instanceof SocketTimeoutException || throwable instanceof UnknownHostException){
            exception = new ApiException(NETWORK_ERROR, throwable.getMessage());
        }else {
            exception = new ApiException(UNKNOWN, throwable.getMessage());
        }
        return exception;
    }
}
