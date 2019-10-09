package imitative.lh.com.wanandroid.core.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Iterator;
import java.util.List;

import imitative.lh.com.wanandroid.app.Constants;
import imitative.lh.com.wanandroid.core.dao.DaoMaster;
import imitative.lh.com.wanandroid.core.dao.DaoSession;
import imitative.lh.com.wanandroid.core.dao.HistoryData;
import imitative.lh.com.wanandroid.core.dao.HistoryDataDao;

/**
 * @Date 2019/10/9
 * @created by lh
 * @Describe:
 */
public class DBHelperImpl implements DBHelper {


    private final Context context;
    private DaoMaster.DevOpenHelper helper;
    private final DaoMaster daoMaster;
    private final DaoSession session;
    private static DBHelperImpl instance;

    public static DBHelperImpl getInstance(Context context){
        if (instance == null){
            synchronized (DBHelperImpl.class){
                if (instance == null){
                    instance = new DBHelperImpl(context);
                }
            }
        }
        return instance;
    }

    DBHelperImpl(Context context){
        this.context = context;
        helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
        daoMaster = new DaoMaster(getWritableDatabase());
        session = daoMaster.newSession();
    }

    private HistoryDataDao getHistoryDataDao() {
        return session.getHistoryDataDao();
    }

    private SQLiteDatabase getWritableDatabase(){
        if (helper == null){
            helper = new DaoMaster.DevOpenHelper(context, "history.db", null);
        }
        return helper.getWritableDatabase();
    }

    private void insert(String data){
        HistoryData historyData = createHistoryData(data);
        getHistoryDataDao().insert(historyData);
    }

    private void delete(String data){
        getHistoryDataDao()
                .queryBuilder()
                .where(HistoryDataDao.Properties.Value.eq(data))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    /**
     *
     * @param data 需要添加的数据
     */
    @Override
    public void addHistoryData(String data) {

        isAlreadyInDbAndDelete(data);

        insert(data);
    }

    @Override
    public void clearAllHistoryData() {
        getHistoryDataDao().deleteAll();
    }

    @Override
    public void clearOneHistoryData(String data) {
        delete(data);
    }

    @Override
    public List<HistoryData> loadAllHistoryData() {
        return getHistoryDataDao().loadAll();
    }

    private boolean isAlreadyInDbAndDelete(String data) {
        List<HistoryData> historyDataList = getHistoryDataDao().loadAll();
        Iterator<HistoryData> iterator = historyDataList.iterator();
        while (iterator.hasNext()){
            HistoryData historyData = iterator.next();
            if (historyData.getValue().equals(data)){
                delete(data);
                return true;
            }
        }
        return false;
    }

    /**
     * 根据data创建一个实例对象
     * @param data
     * @return
     */
    private HistoryData createHistoryData(String data) {
        HistoryData historyData = new HistoryData();
        historyData.setValue(data);
        historyData.setDate(System.currentTimeMillis());
        return historyData;
    }
}
