package imitative.lh.com.wanandroid.network.util;

import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.exception.ApiException;
import imitative.lh.com.wanandroid.network.exception.CustomException;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public class RxUtil {
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult(){
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }

    public static <T> ObservableTransformer<BaseResponse<T>, T> handleLogoutResult(){
        return upstream -> upstream.flatMap(new LogoutResponseFunstion<>());
    }

    /**
     * 非服务器产生的异常
     * 比如 解析json 网络问题等
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable, ObservableSource<? extends BaseResponse<T>>> {
        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 过滤服务器产生的数据
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {
        @Override
        public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
            int code = tBaseResponse.getErrorCode();
            String msg = tBaseResponse.getErrorMsg();
            if (code == BaseResponse.CODE_SUCCESS && tBaseResponse.getData() != null){
                return Observable.just(tBaseResponse.getData());
            }else {
                return Observable.error(new ApiException(code, msg));
            }
        }
    }

    public static <T> ObservableTransformer<T, T> rxSchedulerHelper(){
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static class LogoutResponseFunstion<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
            int code = tBaseResponse.getErrorCode();
            String msg = tBaseResponse.getErrorMsg();
            if (code == BaseResponse.CODE_SUCCESS){
                return Observable.create(emitter -> {
                    emitter.onNext(CommonUtils.cast(new LoginData()));
                    emitter.onComplete();
                });
            }else {
                return Observable.error(new ApiException(code, msg));
            }
        }
    }
}
