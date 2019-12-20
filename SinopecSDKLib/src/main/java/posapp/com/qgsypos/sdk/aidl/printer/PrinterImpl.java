package posapp.com.qgsypos.sdk.aidl.printer;

import android.os.RemoteException;
import android.util.Log;

/**
 * 打印机实现
 *
 * @author runningDigua
 * @date 2019/12/12
 */
public class PrinterImpl extends IPrinter.Stub {

    private static final String TAG = "PrinterImpl";

    @Override
    public int getPrinterState() throws RemoteException {
        return 10011;
    }

    @Override
    public void setPrinterGray(int grayLevel) throws RemoteException {
        Log.e(TAG, "setPrinterGray:" + grayLevel);
    }

    @Override
    public void spitPaper(int distance) throws RemoteException {

    }

    @Override
    public void printData(String dataJson, OnPrintResultListener listener) throws RemoteException {

    }
}
