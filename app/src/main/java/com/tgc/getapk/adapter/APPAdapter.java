package com.tgc.getapk.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tgc.getapk.R;
import com.tgc.getapk.base.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TGC on 2017/4/16.
 */

public class APPAdapter extends RecyclerView.Adapter<APPAdapter.MyViewHolder> {
    private List<ResolveInfo> dataList;
    private Context context;
    private LayoutInflater layoutInflater;
    private PackageManager pm;
    private SparseBooleanArray checkStates;
    private List<Integer> checkList;

    private RecyclerView recyclerView;
//    public OnTitleClickListener mListener;
    public OnIconClickListener listener;

    public APPAdapter(Context context, List<ResolveInfo> dataList, PackageManager pm) {
        this.context = context;
        this.dataList = dataList;
        this.layoutInflater = LayoutInflater.from(context);
        this.pm = pm;
        checkStates = new SparseBooleanArray();
        checkList = new ArrayList<>();
    }

    public void setData(List<ResolveInfo> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<ResolveInfo> getData() {
        return dataList;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ResolveInfo resolveInfo = dataList.get(position);
        CharSequence appName = resolveInfo.loadLabel(pm);
        Drawable appIcon = resolveInfo.loadIcon(pm);
        holder.name.setText(appName);
        holder.icon.setImageDrawable(appIcon);

        holder.pick.setTag(position);
        holder.pick.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) buttonView.getTag();
                if (isChecked) {
                    checkStates.put(pos, true);
                    if (checkList.indexOf(pos) == -1) {
                        checkList.add(pos);
                    }
                    holder.rl.setBackgroundColor(App.getContext().getResources().getColor(R.color.color_select));
                } else {
                    checkStates.delete(pos);
                    int i = checkList.indexOf(pos);
                    if (i != -1) {
                        checkList.remove(i);
                    }
                    holder.rl.setBackgroundColor(App.getContext().getResources().getColor(R.color.white));
                }
            }
        });
        boolean check = checkStates.get(position, false);
        holder.pick.setChecked(check);
        if (check) {
            holder.rl.setBackgroundColor(App.getContext().getResources().getColor(R.color.color_select));
        } else {
            holder.rl.setBackgroundColor(App.getContext().getResources().getColor(R.color.white));
        }


//        holder.pick.setOnClickListener(new ClickListener(position));
        holder.icon.setOnClickListener(new ClickListener(position));

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

    public List<Integer> getCheckList() {
        return checkList;
    }

    public void selectAll() {
        for (int i = 0; i < dataList.size(); i++) {
            int index = checkList.indexOf(i);
            if (index == -1) {
                checkStates.put(i, true);
                checkList.add(i);
            }
        }
        notifyDataSetChanged();
    }

    public void antiSelectAll() {
        for (int i = 0; i < dataList.size(); i++) {
            if (checkStates.get(i)) {
                checkStates.put(i, false);
                int index = checkList.indexOf(i);
                if (index != -1) {
                    checkList.remove(index);
                }
            } else {
                checkStates.put(i, true);
                checkList.add(i);
            }
        }
        notifyDataSetChanged();
    }

    public class ClickListener implements View.OnClickListener {
        private int id;

        public ClickListener(int id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onIconClick(id);
            }
        }
    }

//    public class ClickListener implements View.OnClickListener {
//        private int id;
//
//        public ClickListener(int id) {
//            this.id = id;
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (mListener != null) {
//                mListener.onTitleClick(id);
//            }
//        }
//    }

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
        public CheckBox pick;
        public RelativeLayout rl;

        public MyViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.item_icon);
            name = (TextView) itemView.findViewById(R.id.item_name);
            pick = (CheckBox) itemView.findViewById(R.id.item_pick);
            rl = (RelativeLayout) itemView.findViewById(R.id.item_rl);
        }
    }

    public void setOnIconClickListener(OnIconClickListener listener) {
        this.listener = listener;
    }

    public interface OnIconClickListener {
        void onIconClick(int appId);
    }

//    public void setOnTitleClickListener(OnTitleClickListener listener) {//自己写了一个方法，用上我们的接口
//        mListener = listener;
//    }
//
//    public interface OnTitleClickListener {//自己写了一个点击事件的接口
//
//        void onTitleClick(int id);
//    }

}
