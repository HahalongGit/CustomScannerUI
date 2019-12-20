package com.centerm.sinopecsdktest;

import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centerm.sinopecsdktest.code.BarcodeFormat;
import com.centerm.sinopecsdktest.util.SoundPoolUtil;
import com.centerm.sinopecsdktest.view.ScanActivityDelegate;
import com.centerm.sinopecsdktest.view.ScanListener;
import com.centerm.sinopecsdktest.view.ScanView;

import posapp.com.qgsypos.sdk.aidl.IDeviceManager;
import posapp.com.qgsypos.sdk.aidl.scanner.IScanner;

public class PreviewScannActivity extends BaseTestActivity implements ScanListener,
        View.OnClickListener {

    private ScanView mScanView;

    private SoundPoolUtil mSoundPoolUtil;

    private ScanActivityDelegate.OnScanDelegate scanDelegate;
    private ScanActivityDelegate.OnClickAlbumDelegate clickAlbumDelegate;

    private TextView titleTxt;
    private TextView albumTxt;

    private IDeviceManager mIDeviceService;
    private IScanner iScanner;

    String result = null;

    private boolean isPlayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_scann);

        mScanView = findViewById(R.id.surface_view_scan);
        mScanView.setScanListener(this);

        LinearLayout titleLayout = findViewById(R.id.layout_scan_title);
        ImageView backImg = findViewById(R.id.image_scan_back);
        titleTxt = findViewById(R.id.text_view_scan_title);
        albumTxt = findViewById(R.id.text_view_scan_album);
        mScanView = findViewById(R.id.surface_view_scan);
        backImg.setOnClickListener(this);
        albumTxt.setOnClickListener(this);

        scanDelegate = ScanActivityDelegate.getInstance().getScanDelegate();
        clickAlbumDelegate = ScanActivityDelegate.getInstance().getOnClickAlbumDelegate();

        mSoundPoolUtil = new SoundPoolUtil();
        mSoundPoolUtil.loadDefault(this);

//        mScanView.setScanMode(option.getScanMode());
//        mScanView.setBarcodeFormat(option.getBarcodeFormat());

//        ScanBoxView scanBox = mScanView.getScanBox();

    }

    @Override
    public void onDeviceConnected(IDeviceManager serviceManager) {
        try {
            mIDeviceService = serviceManager;
            iScanner = IScanner.Stub.asInterface(serviceManager.getScanner());
            mScanView.setiScanner(iScanner);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScanView.openCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mScanView.startScan();  // 显示扫描框，并开始识别

        if (false) {//是否连续扫描的时候扩相机倍数
            mScanView.resetZoom();  // 重置相机扩大倍数
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanView.stopScan();
        mScanView.closeCamera(); // 关闭摄像头预览，并且隐藏扫描框
    }

    @Override
    protected void onDestroy() {
        mScanView.onDestroy(); // 销毁二维码扫描控件
        mSoundPoolUtil.release();
        super.onDestroy();
        ScanActivityDelegate.getInstance().setScanResultDelegate(null);
        ScanActivityDelegate.getInstance().setOnClickAlbumDelegate(null);
    }

    @Override
    public void onScanSuccess(byte[] data, int width, int heigth) {// onScanSuccess多次回调，播放不响
        try {
            result = iScanner.decode(data, width, heigth);
            Log.e("TAG", "onScanSuccess--" + result);
            if (!TextUtils.isEmpty(result)) {
                mScanView.stopScan();
                mSoundPoolUtil.play();
                finish();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onOpenCameraError() {
        Log.e("onOpenCameraError", "onOpenCameraError");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.image_scan_back) {
            finish();
        } else if (id == R.id.text_view_scan_album) {
            if (clickAlbumDelegate != null) {
                clickAlbumDelegate.onClickAlbum(this);
            }
        }
    }

}
