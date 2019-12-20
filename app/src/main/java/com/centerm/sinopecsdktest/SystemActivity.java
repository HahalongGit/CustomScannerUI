package com.centerm.sinopecsdktest;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import posapp.com.qgsypos.sdk.aidl.IDeviceManager;
import posapp.com.qgsypos.sdk.aidl.device.IDeviceInfo;


public class SystemActivity extends BaseTestActivity {
    private static final String TAG = "SystemActivity";
    private IDeviceInfo mDeviceInfo;
    private boolean mIsHomeKeyEnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        linearLayout = (LinearLayout) this.findViewById(R.id.tip_ll);
        scrollView = (ScrollView) this.findViewById(R.id.tip_sv);
    }

    @Override
    public void onDeviceConnected(IDeviceManager serviceManager) {
        try {
            mDeviceInfo = IDeviceInfo.Stub.asInterface(serviceManager.getDeviceInfo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void getSerialNo(View view) {
        try {
            String sn = mDeviceInfo.getDeviceTUSN();
            showMessage(sn != null ? "获取终端sn号:" + sn : "获取终端sn号失败");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }

    public void getIMSI(View view) {
        try {
            String imsi = mDeviceInfo.getDeviceIMSI();
            showMessage(imsi != null ? "获取终端IMSI:" + imsi : "获取终端IMSI失败");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }

    public void getIMEI(View view) {
        try {
            String imei = mDeviceInfo.getDeviceIMEI();
            showMessage(imei != null ? "获取终端IMEI:" + imei : "获取终端IMEI失败");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }


    public void getModel(View view) {
        try {
            String type = mDeviceInfo.getDeviceType();
            showMessage(type != null ? "获取设备型号:" + type : "获取设备型号失败");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }

    public void getAndroidOSVersion(View view) {
        try {
            String os = mDeviceInfo.getAndroidCore();
            showMessage(os != null ? "获取android系统版本号:" + os : "获取android系统版本号失败");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }


    public void getROMVersion(View view) {
        try {
            String version = mDeviceInfo.getROMVersionName();
            showMessage(version != null ? "获取系统版本号:" + version : "获取系统版本号失败");
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
    }

//    /**
//     * 不屏蔽
//     * @param view
//     */
//    public void setHomeKeyEnabled(View view) {
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("HOMEKEY", true);
//        bundle.putBoolean("FUNCTIONKEY", true);
//        bundle.putBoolean("STATUSBARKEY ", true);
//        try {
//            showMessage("设置不屏蔽");
//            mDeviceInfo.setSystemFunction(bundle);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 屏蔽
//     * @param view
//     */
//    public void setHomeKeyDisabled(View view) {
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("HOMEKEY", false);
//        bundle.putBoolean("FUNCTIONKEY", false);
//        bundle.putBoolean("STATUSBARKEY ", false);
//        try {
//            showMessage("设置屏蔽");
//            mDeviceInfo.setSystemFunction(bundle);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
}
