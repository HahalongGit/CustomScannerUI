// OnScanResultListener.aidl
package posapp.com.qgsypos.sdk.aidl.scanner;

// Declare any non-default types here with import statements

interface OnScanResultListener {

     /**
      *扫码成功回调
      * @param result result
      **/
     void onSuccess(String result);

      /**
       *扫码失败回调
       *@param errorCode 错误码
       *@param message 错误提示
       **/
      void onFailed(int errorCode,String message);
}
