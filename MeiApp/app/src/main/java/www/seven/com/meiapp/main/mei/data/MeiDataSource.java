package www.seven.com.meiapp.main.mei.data;

import java.util.List;

/**
 * Created by Seven on 2016/5/21.
 */
public interface MeiDataSource {

    interface GetMeiDataCallBack {

        void onSuccess(List<MeiEntity> datas);

        void onFail();
    }

    void getMeiDatas(GetMeiDataCallBack callBack);
}
