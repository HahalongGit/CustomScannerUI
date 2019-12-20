package posapp.com.qgsypos.sdk.aidl.printer;

/**
 * 打印异常码
 *
 * @author runningDigua
 * @date 2019/12/12
 */
public interface ErrorCode {

    int ERROR_NONE = 0;//成功
    int ERROR_PAPERENDED = 240; //- 缺纸，不能打印
    int ERROR_HARDERR = 242; //- 硬件错误
    int ERROR_OVERHEAT = 243; //- 打印头过热
    int ERROR_BUFOVERFLOW = 245; //- 缓冲模式下所操作的位置超出范围
    int ERROR_LOWVOL = 225;//- 低压保护
    int ERROR_PAPERENDING = 244;//- 纸张将要用尽，还允许打印(单步进针打特有返回值)
    int ERROR_MOTORERR = 251; //- 打印机芯故障(过快或者过慢)
    int ERROR_PENOFOUND = 252; //- 自动定位没有找到对齐位置,纸张回到原来位置
    int ERROR_PAPERJAM = 238; //- 卡纸
    int ERROR_NOBM = 246; //- 没有找到黑标
    int ERROR_BUSY = 247; //- 打印机处于忙状态
    int ERROR_BMBLACK = 248; //- 黑标探测器检测到黑色信号
    int ERROR_WORKON = 230; //- 打印机电源处于打开状态
    int ERROR_LIFTHEAD = 224; //- 打印头抬起(自助热敏打印机特有返回值)
    int ERROR_CUTPOSITIONERR = 226; //- 切纸刀不在原位(自助热敏打印机特有返回值)
    int ERROR_LOWTEMP = 27; //- 低温保护或AD出错(自助热敏打印机特有返回值)
    int SERVICE_CRASH = 99; //- 设备服务异常
    int REQUEST_EXCEPTION = 100;// - 请求异常

}
