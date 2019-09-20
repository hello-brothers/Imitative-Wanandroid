package imitative.lh.com.wanandroid.network.core;

import imitative.lh.com.wanandroid.network.api.WanApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe: 管理类
 */
public class NetworkManager {
    private static NetworkManager ourInstance;
    private static Retrofit retrofit;
    private static WanApi wanApi;

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
        httpLoggingInterceptor.setLevel(BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
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




}
