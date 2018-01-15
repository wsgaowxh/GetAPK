package com.tgc.getapk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tgc.getapk.R;
import com.tgc.getapk.base.App;

import java.io.File;
import java.util.List;

/**
 * Created by tgc on 2018/1/15.
 */

public class DirAdapter extends RecyclerView.Adapter<DirAdapter.DirViewHolder> {

    public OnItemClickListener onItemClickListener;
    private List<File> fileList;

    public DirAdapter(List<File> fileList) {
        this.fileList = fileList;
    }

    public void updateFilePath(List<File> fileList) {
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    @Override
    public DirViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(App.getContext(), R.layout.dir_item, null);
        return new DirViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DirViewHolder holder, final int position) {
        File file = fileList.get(position);
        holder.tvName.setText(file.getName());
        if (file.isDirectory()) {
            holder.tvDetail.setText(R.string.directory);
            holder.ivType.setImageResource(R.mipmap.folder_style_blue);
        } else {
            holder.tvDetail.setText("");
            holder.ivType.setImageResource(R.mipmap.file_style_blue);
        }
        holder.layoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.click(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void click(int position);
    }

    class DirViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layoutRoot;
        private ImageView ivType;
        private TextView tvName;
        private TextView tvDetail;

        public DirViewHolder(View itemView) {
            super(itemView);
            ivType = (ImageView) itemView.findViewById(R.id.iv_type);
            layoutRoot = (RelativeLayout) itemView.findViewById(R.id.layout_item_root);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDetail = (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }
}
