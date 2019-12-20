package com.centerm.sinopecsdktest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import posapp.com.qgsypos.sdk.aidl.IDeviceManager;
import posapp.com.qgsypos.sdk.aidl.scanner.CameraBeanZbar;
import posapp.com.qgsypos.sdk.aidl.scanner.IScanner;
import posapp.com.qgsypos.sdk.aidl.scanner.OnScanResultListener;


/**
 * Created by Qzhhh on 2016/11/7.
 */

public class ScannerActivity extends BaseTestActivity {
    private Handler mHandler = new Handler();
    private EditText timeout;
    private CheckBox cb, cb_beep, cb_pi, cb_show;
    private RadioGroup rgScan;
    private RadioButton rb_front, rb_back;
    private IDeviceManager mIDeviceService;
    private IScanner iScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        linearLayout = (LinearLayout) this.findViewById(R.id.tip_ll);
        scrollView = (ScrollView) this.findViewById(R.id.tip_sv);
        timeout = findViewById(R.id.timeout_et);
        cb = findViewById(R.id.cb);
        cb_beep = findViewById(R.id.cb_beep);
        cb_pi = findViewById(R.id.cb_pi);
        cb_show = findViewById(R.id.cb_show);
        rgScan = findViewById(R.id.rg_scan);
        rb_front = findViewById(R.id.rb_front);
        rb_back = findViewById(R.id.rb_back);
    }

    @Override
    public void onDeviceConnected(IDeviceManager serviceManager) {
        try {
            mIDeviceService = serviceManager;
            iScanner = IScanner.Stub.asInterface(serviceManager.getScanner());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void startScan(View view) throws RemoteException {
        CameraBeanZbar zbar = new CameraBeanZbar();
        if (rb_front.isChecked()) {
            zbar.setCameraId(1);
        } else if (rb_back.isChecked()) {
            zbar.setCameraId(0);
        }
        if (cb_beep.isChecked()) {
            zbar.setBeep(1);
        } else {
            zbar.setBeep(0);
        }
        zbar.setPersist(cb_pi.isChecked());
        zbar.setShowPreview(cb_show.isChecked());
        String timeout1 = timeout.getText().toString();
        zbar.setTime(TextUtils.isEmpty(timeout1) ? 60 * 1000 : Long.valueOf(timeout1));
        iScanner.startScan(zbar, new OnScanResultListener.Stub() {
            @Override
            public void onSuccess(String result) throws RemoteException {
                mHandler.post(() -> {
                    showMessage("扫码成功：" + result, Color.BLACK);
                    mHandler.removeCallbacksAndMessages(null);
                });
            }

            @Override
            public void onFailed(int errorCode, String message) throws RemoteException {
                mHandler.post(() -> {
                    showMessage("扫码错误：" + errorCode + " message：" + message, Color.BLACK);
                    mHandler.removeCallbacksAndMessages(null);
                });
            }

        });
        if (cb.isChecked()) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopScan(null);
                }
            }, 5000);
        }
    }


    public void stopScan(View view) {
        try {
            iScanner.stopScan();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
