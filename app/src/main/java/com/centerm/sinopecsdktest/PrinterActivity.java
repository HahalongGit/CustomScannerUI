package com.centerm.sinopecsdktest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import posapp.com.qgsypos.sdk.aidl.IDeviceManager;
import posapp.com.qgsypos.sdk.aidl.printer.IPrinter;
import posapp.com.qgsypos.sdk.aidl.printer.OnPrintResultListener;

/**
 * Created by Qzhhh on 2016/11/9.
 */

public class PrinterActivity extends BaseTestActivity {
    private IPrinter mPrinter;
    //    private EditText textEdit, qrEdit, greyEdit, algin_tv;
    private EditText greyEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        linearLayout = (LinearLayout) this.findViewById(R.id.tip_ll);
        scrollView = (ScrollView) this.findViewById(R.id.tip_sv);
//        textEdit = findViewById(R.id.text_et);
//        qrEdit = findViewById(R.id.qrtext_et);
        greyEdit = findViewById(R.id.greytext_et);
//        algin_tv = findViewById(R.id.algin_tv);
    }

    @Override
    public void onDeviceConnected(IDeviceManager serviceManager) {

        try {
            mPrinter = IPrinter.Stub.asInterface(serviceManager.getPrinter());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getStatus(View view) {
        try {
            int state = mPrinter.getPrinterState();
            showMessage("打印机返回的状态：" + state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGrey(View view) {
        try {
            if (TextUtils.isEmpty(greyEdit.getText().toString())) {
                showMessage("请输入灰度");
                return;
            }
            int state = Integer.valueOf(greyEdit.getText().toString());
            mPrinter.setPrinterGray(state);
            showMessage("打印机设置灰度成功：" + state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void feedLine(View view) {
        try {
            mPrinter.spitPaper(5);
            showMessage("添加走纸完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print(View view) {
        try {
            List<PrinterParams> paramList = new ArrayList<PrinterParams>();
            paramList.add(getPrintText("测试数据及"));
            paramList.add(getPrintText("测试数据及1212"));
            paramList.add(getPrintText("测试数据及21"));
            paramList.add(getPrintText("测试数据21及"));
            paramList.add(getPrintText("测试数据及"));
            paramList.add(getPrintText("测试数据1212及"));
            paramList.add(getPrintText("测试数据2121及"));
            paramList.add(getPrintText("测试数据21212及"));
            paramList.add(getPrintText("212121212"));
            paramList.add(getPrintText("测试数据dsfdsf及"));
            PrinterParams PrinterParams1 = new PrinterParams();
            PrinterParams1.setDataType(PrinterParams.DATATYPE.TEXT);// 文本打印
            PrinterParams1.setText("打印测试数据");
            PrinterParams1.setTextSize(30);
            PrinterParams1.setItalic(true);
            PrinterParams1.setUnderLine(true);
            PrinterParams1.setBold(true);
            PrinterParams1.setStrikeThruText(true);
            PrinterParams1.setAlign(PrinterParams.ALIGN.CENTER);

            PrinterParams PrinterParams3 = new PrinterParams();
            PrinterParams3.setDataType(PrinterParams.DATATYPE.FEED_LINE);//走纸
            PrinterParams3.setFeedlineNum(2);

            PrinterParams PrinterParams4 = new PrinterParams();
            PrinterParams4.setDataType(PrinterParams.DATATYPE.BARCODE);//一维码
            PrinterParams4.setBarcodeHeight(150);
            PrinterParams4.setBarcodeWidth(4);
            PrinterParams4.setText("12345678");

            PrinterParams PrinterParams5 = new PrinterParams();
            PrinterParams5.setDataType(PrinterParams.DATATYPE.QRCODE);//二维码
            PrinterParams5.setText("1234567890123456");
            PrinterParams5.setQrcodeWidth(300);
            PrinterParams5.setAlign(PrinterParams.ALIGN.CENTER);
            PrinterParams PrinterParams6 = new PrinterParams();
            PrinterParams6.setDataType(PrinterParams.DATATYPE.IMAGE);//图片
            InputStream ins;
            ins = getResources().getAssets().open("bmp/canvas.bmp");
            Bitmap bmp = BitmapFactory.decodeStream(ins);
            PrinterParams6.setBitmap(bitmap2Bytes(bmp));
            PrinterParams6.setImgHeigth(100);
            PrinterParams6.setImgWidth(200);
            PrinterParams6.setAlign(PrinterParams.ALIGN.CENTER);
            PrinterParams PrinterParams7 = new PrinterParams();
            PrinterParams7.setAlign(PrinterParams.ALIGN.LEFT);
            PrinterParams7.setText("下划线加粗测试");
            PrinterParams7.setUnderLine(true);
            PrinterParams7.setTextSize(25);
            PrinterParams7.setBold(true);
            PrinterParams7.setLineSpace(30);

            paramList.add(PrinterParams1);
            paramList.add(PrinterParams3);
            paramList.add(PrinterParams4);
            paramList.add(PrinterParams5);
            paramList.add(PrinterParams6);
            paramList.add(PrinterParams7);
            String json = new Gson().toJson(paramList);
            mPrinter.printData(json, new OnPrintResultListener.Stub() {
                @Override
                public void onSuccess(String result) throws RemoteException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("打印完成：" + result);
                        }
                    });
                }

                @Override
                public void onFailed(int errorCode, String message) throws RemoteException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showMessage("打印失败：" + errorCode + " message：" + message);
                        }
                    });
                }
            });//打印数据，传递json参数

            mPrinter.spitPaper(10);//打印机走纸

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private PrinterParams getPrintText(String text) {
        PrinterParams printerParams1 = new PrinterParams();
        printerParams1.setDataType(PrinterParams.DATATYPE.TEXT);// 文本打印
        printerParams1.setText(text);
        return printerParams1;
    }
}
