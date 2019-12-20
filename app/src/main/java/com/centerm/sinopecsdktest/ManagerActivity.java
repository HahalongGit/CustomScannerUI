package com.centerm.sinopecsdktest;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import posapp.com.qgsypos.sdk.aidl.IDeviceManager;
import posapp.com.qgsypos.sdk.aidl.sys.IPackageInstallListener;
import posapp.com.qgsypos.sdk.aidl.sys.IPackageUnInstallListener;
import posapp.com.qgsypos.sdk.aidl.sys.ISystemSettingService;


/**
 *  @author quzhenghan
 *  @date 2019-12-16 15:54
 */

public class ManagerActivity extends BaseTestActivity {
    private ISystemSettingService mSystemSettingService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        linearLayout = (LinearLayout) this.findViewById(R.id.tip_ll);
        scrollView = (ScrollView) this.findViewById(R.id.tip_sv);

    }

    @Override
    public void onDeviceConnected(IDeviceManager serviceManager) {
        try {
            mSystemSettingService = ISystemSettingService.Stub.asInterface(serviceManager.getDeviceManager());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void install(View view) {
        try {
            mSystemSettingService.installApkBack("/mnt/sdcard/test.apk", new IPackageInstallListener.Stub() {
                @Override
                public void onInstallFinished() throws RemoteException {
                    showMessage("静默安装成功");
                }

                @Override
                public void onInstallError(int errorId) throws RemoteException {
                    showMessage("静默安装失败:" + errorId);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void install1(View view) {
        try {
            boolean installApk = mSystemSettingService.installApk("/mnt/sdcard/test.apk");
            showMessage("静默安装同步方法结果：" + installApk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uninstall(View view) {
        try {
            mSystemSettingService.uninstallApkBack("posapp.com.qgsypos", new IPackageUnInstallListener.Stub() {
                @Override
                public void onUnInstallFinished() throws RemoteException {
                    showMessage("静默卸载posapp.com.qgsypos成功");
                }

                @Override
                public void onUnInstallError(int errorId) throws RemoteException {
                    showMessage("静默卸载posapp.com.qgsypos失败：" + errorId);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void uninstall1(View view) {
        try {
            boolean installApk = mSystemSettingService.uninstallApk("posapp.com.qgsypos");
            showMessage("静默卸载同步方法结果：" + installApk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reboot(View view) {
        try {
           mSystemSettingService.powerReboot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSystemTime(View view) {
        try {
            boolean result = mSystemSettingService.setSystemTime(15,20,6,2,12,2020);
            showMessage("设置日期2020-12-2 6:20:15 结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 不屏蔽
     * @param view
     */
    public void setHomeKeyEnabled(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("HOMEKEY", true);
        bundle.putBoolean("FUNCTIONKEY", true);
        bundle.putBoolean("STATUSBARKEY ", true);
        try {
            showMessage("设置不屏蔽");
            mSystemSettingService.setSystemFunction(bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 屏蔽
     * @param view
     */
    public void setHomeKeyDisabled(View view) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("HOMEKEY", false);
        bundle.putBoolean("FUNCTIONKEY", false);
        bundle.putBoolean("STATUSBARKEY ", false);
        try {
            showMessage("设置屏蔽");
            mSystemSettingService.setSystemFunction(bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
