package www.seven.com.mvpdemo.framework;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by sunyun004_macmini on 16/3/1.
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment{

    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = getPresenter();
        mPresenter.attachView((V)this);
    }

    protected abstract T getPresenter() ;

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }
}
