package imitative.lh.com.wanandroid.network.core;

import java.util.List;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.api.WanApi;
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

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe: HttpHelper的实现类
 */
public class HttpHelperImpl implements HttpHelper{

    private WanApi service = WanAndroidApp.getInstance().getNetworkManager().createApi();

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
    public Observable<BaseResponse<WxArticalListData>> getWxAuthorListData(int authorId, int pageIndex) {
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
    public Observable<BaseResponse<ProjectListData>> getProjectListData(int pageIndex, int cid) {
        return service.getProjectListData(pageIndex, cid);
    }
}
