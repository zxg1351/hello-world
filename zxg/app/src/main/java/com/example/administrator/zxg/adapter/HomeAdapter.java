package com.example.administrator.zxg.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zxg.MainActivity;
import com.example.administrator.zxg.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mList = new ArrayList<>();
    public HomeAdapter(Context context,List<String> mData) {
        mContext = context;
        mList = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                mContext).inflate(R.layout.item_grid_layout, parent,
                false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }


    public interface OnItemClickLitener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }
    private OnItemClickLitener mOnItemClickLitener = null;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));

        if (mOnItemClickLitener !=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
