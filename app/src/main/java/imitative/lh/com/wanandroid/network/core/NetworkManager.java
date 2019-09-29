package imitative.lh.com.wanandroid.network.core;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.network.api.WanApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;
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
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), mSharedCookiePersistor))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(WanApi.HOST)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
        mSharedCookiePersistor.clear();
    }


}
