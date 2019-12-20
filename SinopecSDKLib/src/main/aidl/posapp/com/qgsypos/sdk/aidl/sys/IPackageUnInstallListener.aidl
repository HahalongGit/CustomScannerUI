// ISystemSettingService.aidl
package posapp.com.qgsypos.sdk.aidl.sys;

// Declare any non-default types here with import statements

interface IPackageUnInstallListener {

  /**卸载成功*/
  	void onUnInstallFinished();

  	/**卸载失败*/
  	void onUnInstallError(int errorId);

}
