package com.centerm.sinopecsdktest.view;

import com.centerm.sinopecsdktest.code.BarcodeFormat;

/**
 * @author : dongSen
 * date : 2019-06-29 16:16
 * desc :
 */
public interface ScanListener {

    /**
     * 扫描结果
     *
     * @param data 摄像头扫码时 预览的结果 字节 data
     * @param width 图片宽
     * @param heigth 图片高
     */
    void onScanSuccess(byte[] data, int width, int heigth);

    /**
     * 处理打开相机出错
     */
    void onOpenCameraError();

    /**
     * 亮度分析回调
     */
    interface AnalysisBrightnessListener {

        void onAnalysisBrightness(boolean isDark);

    }
}