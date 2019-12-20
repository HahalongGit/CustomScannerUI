package com.centerm.sinopecsdktest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Qzhhh on 2016/10/27.
 */

public class RecyerViewAdpater extends RecyclerView.Adapter<RecyerViewAdpater.RvViewHolder> {
    private List<ModuleBean> itemLists;
    private Context mContext;
    private onItemListener mOnItemListener;

    public RecyerViewAdpater(List<ModuleBean> itemLists, Context context, onItemListener onItemListener) {
        this.itemLists = itemLists;
        mContext = context;
        mOnItemListener = onItemListener;
    }

    @Override
    public RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RvViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, null));
    }

    @Override
    public void onBindViewHolder(RvViewHolder holder, final int position) {
        holder.tv.setText(itemLists.get(position).getTitle());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    class RvViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public RvViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.rvitem_tv);
        }
    }

    interface onItemListener {
        public void onClick(int pos);
    }
}
