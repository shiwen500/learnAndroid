package www.seven.com.mvpdemo.demo.interfaces;

import www.seven.com.mvpdemo.demo.DemoBean;
import www.seven.com.mvpdemo.framework.interfaces.BaseListViewInterface;

/**
 * Created by sunyun004_macmini on 16/3/1.
 * 这里加一些专有的接口，比如设置列表是否可编辑
 */
public interface DemoViewInterface extends BaseListViewInterface.ListViewInterface<DemoBean>{

    /**
     * @param editable
     */
    void setEditable(boolean editable);
}
