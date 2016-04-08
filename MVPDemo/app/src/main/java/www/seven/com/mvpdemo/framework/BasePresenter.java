package www.seven.com.mvpdemo.framework;

import java.lang.ref.WeakReference;

/**
 * Created by sunyun004_macmini on 16/3/1.
 * T 是一个View的接口
 * 这个抽象类可供，Activity,Fragment使用
 */
public abstract class BasePresenter<T> {

    // 持有view的弱引用接口
    protected WeakReference<T> mWeakView;

    public void attachView(T view) {
        mWeakView = new WeakReference<T>(view);
    }

    protected T getView() {
        return mWeakView.get();
    }

    public boolean isViewAttached() {
        return mWeakView != null && mWeakView.get() != null;
    }

    public void detachView() {
        if (mWeakView != null) {
            mWeakView.clear();
            mWeakView = null;
        }
    }
}
