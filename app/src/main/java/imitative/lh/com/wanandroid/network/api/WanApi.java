package imitative.lh.com.wanandroid.network.api;

import java.util.List;

import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public interface WanApi {

    String HOST = "https://www.wanandroid.com/";


    /**
     * 首页文章列表
     * https://www.wanandroid.com/article/list/0/json
     * @return
     */
    @GET("article/list/{pageIndex}/json")
    Observable<BaseResponse<EssayListData>> getEssayListData(@Path("pageIndex")int pageIndex);

    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();



}
