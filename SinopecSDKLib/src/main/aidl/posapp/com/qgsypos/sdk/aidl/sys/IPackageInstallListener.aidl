// ISystemSettingService.aidl
package posapp.com.qgsypos.sdk.aidl.sys;

// Declare any non-default types here with import statements

interface IPackageInstallListener {

   /**安装成功*/
 	void onInstallFinished();

 	/**安装失败*/
 	void onInstallError(int errorId);

}
