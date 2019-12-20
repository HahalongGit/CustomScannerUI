package posapp.com.qgsypos.sdk.aidl.scanner;

/**
 * 扫码错误码
 *
 * @author runningDigua
 * @date 2019/12/12
 */
public interface ErrorCode {

    int ERROR_NONE = 0; //成功
    int ERROR_INIT_FAIL = 1; //初始化解码库失败
    int ERROR_INIT_ENGINE = 3; //初始化扫码模组失败
    int ERROR_AUTH_LICENSE = 4; // License认证失败
    int ERROR_NOT_FIND_DECODE_LIB = 5; // 找不到底层解码库
    int ERROR_OPEN_CAMERA = 6; // 打开摄像头失败
    int ERROR_NOT_SUPPORT = 16; //设备不支持

}
