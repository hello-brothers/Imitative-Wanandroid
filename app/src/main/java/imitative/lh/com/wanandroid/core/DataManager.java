package imitative.lh.com.wanandroid.core;

import java.util.List;

import imitative.lh.com.wanandroid.core.dao.HistoryData;
import imitative.lh.com.wanandroid.core.db.DBHelper;
import imitative.lh.com.wanandroid.core.prefs.PreferenceHelper;
import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.bean.HotKey;
import imitative.lh.com.wanandroid.network.bean.KnowledgeHierarchyData;
import imitative.lh.com.wanandroid.network.bean.LoginData;
import imitative.lh.com.wanandroid.network.bean.NavigationListData;
import imitative.lh.com.wanandroid.network.bean.ProjectTab;
import imitative.lh.com.wanandroid.network.bean.EssayData;
import imitative.lh.com.wanandroid.network.bean.RegisterData;
import imitative.lh.com.wanandroid.network.bean.WxAuthor;
import imitative.lh.com.wanandroid.network.core.HttpHelper;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import io.reactivex.Observable;

/**
 * 代理模式
 */
public class DataManager implements PreferenceHelper, HttpHelper, DBHelper {
    private final HttpHelper httpHelper;
    private final PreferenceHelper preferenceHelper;
    private final DBHelper dbHelper;

    public DataManager(PreferenceHelper preferenceHelper, HttpHelper httpHelper, DBHelper dbHelper) {
        this.preferenceHelper = preferenceHelper;
        this.httpHelper = httpHelper;
        this.dbHelper = dbHelper;
    }

    @Override
    public void setLoginAccount(String account) {
        preferenceHelper.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        preferenceHelper.setLoginPassword(password);
    }

    @Override
    public void setLoginState(boolean isLogin) {
        preferenceHelper.setLoginState(isLogin);
    }

    @Override
    public String getLoginAccount() {
        return preferenceHelper.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return preferenceHelper.getLoginPassword();
    }

    @Override
    public boolean getLoginState() {
        return preferenceHelper.getLoginState();
    }

    @Override
    public int getCurrentPage() {
        return preferenceHelper.getCurrentPage();
    }

    @Override
    public void setCurrentPage(int pageIndex) {
        preferenceHelper.setCurrentPage(pageIndex);
    }

    @Override
    public void setIsLoadTopEssayData(boolean isLoad) {
        preferenceHelper.setIsLoadTopEssayData(isLoad);
    }

    @Override
    public boolean isLoadTopEssayData() {
        return preferenceHelper.isLoadTopEssayData();
    }




    /**********************************************http*****************************************************************/

    @Override
    public Observable<BaseResponse<LoginData>> getLoginData(String username, String password) {
        return httpHelper.getLoginData(username, password);
    }

    @Override
    public Observable<BaseResponse<RegisterData>> register(String username, String password, String repassword) {
        return httpHelper.register(username, password, repassword);
    }

    @Override
    public Observable<BaseResponse<LoginData>> logout() {
        return httpHelper.logout();
    }

    @Override
    public Observable<BaseResponse<List<EssayData>>> getTopArticalListData() {
        return httpHelper.getTopArticalListData();
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return httpHelper.getBannerData();
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getEssayListData(int pageIndex) {
        return httpHelper.getEssayListData(pageIndex);
    }

    @Override
    public Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowleageHierarchyData() {
        return httpHelper.getKnowleageHierarchyData();
    }

    @Override
    public Observable<BaseResponse<List<WxAuthor>>> getWxAuthorTab() {
        return httpHelper.getWxAuthorTab();
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getWxAuthorListData(int authorId, int pageIndex) {
        return httpHelper.getWxAuthorListData(authorId, pageIndex);
    }

    @Override
    public Observable<BaseResponse<List<NavigationListData>>> getNavigationListData() {
        return httpHelper.getNavigationListData();
    }

    @Override
    public Observable<BaseResponse<List<ProjectTab>>> getProjectTab() {
        return httpHelper.getProjectTab();
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getProjectListData(int pageIndex, int cid) {
        return httpHelper.getProjectListData(pageIndex, cid);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getCollectionData(int pageIndex) {
        return httpHelper.getCollectionData(pageIndex);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> addCollectEssay(int essayId) {
        return httpHelper.addCollectEssay(essayId);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> cancelCollectEssay(int essayId) {
        return httpHelper.cancelCollectEssay(essayId);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> cancelPageCollectEssay(int essayId) {
        return httpHelper.cancelPageCollectEssay(essayId);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getKnowledagDetailListData(int pageIndex, int cid) {
        return httpHelper.getKnowledagDetailListData(pageIndex, cid);
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getSearchData(int pageIndex, String key) {
        return httpHelper.getSearchData(pageIndex, key);
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> getSearchHotKey() {
        return httpHelper.getSearchHotKey();
    }

    /************************************************Database*****************************************************************/

    @Override
    public void addHistoryData(String data) {
        dbHelper.addHistoryData(data);
    }

    @Override
    public void clearAllHistoryData() {
        dbHelper.clearAllHistoryData();
    }

    @Override
    public void clearOneHistoryData(String data) {
        dbHelper.clearOneHistoryData(data);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return dbHelper.loadAllHistoryData();
    }
}
