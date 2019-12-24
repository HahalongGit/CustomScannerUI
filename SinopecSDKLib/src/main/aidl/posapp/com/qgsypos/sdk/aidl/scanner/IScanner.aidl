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

     /**
        * 对调用Camera相机预览结果解码
        * @param type 声明传递的数据类型 type 1为相机预览帧数据，2为本地相册bitmap的字节数组
        * @param data 解码字节数组
        * @param width 预览图的宽
        * @param heigth 预览图高
        */
//     String decode(int type,in byte[] data, int width, int height);

}
