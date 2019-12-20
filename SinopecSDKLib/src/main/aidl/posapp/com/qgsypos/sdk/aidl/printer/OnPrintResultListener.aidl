// PrinterImpl.aidl
package posapp.com.qgsypos.sdk.aidl.printer;

// Declare any non-default types here with import statements


/**
 * 打印结果监听
 **/
interface OnPrintResultListener {
     /**
      *打印成功回调
      **/
     void onSuccess(String result);
     /**
      *扫码失败回调
      **/
     void onFailed(int errorCode,String message);
}