package www.seven.com.mvpdemo.framework.interfaces;

import android.os.Bundle;

/**
 * Created by sunyun004_macmini on 16/3/1.
 */
public interface BaseApiExecutorInterface<T> {

    T execute(Bundle data);

    BaseApiExecutorInterface<T>  asInterface();
}
