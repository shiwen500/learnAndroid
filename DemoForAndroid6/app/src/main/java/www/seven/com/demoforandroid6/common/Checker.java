package www.seven.com.demoforandroid6.common;

/**
 * Created by Seven on 2016/4/23.
 */
public class Checker {

    public static <T> T checkNotNull(T ref) {
        if (ref == null) {
            throw new NullPointerException("Object is Null");
        }

        return ref;
    }
}
