package www.seven.com.demoforandroid6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.seven.com.demoforandroid6.Animator.EnterExitAnimatorActivity;

import static www.seven.com.demoforandroid6.common.Checker.checkNotNull;

public class MainActivity extends BaseAppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        String[] titles = getResources().getStringArray(R.array.demo_name);
        mainAdapter = new MainAdapter(this, titles);
        mainAdapter.setOnClickItemListener(onClickItemListener);
        recyclerView.setAdapter(mainAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private MainAdapter.OnClickItemListener onClickItemListener = new MainAdapter.OnClickItemListener() {
        @Override
        public void onClickItem(int position) {
            switch (position) {
                case 0:{
                    Intent intent = new Intent(MainActivity.this, EnterExitAnimatorActivity.class);
                    startActivity(intent);
                }
            }
        }
    };


    static class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

        private String[] titles;
        private LayoutInflater inflater;
        private OnClickItemListener onClickItemListener;

        public MainAdapter(Context context, String[]ts) {
            titles = checkNotNull(ts);
            inflater = LayoutInflater.from(checkNotNull(context));
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_main, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            String title = titles[position];
            holder.title.setText(title);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickItemListener != null) {
                        onClickItemListener.onClickItem(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            public TextView title;
            public View view;

            public ViewHolder(View itemView) {
                super(itemView);

                view = itemView;
                title = (TextView) itemView.findViewById(R.id.title);
            }
        }

        interface OnClickItemListener {
            void onClickItem(int position);
        }

        public void setOnClickItemListener(OnClickItemListener oc) {
            onClickItemListener = oc;
        }
    }
}
