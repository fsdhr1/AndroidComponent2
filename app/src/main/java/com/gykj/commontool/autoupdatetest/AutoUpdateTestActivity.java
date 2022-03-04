package com.gykj.commontool.autoupdatetest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gykj.autoupdate.BaseConfig.Constants_test;
import com.gykj.autoupdate.utils.PermisionUtils;
import com.gykj.autoupdate.utils.UpdateAppUtil;
import com.gykj.commontool.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by ren on 2021/4/6
 */
public class AutoUpdateTestActivity extends AppCompatActivity {
    private String versionName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_autoupdate_test);
        initView();
    }

    private void initView() {
        getVersionInfo(AutoUpdateTestActivity.this);
        TextView tvVersion = findViewById(R.id.tv_version);
        String str = "当前版本：V%s";
        tvVersion.setText(String.format(str, versionName));
    }

    // 检测新版本
    public void checkVersion(View view) {
        // 检查权限
        PermisionUtils.verifyStoragePermissions(AutoUpdateTestActivity.this);

        // 获取util单例
        // 1.使用默认Authority：${applicationId}.autoupdatefileprovider
//        UpdateAppUtil util = UpdateAppUtil.getInstance(MainActivity.this);
        // 2.自定义外部Authority
        UpdateAppUtil util = UpdateAppUtil.getInstance(AutoUpdateTestActivity.this, "com.gykj.commontool.autoupdatefileprovider");


        // 自定义弹窗
        //util.setCustomUpdateDialog(R.drawable.ic_launcher_background, R.color.white, R.color.color_dialog_defult);

        // 检测更新
        /**
         * 别在这改key!!!!!
         * 别在这改key!!!!!
         * 别在这改key!!!!!
         *
         * 要测试，去Constants加个常量，测试完换回来，老乱了
         */
        String appkey = Constants.AutoUpdateDemo_Key;
        String signType = "debug"; // release / debug

        util.checkVersion(appkey, signType, Constants_test.CHECK_VERSION, new UpdateAppUtil.hotFixCallBack() {
            @Override
            public void onHotFixVetrsionCallBack(final int hotFixVersion) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), " --- hotFixVersion " + hotFixVersion + " --- ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    // 获取当前版本信息
    private void getVersionInfo(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


}
