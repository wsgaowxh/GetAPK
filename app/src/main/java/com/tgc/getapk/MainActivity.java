package com.tgc.getapk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tgc.getapk.adapter.APPAdapter;
import com.tgc.getapk.common.asynctask.CopyAsyncTask;
import com.tgc.getapk.common.utils.InitAPP;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
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


    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void startCopy(String id){
        int appID = Integer.parseInt(id);
        ResolveInfo app = dataList.get(appID);
        final String packName = app.activityInfo.packageName;
        CopyAsyncTask copyAsyncTask=new CopyAsyncTask(this,packName,MainActivity.this);
        copyAsyncTask.execute();
    }

    @OnPermissionDenied({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    public void toEnd(){
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    public void onTitleClick(String id) {
        MainActivityPermissionsDispatcher.startCopyWithPermissionCheck(this,id);
    }
}
