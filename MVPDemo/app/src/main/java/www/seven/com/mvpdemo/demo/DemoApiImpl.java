package www.seven.com.mvpdemo.demo;

import android.os.Bundle;

import www.seven.com.mvpdemo.framework.BaseApiImpl;
import www.seven.com.mvpdemo.framework.interfaces.BaseApiExecutorInterface;

/**
 * Created by sunyun004_macmini on 16/3/1.
 */
public class DemoApiImpl extends BaseApiImpl<String> {

    protected boolean success;

    public DemoApiImpl(Bundle data, BaseApiExecutorInterface<String> executorInterface,
                       ApiCallBackInterface<String> res) {
        super(data, executorInterface, res);
    }

    @Override
    public String onExecute(Bundle data, BaseApiExecutorInterface<String> executorInterface) {
        String res = executorInterface.execute(data);
        if (data.getInt("code") == 200) {
            success = true;
        } else {
            success = false;
        }
        return res;
    }

    @Override
    public boolean isExecuteSuccess() {
        return success;
    }
}
