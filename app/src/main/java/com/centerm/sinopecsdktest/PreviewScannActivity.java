package com.centerm.sinopecsdktest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.centerm.sinopecsdktest.code.BarcodeFormat;
import com.centerm.sinopecsdktest.code.CodeResult;
import com.centerm.sinopecsdktest.util.BitmapUtil;
import com.centerm.sinopecsdktest.util.SoundPoolUtil;
import com.centerm.sinopecsdktest.view.ScanActivityDelegate;
import com.centerm.sinopecsdktest.view.ScanListener;
import com.centerm.sinopecsdktest.view.ScanView;

import java.io.ByteArrayOutputStream;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import posapp.com.qgsypos.sdk.aidl.IDeviceManager;
import posapp.com.qgsypos.sdk.aidl.scanner.IScanner;

public class PreviewScannActivity extends BaseTestActivity implements ScanListener,
        View.OnClickListener {

    private static final int PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int PERMISSIONS_REQUEST_STORAGE = 2;
    private static final int CODE_SELECT_IMAGE = 100;

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

        requestCameraPermission();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CODE_SELECT_IMAGE) {
            decodeImage(data);
            finish();
        }
    }

    @Override
    public void onScanSuccess(byte[] data, int width, int heigth) {// onScanSuccess多次回调，播放不响
        try {
            result = iScanner.decode(data, width, heigth);
            Log.e("TAG", "onScanSuccess--" + result);
            if (!TextUtils.isEmpty(result)) {
//                mScanView.stopScan();
//                mSoundPoolUtil.play();
//                finish();
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
            requestStoragePermission();
        }
    }

    private void decodeImage(Intent intent) {
        Uri selectImageUri = intent.getData();
        if (selectImageUri == null) {
            return;
        }
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectImageUri, filePathColumn, null, null, null);
        if (cursor == null) {
            return;
        }
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        Bitmap bitmap = BitmapUtil.getDecodeAbleBitmap(picturePath);
        if (bitmap == null) {
            return;
        }


//        Bitmap bitmap0 = readBitmapFromAssets("barcode.png");
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 90, outStream);
//        outStream.toByteArray();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();
        Log.e("TAG", "datas--size:" + datas.length);

        // TODO: [runningDigua create at 2019/12/23] 解析二维码图片，图片要小于1M
//        CodeResult result = BarcodeReader.getInstance().read(bitmap);
//        if (result == null) {
//            Log.e("Scan >>> ", "no code");
//            return;
//        } else {
//            Log.e("Scan >>> ", result.getText());
//        }
//        showResult(result.getText());
    }

    /**
     * 获取摄像头权限（实际测试中，使用第三方获取权限工具，可能造成摄像头打开失败）
     */
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        }
    }

    private void requestStoragePermission() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSIONS_REQUEST_STORAGE);
        } else {
            Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(albumIntent, CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mScanView.openCamera();
                mScanView.startScan();
            }
            return;
        } else if (requestCode == PERMISSIONS_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent albumIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(albumIntent, CODE_SELECT_IMAGE);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
