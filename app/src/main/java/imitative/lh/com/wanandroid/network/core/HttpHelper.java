package imitative.lh.com.wanandroid.network.core;

import java.util.List;

import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import io.reactivex.Observable;

/**
 * @Date 2019/9/20
 * @created by lh
 * @Describe:
 */
public interface HttpHelper {

    /**
     * 广告栏
     * @return
     */
    Observable<BaseResponse<List<BannerData>>> getBannerData();

    Observable<BaseResponse<EssayListData>> getEssayListData(int pageIndex);

    /**
     * 知识体系
     * @return
     */
    Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowleageHierarchyData();

}
