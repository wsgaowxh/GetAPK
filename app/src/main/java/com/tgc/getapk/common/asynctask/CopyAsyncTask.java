package com.tgc.getapk.common.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;

import com.tgc.getapk.R;
import com.tgc.getapk.base.App;
import com.tgc.getapk.common.utils.CopyUtil;
import com.tgc.getapk.common.utils.DialogUtils;
import com.tgc.getapk.common.utils.PreferencesHelper;

import java.util.List;

/**
 * Created by TGC on 2017/4/18.
 */

public class CopyAsyncTask extends AsyncTask<Void, Void, Integer> {
    private Context context;
    private List<Integer> appID;
    private List<ResolveInfo> dataList;
    private ProgressDialog progressDialog;

    public CopyAsyncTask(Context context, List<Integer> appID, List<ResolveInfo> dataList) {
        this.context = context;
        this.appID = appID;
        this.dataList = dataList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getString(R.string.tiqu));
        progressDialog.setMessage(context.getString(R.string.tiquzhong));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        int resultCode = CopyUtil.backupApp(appID, dataList);

        return resultCode;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        String str = App.getAppResources().getString(R.string.success);
        str = String.format(str, PreferencesHelper.getPath());
        switch (result) {
            case 1:
                DialogUtils.alert(context, R.string.ok, str);
                break;
            case 2:
                DialogUtils.alert(context, R.string.ok, R.string.failed);
                break;
            case 3:
                DialogUtils.alert(context, R.string.ok, str);
                break;
            default:
                break;
        }
//        if (result.size() > 0) {
//            if (!result.get(0)) {
//                //不存在
//                if (result.get(1)) {
//                    DialogUtils.alert(context, R.string.ok, R.string.success);
//                } else {
//                    DialogUtils.alert(context, R.string.ok, R.string.failed);
//                }
//            } else if (result.get(0)) {
//                //已存在
//                DialogUtils.alert(context, R.string.ok, R.string.is_has);
//            }
//        } else {
//            Toast.makeText(App.getContext(), App.getContext().getString(R.string.not_found),
//                    Toast.LENGTH_SHORT).show();
//        }
    }
}
