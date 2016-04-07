// IBinderPool.aidl
package www.seven.com.binderpooldemo.aidl;
import android.os.IBinder;

// Declare any non-default types here with import statements

interface IBinderPool {

    IBinder queryBinder(int binderCode);
}
