package imitative.lh.com.wanandroid.core.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Date 2019/10/9
 * @created by lh
 * @Describe:
 */

@Entity
public class HistoryData {

    @Id(autoincrement = true)
    private Long id;

    private long date;

    private String value;

    @Generated(hash = 1568275096)
    public HistoryData(Long id, long date, String value) {
        this.id = id;
        this.date = date;
        this.value = value;
    }

    @Generated(hash = 422767273)
    public HistoryData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

}
