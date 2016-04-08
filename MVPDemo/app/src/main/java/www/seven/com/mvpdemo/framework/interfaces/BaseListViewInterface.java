package www.seven.com.mvpdemo.framework.interfaces;

import java.util.ArrayList;

/**
 * Created by sunyun004_macmini on 16/3/1.
 * 所有的，需要listview显示内容的View都应该实现这个接口
 */
public interface BaseListViewInterface<T> {

    boolean isLoadMoreEnable();
    boolean isPullRefreshEnable();
    void showListData(int page, ArrayList<T> dataList);
    void showLoadingProgress();
    void hideLoadingProgress();

    /**
     * 如果实现一个正常的ListView，实现下面的接口
     * @param <T>
     */
    public static interface ListViewInterface<T> extends BaseListViewInterface<T> {
        default boolean isLoadMoreEnable(){return false;}
        default boolean isPullRefreshEnable(){return false;}
    }

    /**
     * 如果想要实现上拉加载更多的功能，实现下面的接口
     * @param <T>
     */
    public static interface ListViewLoadMoreInterface<T> extends BaseListViewInterface<T> {
        default boolean isLoadMoreEnable(){return true;}
        default boolean isPullRefreshEnable(){return false;}
    }

}
