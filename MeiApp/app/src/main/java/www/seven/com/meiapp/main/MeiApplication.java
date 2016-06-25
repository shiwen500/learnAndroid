package www.seven.com.meiapp.main;

import android.app.Application;

/**
 * Created by Seven on 2016/6/14.
 */
public class MeiApplication extends Application {

    private static MeiApplication app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }

    public static MeiApplication getInstance() {

        return app;
    }
}
