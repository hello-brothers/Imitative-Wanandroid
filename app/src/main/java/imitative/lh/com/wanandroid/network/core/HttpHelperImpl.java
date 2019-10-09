package imitative.lh.com.wanandroid.network.core;

import java.util.List;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.api.WanApi;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.HotKey;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.network.bean.ProjectTab;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;
import io.reactivex.Observable;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe: HttpHelper的实现类
 */
public class HttpHelperImpl implements HttpHelper{

    private WanApi service = WanAndroidApp.getInstance().getNetworkManager().createApi();

    /**
     * 登录
     * @param
     * @return
     */
    @Override
    public Observable<BaseResponse<LoginData>> getLoginData(String username, String password) {
        return service.getLoginData(username, password);
    }

    /**
     * 登出
     * @return
     */
    @Override
    public Observable<BaseResponse<LoginData>> logout() {
        return service.logout();
    }

    @Override
    public Observable<BaseResponse<List<EssayData>>> getTopArticalListData() {
        return service.getTopArticalListData();
    }

    /**
     * banner数据
     * @return
     */
    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return service.getBannerData();
    }

    /**
     * 首页文章数据
     * @param pageIndex 页码
     * @return
     */
    @Override
    public Observable<BaseResponse<EssayListData>> getEssayListData(int pageIndex) {
        return service.getEssayListData(pageIndex);
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowleageHierarchyData() {
        return service.getKnowleageHierarchyData();
    }

    @Override
    public Observable<BaseResponse<List<WxAuthor>>> getWxAuthorTab() {
        return service.getWxAuthorTab();
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getWxAuthorListData(int authorId, int pageIndex) {
        return service.getWxAuthorListData(authorId, pageIndex);
    }

    @Override
    public Observable<BaseResponse<List<NavigationListData>>> getNavigationListData() {
        return service.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<List<ProjectTab>>> getProjectTab() {
        return service.getProjectTab();
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getProjectListData(int pageIndex, int cid) {
        return service.getProjectListData(pageIndex, cid);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getCollectionData(int pageIndex) {
        return service.getCollectionData(pageIndex);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> addCollectEssay(int essayId) {
        return service.addCollectEssay(essayId);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> cancelCollectEssay(int essayId) {
        return service.cancelCollectEssay(essayId);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> cancelPageCollectEssay(int essayId) {
        return service.cancelPageCollectEssay(essayId, -1);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getKnowledagDetailListData(int pageIndex, int cid) {
        return service.getKnowledagDetailListData(pageIndex, cid);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getSearchData(int pageIndex, String key) {
        return service.getSearchData(pageIndex, key);
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> getSearchHotKey() {
        return service.getSearchHotKey();
    }
}
