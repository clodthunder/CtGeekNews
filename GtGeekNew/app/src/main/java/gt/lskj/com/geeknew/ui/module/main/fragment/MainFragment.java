package gt.lskj.com.geeknew.ui.module.main.fragment;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.widget.Toast;

import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import gt.lskj.com.geeknew.R;
import gt.lskj.com.geeknew.https.beans.WXItemBean;
import gt.lskj.com.geeknew.recycleview.divider.GItemDecoration;
import gt.lskj.com.geeknew.ui.base.BaseFragment;
import gt.lskj.com.geeknew.ui.module.main.adapter.MainFragmentAdapter;
import gt.lskj.com.geeknew.ui.module.main.contract.MainFragmentContract;
import gt.lskj.com.geeknew.ui.module.main.presenter.MainFragmentPresenter;


/**
 * Created by Home on 16/9/18.
 */
public class MainFragment extends BaseFragment<MainFragmentPresenter> implements MainFragmentContract.View {
    @BindView(R.id.rol_main_fragment)
    RotateLoading mRotateLoading;
    @BindView(R.id.rv_wechat_list)
    RecyclerView mRecycleview;
    @BindView(R.id.srl_main_fragment)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<WXItemBean> mList;

    private MainFragmentAdapter mFragmentAdapter;
    boolean isLoadingMore = false;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    //该周期在第二条路的情况下只会执行一次
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //实例化数据集合
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initEventAndData() {
        mList= new ArrayList<>();
        mFragmentAdapter = new MainFragmentAdapter(mContext);
        mRecycleview.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleview.addItemDecoration(new GItemDecoration(mContext, OrientationHelper.VERTICAL));
        mRecycleview.setAdapter(mFragmentAdapter);
        mRecycleview.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) mRecycleview.getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = mRecycleview.getLayoutManager().getItemCount();
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {  //还剩2个Item时加载更多
                    if (!isLoadingMore) {
                        isLoadingMore = true;
                        mPresenter.getMoreWechatData();
                    }
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRotateLoading.start();
                mPresenter.getWechatData();
            }
        });
        mRotateLoading.start();
        mPresenter.getWechatData();
    }

    @Override
    public void setToastMsg(String msg) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showData(List<WXItemBean> data) {
        mRotateLoading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (!isLoadingMore){
            mList.clear();
        }
        mList.addAll(data);
        isLoadingMore = false;
        mFragmentAdapter.setmDataList(mList);
    }

    @Override
    public void setEmpty(boolean isShow) {
        mRotateLoading.stop();
        //出错了
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


}
