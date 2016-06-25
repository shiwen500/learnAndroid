package www.seven.com.meiapp.main.mei;

import java.util.List;

import www.seven.com.meiapp.main.BasePresenter;
import www.seven.com.meiapp.main.BaseView;
import www.seven.com.meiapp.main.mei.data.MeiEntity;

/**
 * Created by Seven on 2016/5/21.
 */
public interface MeiContract {

    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter> {

        void showMeiDatas(List<MeiEntity> data);

        void showFail(String msg);
    }
}
