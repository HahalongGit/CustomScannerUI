// IDeviceInfo.aidl
package posapp.com.qgsypos.sdk.aidl.device;

// Declare any non-default types here with import statements

interface IDeviceInfo {

  /**
   *获获取设备TUSN号
   **/
   String getDeviceTUSN();
  /**
   *获取设备IMEI号
   **/
   String getDeviceIMEI();

   /**
    *获取设备IMSI号
    **/
   String getDeviceIMSI();

   /**
    *获取终端型号
    **/
   String getDeviceType();

   /**
    *获取ROM版本号
    **/
   String getROMVersionName();
   /**
    *获取Android内核版本号
    **/
   String getAndroidCore();

}
