package www.seven.com.meiapp.main.mei.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seven on 2016/5/26.
 */
public class MeiRemoteDataSources implements MeiDataSource {
    @Override
    public void getMeiDatas(GetMeiDataCallBack callBack) {

        List<MeiEntity> data = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {

            data.add(new MeiEntity());
        }

        callBack.onSuccess(data);
    }
}
