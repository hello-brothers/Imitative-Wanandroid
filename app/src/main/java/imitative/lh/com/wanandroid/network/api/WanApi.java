package imitative.lh.com.wanandroid.network.api;

import java.util.List;

import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.HotKey;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.network.bean.ProjectTab;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.RegisterData;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public interface WanApi {

    String HOST = "https://www.wanandroid.com/";

    /**
     * 登录
     * https://www.wanandroid.com/user/login
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponse<LoginData>> getLoginData(@Field("username")String username, @Field("password")String password);

    /**
     * 注册
     *https://www.wanandroid.com/user/register
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseResponse<RegisterData>> register(@Field("username") String username, @Field(("password")) String password, @Field("repassword") String repassword);


    /**
     * 登出
     * https://www.wanandroid.com/user/logout/json
     * @return
     */
    @GET("user/logout/json")
    Observable<BaseResponse<LoginData>> logout();
    /**
     * 顶置文章
     * https://www.wanandroid.com/article/top/json
     */
    @GET("article/top/json")
    Observable<BaseResponse<List<EssayData>>> getTopArticalListData();


    /**
     * 首页文章列表
     * https://www.wanandroid.com/article/list/0/json
     * @return
     */
    @GET("article/list/{pageIndex}/json")
    Observable<BaseResponse<EssayListData>> getEssayListData(@Path("pageIndex")int pageIndex);

    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    /**
     * 知识体系数据
     * https://www.wanandroid.com/tree/json
     * @return
     */
    @GET("tree/json")
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowleageHierarchyData();

    /**
     * 微信公众号 Tab
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<WxAuthor>>> getWxAuthorTab();

    /**
     * 微信公众号 查看某个公众号历史数据
     */
    @GET("wxarticle/list/{authorId}/{pageIndex}/json")
    Observable<BaseResponse<EssayListData>> getWxAuthorListData(@Path("authorId") int authorId, @Path("pageIndex") int pageIndex);

    /**
     * 导航
     * https://www.wanandroid.com/navi/json
     */
    @GET("navi/json")
    Observable<BaseResponse<List<NavigationListData>>> getNavigationListData();

    /**
     * 项目 Tab
     * https://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectTab>>> getProjectTab();

    /**
     * 项目 列表数据
     * https://www.wanandroid.com/project/list/1/json?cid=294
     */
    @GET("project/list/{pageIndex}/json")
    Observable<BaseResponse<EssayListData>> getProjectListData(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

    /**
     * 收藏列表
     * https://www.wanandroid.com/lg/collect/list/0/json
     * @return
     */
    @GET("lg/collect/list/{pageIndex}/json")
    Observable<BaseResponse<EssayListData>> getCollectionData(@Path("pageIndex") int pageIndex);


    /**
     * 收藏站内文章
     * https://www.wanandroid.com/lg/collect/1165/json
     *
     */
    @POST("lg/collect/{essayId}/json")
    Observable<BaseResponse<EssayListData>> addCollectEssay(@Path("essayId") int essayId);

    /**
     *
     * 取消收藏： 文章列表位置
     * https://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param essayId
     * @return
     */
    @POST("lg/uncollect_originId/{essayId}/json")
    Observable<BaseResponse<EssayListData>> cancelCollectEssay(@Path("essayId") int essayId);

    /**
     * 取消收藏：收藏列表位置
     * https://www.wanandroid.com/lg/uncollect/2805/json
     * @param essayId
     * @param originId 代表的是你收藏之前的那篇文章本身的id 但是收藏支持主动添加，这种情况下，没有originId则为-1
     * @return
     */
    @FormUrlEncoded
    @POST("lg/uncollect/{essayId}/json")
    Observable<BaseResponse<EssayListData>> cancelPageCollectEssay(@Path("essayId") int essayId, @Field("originId") int originId);

    /**
     * 知识体系下的文章
     * https://www.wanandroid.com/article/list/0/json?cid=60
     * @param cid
     * @return
     */
    @GET("article/list/{pageIndex}/json")
    Observable<BaseResponse<EssayListData>> getKnowledagDetailListData(@Path("pageIndex") int pageIndex, @Query("cid") int cid);

    /**
     * 搜索
     * https://www.wanandroid.com/article/query/0/json
     * @param pageIndex
     * @param key
     * @return
     */
    @POST("article/query/{pageIndex}/json")
    @FormUrlEncoded
    Observable<BaseResponse<EssayListData>> getSearchData(@Path("pageIndex") int pageIndex, @Field("k") String key);

    /**
     * 搜索热词
     * https://www.wanandroid.com//hotkey/json
     */
    @GET("/hotkey/json")
    Observable<BaseResponse<List<HotKey>>> getSearchHotKey();

}
