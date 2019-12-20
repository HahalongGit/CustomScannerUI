// ISystemSettingService.aidl
package posapp.com.qgsypos.sdk.aidl.sys;
import posapp.com.qgsypos.sdk.aidl.sys.IPackageInstallListener;
import posapp.com.qgsypos.sdk.aidl.sys.IPackageUnInstallListener;

// Declare any non-default types here with import statements

interface ISystemSettingService {

  /**
   *安装APK (非界面)静默方式
   **/
   void installApkBack(String apkPath,IPackageInstallListener listener) ;
  /**
   *安装APK
   **/
   boolean installApk(String apkPath);

  /**
   *静默卸载APP
   **/
   void uninstallApkBack(String pkgName,IPackageUnInstallListener listener) ;

  /**
   *卸载APK
   **/
   boolean uninstallApk(String pkgName);
  /**
   *更新系统时间
   **/
   boolean setSystemTime(int second,int minute,int hour,int day,int month,int year);

  /**
   *重启终端
   **/
   void powerReboot();

  /**
   *屏蔽home键、状态栏、menu
   **/
   void setSystemFunction(in Bundle bundle);

}
