// IReader.aidl
package posapp.com.qgsypos.sdk.aidl.reader;
import posapp.com.qgsypos.sdk.aidl.reader.SinopecICRec;
// Declare any non-default types here with import statements

/**
 * 读卡器
 * */
interface IReader{

   /**
    *初始化
    **/
    void init();

   /**
    *判断pin
    **/
    String isDefaultPin();

   /**
    *读取一条记录结果
    *pFg为0时:采用默认pin, PWDstr为空
    *pFg为1时:采用输入pin, PWDstr传入pin
    *返回009000表示默认pin
    *返回019000表示需输入pin
    **/
    SinopecICRec getSingelRec(String PWDstr, int pFg);

   /**
    *读取十条记录结果
    **/
    List<SinopecICRec> getAllRec(String PWDstr, int pFg);

   /**
    *资源回收
    **/
    void finish();
}
