package com.gykj.commontool;

import android.app.Application;

import com.grandtech.mapframe.core.maps.GMapView;
import com.gykj.grandphotos.config.GrandPhotoHelper;
import com.gykj.mvpbasemodule.MvpBaseModule;
import com.gykj.networkmodule.NetworkHelper;
import com.mapbox.android.core.crashreporter.CrashReport;

/**
 * @author zyp
 * 2021/2/26
 */
public class MyApplication extends Application {

    public GMapView gMapView;

    @Override
    public void onCreate() {
        super.onCreate();
        MvpBaseModule.init(this);
        NetworkHelper.init(this, "http://gykj123.cn:8849/grandtech-service-snyzt/");
        NetworkHelper.setToken(this, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLogL_kvbPolYrkuKrkuroiLCJjb250ZXh0VXNlcklkIjoicDlGQjJDQTM4RDREOTQ0MjA4QjAwNDI2MkMwMEM2Rjc4IiwiY29udGV4dE5hbWUiOiJnZW8iLCJjb250ZXh0RGVwdElkIjoiMSIsImNvbnRleHRBcHBsaWNhdGlvbklkIjoiMSIsImV4cCI6MjYxMzIxODk2NDd9.dM92KheaX7Yp7GsHB_iFubyzV8KClL9mFKDy0a1AwXM");
        NetworkHelper.addDefaultSuccessCode(200);
        //
        GrandPhotoHelper.init("com.gykj.commontool.autoupdatefileprovider");
    }
}
