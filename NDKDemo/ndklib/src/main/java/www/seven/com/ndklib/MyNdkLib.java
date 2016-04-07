package www.seven.com.ndklib;

/**
 * Created by Seven on 2016/3/15.
 */
public class MyNdkLib {

    static {
        System.loadLibrary("myndklib");
    }

    public native String getHelloWorld();
}
