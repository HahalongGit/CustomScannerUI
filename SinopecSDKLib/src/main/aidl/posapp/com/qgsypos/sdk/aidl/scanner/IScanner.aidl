// IScanner.aidl
package posapp.com.qgsypos.sdk.aidl.scanner;

import posapp.com.qgsypos.sdk.aidl.scanner.OnScanResultListener;
import posapp.com.qgsypos.sdk.aidl.scanner.CameraBeanZbar;
// Declare any non-default types here with import statements

interface IScanner {

    /**
     * 查看扫码器状态
     */
    int checkScan();

    /**
     * 停止扫码
     */
    void stopScan();

    /**
     * 开始扫码
     */
    void startScan(in CameraBeanZbar param,OnScanResultListener listener);

    /**
     * 对相机预览结果解码，调用Camera
     * @param data 预览图返回的数据
     * @param width 预览图的宽
     * @param heigth 预览图高
     */
    String decode(in byte[] data, int width, int height);

}
