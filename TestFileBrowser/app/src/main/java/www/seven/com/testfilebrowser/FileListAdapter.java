package www.seven.com.testfilebrowser;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by sunyun004_macmini on 16/5/9.
 */
public class FileListAdapter extends ArrayAdapter<File> {
    public FileListAdapter(Context context, List<File> objects) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        File row = getItem(position);

        String permissions = getDirOrNot(row.isDirectory(), "d") + getReadWriteState(row.canRead(), "r") + getReadWriteState(row.canWrite(), "w")
                + getReadWriteState(row.canExecute(), "x");

        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setText(row.getAbsolutePath()+"(" + permissions +")");

        return view;
    }

    String getReadWriteState(boolean can, String show) {
        if (can) {
            return show;
        } else {
            return "-";
        }
    }

    String getDirOrNot(boolean isdir, String show) {

        if (isdir) {
            return show;
        } else {
            return "-";
        }
    }
}
