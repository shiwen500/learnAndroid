package www.seven.com.mvpdemo.framework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sunyun004_macmini on 16/3/1.
 * V 是一个View接口, T 是一个持有V 的Presenter
 * Activity 被当做是View
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity{

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = getPresenter();

        // 如果继承这个BaseActivity的子类没有implements V接口
        // 在这里会出错。
        mPresenter.attachView((V)this);
    }

    /**
     * 让子类去实现如何创建Presenter
     * @return 特定的Presenter
     */
    protected abstract T getPresenter();

    /**
     * 在Activity销毁的时候，解除Presenter的绑定
     * 能够让系统及时回收内存
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mPresenter.detachView();
    }
}
