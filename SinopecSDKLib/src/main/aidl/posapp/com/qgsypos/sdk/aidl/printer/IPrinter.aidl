// PrinterImpl.aidl
package posapp.com.qgsypos.sdk.aidl.printer;
import posapp.com.qgsypos.sdk.aidl.printer.OnPrintResultListener;
// Declare any non-default types here with import statements

interface IPrinter {

   /**
    *获取打印机状态
    **/
   int getPrinterState();
   /**
    *设置打印机灰度
    **/
   void setPrinterGray(int grayLevel);
   /**
    *打印机走纸
    **/
   void spitPaper(int distance);
   /**
    *开始打印数据
    **/
   void printData(String dataJson,OnPrintResultListener listener);


}
