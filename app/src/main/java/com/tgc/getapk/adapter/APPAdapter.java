package com.tgc.getapk.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tgc.getapk.R;

import java.util.List;

/**
 * Created by TGC on 2017/4/16.
 */

public class APPAdapter extends RecyclerView.Adapter<APPAdapter.MyViewHolder> {
    private List<ResolveInfo> dataList;
    private Context context;
    private LayoutInflater layoutInflater;
    private PackageManager pm;

    private RecyclerView recyclerView;
    public OnTitleClickListener mListener;

    public APPAdapter(Context context, List<ResolveInfo> dataList, PackageManager pm) {
        this.context = context;
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
        this.pm = pm;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ResolveInfo resolveInfo = dataList.get(position);
        CharSequence appName = resolveInfo.loadLabel(pm);
        Drawable appIcon = resolveInfo.loadIcon(pm);
        holder.name.setText(appName);
        holder.icon.setImageDrawable(appIcon);

        holder.pick.setOnClickListener(new ClickListener(String.valueOf(position)));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.main_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class ClickListener implements View.OnClickListener {
        private String id;

        public ClickListener(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onTitleClick(id);
            }
        }
    }

    /*
    *当adapter与recyclerview关联的时候调用
    * */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    /*
    *当adapter与recyclerview失去关联的时候调用
    * */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView name;
        public Button pick;

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            name = (TextView) itemView.findViewById(R.id.item_name);
            pick = (Button) itemView.findViewById(R.id.item_pick);
        }
    }

    public void setOnTitleClickListener(OnTitleClickListener listener) {//自己写了一个方法，用上我们的接口
        mListener = listener;
    }

    public interface OnTitleClickListener {//自己写了一个点击事件的接口

        void onTitleClick(String id);
    }

}
