package posapp.com.qgsypos.sdk.aidl.scanner;

import android.os.RemoteException;
import android.util.Log;

/**
 * 扫码器实现
 *
 * @author runningDigua
 * @date 2019/12/12
 */
public class ScannerImpl extends IScanner.Stub {

    private static final String TAG = "ScannerImpl";

    @Override
    public int checkScan() throws RemoteException {
        Log.e(TAG, "checkScan");
        return 1000;
    }

    @Override
    public void stopScan() throws RemoteException {
        Log.e(TAG, "stopScan");
    }

    @Override
    public void startScan(CameraBeanZbar param, OnScanResultListener listener) throws RemoteException {
        Log.e(TAG, "startScan");
    }

    @Override
    public String decode(byte[] data, int width, int height) throws RemoteException {
        return null;
    }

}
