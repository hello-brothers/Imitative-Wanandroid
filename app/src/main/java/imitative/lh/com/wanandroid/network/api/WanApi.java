package imitative.lh.com.wanandroid.network.api;

import java.util.List;

import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.network.bean.ProjectListData;
import imitative.lh.com.wanandroid.network.bean.ProjectTab;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.WxArticalListData;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;
import io.reactivex.Observable;
import retrofit2.http.GET;
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
    Observable<BaseResponse<WxArticalListData>> getWxAuthorListData(@Path("authorId") int authorId, @Path("pageIndex") int pageIndex);

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
    Observable<BaseResponse<ProjectListData>> getProjectListData(@Path("pageIndex") int pageIndex, @Query("cid") int cid);



}
