package www.seven.com.mvpdemo.demo;

import android.os.Bundle;

import java.util.ArrayList;

import www.seven.com.mvpdemo.demo.interfaces.DemoViewInterface;
import www.seven.com.mvpdemo.framework.BaseActivity;

/**
 * Created by sunyun004_macmini on 16/3/1.
 */
public class DemoActivity extends BaseActivity<DemoViewInterface, DemoPresenter> implements DemoViewInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected DemoPresenter getPresenter() {
        return new DemoPresenter();
    }

    @Override
    public void setEditable(boolean editable) {

    }

    @Override
    public void showListData(int page, ArrayList<DemoBean> dataList) {

    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void hideLoadingProgress() {

    }
}
