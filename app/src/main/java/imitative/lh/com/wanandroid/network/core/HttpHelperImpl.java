package imitative.lh.com.wanandroid.network.core;

import java.util.List;

import imitative.lh.com.wanandroid.app.WanAndroidApp;
import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.api.WanApi;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import io.reactivex.Observable;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe: HttpHelper的实现类
 */
public class HttpHelperImpl implements HttpHelper{

    private WanApi service = WanAndroidApp.getInstance().getNetworkManager().createApi();

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
}
