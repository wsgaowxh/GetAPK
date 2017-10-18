package com.tgc.getapk.common.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.tgc.getapk.R;
import com.tgc.getapk.base.App;
import com.tgc.getapk.common.utils.CopyUtil;
import com.tgc.getapk.common.utils.DialogUtils;

import java.util.List;

/**
 * Created by TGC on 2017/4/18.
 */

public class CopyAsyncTask extends AsyncTask<Void, Void, List<Boolean>> {
    private Context context;
    private String packName;
    private ProgressDialog progressDialog;

    public CopyAsyncTask(Context context, String packName) {
        this.context = context;
        this.packName = packName;
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
    protected List<Boolean> doInBackground(Void... params) {

        List<Boolean> resultList = CopyUtil.backupApp(packName);

        return resultList;
    }

    @Override
    protected void onPostExecute(List<Boolean> result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        if (result.size() > 0) {
            if (!result.get(0)) {
                //不存在
                if (result.get(1)) {
                    DialogUtils.alert(context, R.string.ok, R.string.success);
                } else {
                    DialogUtils.alert(context, R.string.ok, R.string.failed);
                }
            } else if (result.get(0)) {
                //已存在
                DialogUtils.alert(context, R.string.ok, R.string.is_has);
            }
        } else {
            Toast.makeText(App.getContext(), App.getContext().getString(R.string.not_found),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
