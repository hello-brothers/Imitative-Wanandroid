package imitative.lh.com.wanandroid.core.db;

import java.util.List;

import imitative.lh.com.wanandroid.core.dao.HistoryData;

/**
 * @Date 2019/10/9
 * @created by lh
 * @Describe:
 */
public interface DBHelper {

    /**
     * 添加历史记录
     * @param data 需要添加的数据
     */
    void addHistoryData(String data);

    /**
     * 清除所有的历史数据
     */
    void clearAllHistoryData();

    /**
     * 清楚单个历史记录
     * @param data
     */
    void clearOneHistoryData(String data);

    /**
     * 加载所有的历史数据
     * @return 返回所有的历史数据
     */
    List<HistoryData> loadAllHistoryData();
}
