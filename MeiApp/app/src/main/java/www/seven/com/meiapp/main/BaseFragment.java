package www.seven.com.meiapp.main;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Seven on 2016/5/26.
 */
public class BaseFragment extends Fragment {

    public void showToast(String toast) {

        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }
}
