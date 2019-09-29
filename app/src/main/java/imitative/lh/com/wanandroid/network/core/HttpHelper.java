package imitative.lh.com.wanandroid.network.core;

import java.util.List;
import java.util.Map;

import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.network.bean.ProjectListData;
import imitative.lh.com.wanandroid.network.bean.ProjectTab;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.WxArticalListData;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;
import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.Path;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public interface HttpHelper {

    /**
     * 登录
     * @param
     * @return
     */
    Observable<BaseResponse<LoginData>> getLoginData(String username, String password);

    /**
     * 登出
     * @return
     */
    Observable<BaseResponse<LoginData>> logout();

    /**
     * 顶置文章
     * @return
     */
    Observable<BaseResponse<List<EssayData>>> getTopArticalListData();

    /**
     * 广告栏
     * @return
     */
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    /**
     * 首页列表
     * @param pageIndex 页码
     * @return
     */
    Observable<BaseResponse<EssayListData>> getEssayListData(int pageIndex);

    /**
     * 知识体系
     * @return
     */
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowleageHierarchyData();

    /**
     * 微信公众号 Tab
     * @return
     */
    Observable<BaseResponse<List<WxAuthor>>> getWxAuthorTab();

    /**
     * 查看某个公众号历史数据
     */
    Observable<BaseResponse<WxArticalListData>> getWxAuthorListData(int authorId, int pageIndex);

    /**
     * 导航
     * @return
     */
    Observable<BaseResponse<List<NavigationListData>>> getNavigationListData();

    /**
     * 项目 Tab
     * @return
     */
    Observable<BaseResponse<List<ProjectTab>>> getProjectTab();

    /**
     * 项目 数据列表
     *
     */
    Observable<BaseResponse<ProjectListData>> getProjectListData(int pageIndex, int cid);

    /**
     * 收藏列表
     * @param pageIndex 页码
     * @return
     */
    Observable<BaseResponse<EssayListData>> getCollectionData(int pageIndex);
}
