package www.seven.com.meiapp.main.mei;

import java.util.List;

import www.seven.com.meiapp.main.mei.data.MeiDataSource;
import www.seven.com.meiapp.main.mei.data.MeiEntity;

/**
 * Created by Seven on 2016/5/21.
 */
public class MeiPresenter implements MeiContract.Presenter {

    private MeiContract.View view;

    private MeiDataSource data;

    public MeiPresenter(MeiContract.View v, MeiDataSource d) {

        view = v;
        data = d;

        v.setPresenter(this);
    }


    @Override
    public void start() {

        data.getMeiDatas(new MeiDataSource.GetMeiDataCallBack() {

            @Override
            public void onSuccess(List<MeiEntity> datas) {

                view.showMeiDatas(datas);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
