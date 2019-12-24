package com.centerm.sinopecsdktest.view;

import android.content.Context;
import android.util.AttributeSet;

import com.centerm.sinopecsdktest.util.SaveImageUtil;

import posapp.com.qgsypos.sdk.aidl.scanner.IScanner;

/**
 * @author : dongSen
 * date : 2019-06-29 16:18
 * desc : 二维码界面使用类
 */
public class ScanView extends BarCoderView implements ScanBoxView.ScanBoxClickListener {//BarcodeReader.ReadCodeListener

    /**
     * 混合扫描模式（默认），扫描4次扫码框里的内容，扫描1次以屏幕宽为边长的内容
     */
    public static final int SCAN_MODE_MIX = 0;
    /**
     * 只扫描扫码框里的内容
     */
    public static final int SCAN_MODE_TINY = 1;
    /**
     * 扫描以屏幕宽为边长的内容
     */
    public static final int SCAN_MODE_BIG = 2;

    private static final int DARK_LIST_SIZE = 4;

    private boolean isStop;
    private boolean isDark;
    private int showCounter;

    public void setiScanner(IScanner iScanner) {
        this.iScanner = iScanner;
    }

    //    private BarcodeReader reader;

    public ScanView(Context context) {
        this(context, null);
    }

    public ScanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScanBoxView.setScanBoxClickListener(this);
//        reader = BarcodeReader.getInstance();
    }

    @Override
    public void onPreviewFrame(byte[] data, int left, int top, int width, int height, int rowWidth, int rowHeight) {
        if (isStop) {
            return;
        }
        SaveImageUtil.saveData(data,left,top,width,height,rowWidth);
        if (mScanListener != null) {
            mScanListener.onScanSuccess(data, width, height);
        }
    }

    @Override
    public void startScan() {
        super.startScan();
        isStop = false;
    }

    @Override
    public void stopScan() {
        super.stopScan();
        isStop = true;
    }

    public void resetZoom() {
        setZoomValue(0);
    }

    @Override
    public void onFlashLightClick() {
        // 第一次onCreate
        isDark = true;
        mCameraSurface.toggleFlashLight(isDark);
    }

}
