package gt.lskj.com.geeknew.ui.module.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gt.lskj.com.geeknew.R;
import gt.lskj.com.geeknew.gilde.ImageLoader;
import gt.lskj.com.geeknew.https.beans.WXItemBean;
import gt.lskj.com.geeknew.ui.cusview.SquareImageView;
import gt.lskj.com.geeknew.ui.module.main.activity.TechDetailActivity;

/**
 * Created by Home on 16/9/22.
 */
public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
    private List<WXItemBean> mDataList;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public MainFragmentAdapter(Context mContext) {
        this.mContext = mContext;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setmDataList(List<WXItemBean> mDataList) {
        this.mDataList = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_wxbean, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final WXItemBean current = mDataList.get(position);
        ImageLoader.load(mContext, current.getPicUrl(), holder.mSquareIamgeView);
        holder.mWxTitle.setText(current.getTitle());
        holder.mWxFrome.setText(current.getDescription());
        holder.mWxTime.setText(current.getCtime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mContext, TechDetailActivity.class);
                intent.putExtra("id", mDataList.get(holder.getAdapterPosition()).getPicUrl());   //wechat API 没有id，用图片来做唯一数据库索引
                intent.putExtra("title", mDataList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("url", mDataList.get(holder.getAdapterPosition()).getUrl());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.siv_wechat_item_image)
        SquareImageView mSquareIamgeView;
        @BindView(R.id.tv_wechat_item_title)
        TextView mWxTitle;
        @BindView(R.id.tv_wechat_item_from)
        TextView mWxFrome;
        @BindView(R.id.tv_wechat_item_time)
        TextView mWxTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
