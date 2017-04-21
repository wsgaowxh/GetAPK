package com.tgc.getapk;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tgc.getapk.adapter.APPAdapter;
import com.tgc.getapk.utils.InitAPP;

import java.util.List;

public class MainActivity extends AppCompatActivity implements APPAdapter.OnTitleClickListener{

    private RecyclerView recyclerView;
    private List<ResolveInfo> dataList;
    private PackageManager pm;
    private APPAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pm=getPackageManager();
        initView();
        initData();

    }

    private void initData() {
        dataList= InitAPP.initApp(MainActivity.this,getPackageManager());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        appAdapter = new APPAdapter(MainActivity.this, dataList, pm);
        appAdapter.setOnTitleClickListener(this);
        recyclerView.setAdapter(appAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.main_rv);
    }

    @Override
    public void onTitleClick(String id) {
        int appID = Integer.parseInt(id);
        ResolveInfo app = dataList.get(appID);
        final String packName = app.activityInfo.packageName;
        CopyAsyncTask copyAsyncTask=new CopyAsyncTask(this,packName,MainActivity.this);
        copyAsyncTask.execute();

    }


}
