package www.seven.com.meiapp.main.mei;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import www.seven.com.meiapp.R;
import www.seven.com.meiapp.main.BaseFragment;
import www.seven.com.meiapp.main.mei.data.MeiEntity;

/**
 * Created by Seven on 2016/5/21.
 */
public class MeiFragment extends BaseFragment implements MeiContract.View {

    private MeiContract.Presenter mPresenter;

    private RecyclerView recyclerView;

    private MeiAdapter mAdapter;

    private List<MeiEntity> datas = new ArrayList<>();

    @Override
    public void setPresenter(MeiContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void showMeiDatas(List<MeiEntity> data) {

        datas.clear();
        datas.addAll(data);

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void showFail(String msg) {

        showToast(msg);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        recyclerView = new RecyclerView(getContext());
        mAdapter = new MeiAdapter(datas);

        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPresenter.start();

        return recyclerView;
    }

    static class MeiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<MeiEntity> datas;

        public MeiAdapter(List<MeiEntity> data) {
            datas = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mei, parent, false);

            return new MeiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }


        class MeiViewHolder extends RecyclerView.ViewHolder{

            RecyclerView picture_list;

            TextView title;

            public MeiViewHolder(View itemView) {
                super(itemView);

                picture_list = (RecyclerView) itemView.findViewById(R.id.picture_list);
                title = (TextView) itemView.findViewById(R.id.title);
            }
        }
    }
}
