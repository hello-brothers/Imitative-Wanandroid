package imitative.lh.com.wanandroid.network.core;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import imitative.lh.com.wanandroid.BuildConfig;
import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.network.api.WanApi;
import imitative.lh.com.wanandroid.utils.CommonUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe: 管理类
 */
public class NetworkManager {
    private static NetworkManager ourInstance;
    private static Retrofit retrofit;
    private static WanApi wanApi;
    private static final SharedPrefsCookiePersistor mSharedCookiePersistor =  new SharedPrefsCookiePersistor(WanAndroidApp.getInstance());
    public static final PersistentCookieJar persistentCookieJar = new PersistentCookieJar(new SetCookieCache(), mSharedCookiePersistor);


    public static NetworkManager getInstance() {
        init();
       if (ourInstance == null){
           synchronized (NetworkManager.class){
               if (ourInstance == null){
                   ourInstance = new NetworkManager();
               }
           }
       }
        return ourInstance;
    }

    private static void init() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();


        OkHttpClient client = decorateBuilder(builder);
        retrofit = new Retrofit.Builder()
                .baseUrl(WanApi.HOST)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient decorateBuilder(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(BODY);
            builder.addInterceptor(httpLoggingInterceptor);
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        /**
         * 一般缓存策略：有网络的时候，读取缓存直到缓存时间到读取网络数据；在没有网络时读取缓存数据。
         */
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!CommonUtils.isNetworkConnected()){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (CommonUtils.isNetworkConnected()){
                //有网络时，不缓存，最大的保存时长设置为0
                int maxAge = 0;
                response = response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            }else {
                int maxStale = 60 * 60 * 24 * 28;
                response = response.newBuilder()
                        .addHeader("Cache-Control", "")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        };

        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);//网络拦截器
        builder.addInterceptor(cacheInterceptor);       //应用拦截器
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024*1024*50);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //超时重连
        builder.retryOnConnectionFailure(true);
        //Cookie认证
        builder.cookieJar(persistentCookieJar);
        return builder.build();
    }

    public WanApi createApi() {
            if (ourInstance == null){
                throw new IllegalStateException("需要调用NetworkManager#getInstance()");
            }
            if (wanApi == null){
                synchronized (WanApi.class){
                    wanApi = retrofit.create(WanApi.class);
                }
            }
            return wanApi;
    }

    public void removeAllCookie(){
        persistentCookieJar.clear();
    }
}
