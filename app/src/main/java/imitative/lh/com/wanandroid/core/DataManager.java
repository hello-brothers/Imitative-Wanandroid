package imitative.lh.com.wanandroid.core;

import java.util.List;

import imitative.lh.com.wanandroid.core.prefs.PreferenceHelper;
import imitative.lh.com.wanandroid.network.base.BaseResponse;
import imitative.lh.com.wanandroid.network.bean.EssayListData;
import imitative.lh.com.wanandroid.network.core.HttpHelper;
import imitative.lh.com.wanandroid.network.bean.BannerData;
import io.reactivex.Observable;

/**
 * 代理模式
 */
public class DataManager implements PreferenceHelper, HttpHelper {
    private final HttpHelper httpHelper;
    private final PreferenceHelper preferenceHelper;

    public DataManager(PreferenceHelper preferenceHelper, HttpHelper httpHelper) {
        this.preferenceHelper = preferenceHelper;
        this.httpHelper = httpHelper;
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


    /**********************************************http*****************************************************************/
    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerData() {
        return httpHelper.getBannerData();
    }

    @Override
    public Observable<BaseResponse<EssayListData>> getEssayListData(int pageIndex) {
        return httpHelper.getEssayListData(pageIndex);
    }
}
