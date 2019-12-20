// IDeviceManager.aidl
package posapp.com.qgsypos.sdk.aidl;

// Declare any non-default types here with import statements

interface IDeviceManager {
    /**
     * 获取扫码器对象
     *
     */
     IBinder getScanner();

    /**
     * 获取扫码器对象
     *
     */
    IBinder getPrinter();

    /**
     * 获取设备信息对象
     *
     */
    IBinder getDeviceInfo();

     /**
      * 获取终端管理对象
      *
      */
    IBinder getDeviceManager();

}
