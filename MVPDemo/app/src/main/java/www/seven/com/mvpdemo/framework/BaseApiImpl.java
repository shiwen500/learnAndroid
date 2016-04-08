package www.seven.com.mvpdemo.framework;

import android.os.Bundle;

import www.seven.com.mvpdemo.framework.interfaces.BaseApiExecutorInterface;

/**
 * Created by sunyun004_macmini on 16/3/1.
 */
public abstract class BaseApiImpl<T> implements Runnable{

    protected Bundle data;
    protected BaseApiExecutorInterface<T> executorInterface;
    protected ApiCallBackInterface<T> apiCallBackInterface;

    public BaseApiImpl(Bundle data, BaseApiExecutorInterface<T> executorInterface,
                       ApiCallBackInterface<T> apiCallBackInterface) {
        this.data = data;
        this.executorInterface = executorInterface;
        this.apiCallBackInterface = apiCallBackInterface;
    }

    @Override
    final public void run() {
        T res = onExecute(data, executorInterface);
        if (isExecuteSuccess()) {
            apiCallBackInterface.onSuccess(res);
        } else {
            apiCallBackInterface.onFail(res);
        }
    }

    public abstract T onExecute(Bundle data, BaseApiExecutorInterface<T> executorInterface);
    public abstract boolean isExecuteSuccess();

    public static interface ApiCallBackInterface<T> {
        void onSuccess(T response);
        void onFail(T resonse);
    }
}
