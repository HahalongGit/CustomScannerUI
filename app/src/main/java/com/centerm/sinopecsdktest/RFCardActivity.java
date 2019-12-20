package com.centerm.sinopecsdktest;//package com.bankcomm.bcsdktest;
//
//import android.os.Bundle;
//import android.os.RemoteException;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//
//import com.centerm.icbcsdktest.R;
//import com.icbc.smartpos.deviceservice.aidl.IDeviceService;
//import com.icbc.smartpos.deviceservice.aidl.IRFCardReader;
//import com.icbc.smartpos.deviceservice.aidl.RFSearchListener;
//
//import static com.bankcomm.bcsdktest.Constants.constants.RfCardKeyType.KEY_A;
//
//
///**
// * Created by Qzhhh on 2016/11/8.
// */
//
//public class RFCardActivity extends BaseTestActivity {
//    private IRFCardReader mCardReader;
//
//    private EditText edtSectorNo;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_rfcard);
////        edtSectorNo = findViewById(R.id.edt_sector_no);
//        linearLayout = (LinearLayout) this.findViewById(R.id.tip_ll);
//        scrollView = (ScrollView) this.findViewById(R.id.tip_sv);
//
//    }
//
//    @Override
//    public void onDeviceConnected(IDeviceService serviceManager) {
//        try {
//            mCardReader = IRFCardReader.Stub.asInterface(serviceManager.getRFCardReader());
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void powerOn(View view) {
//        showMessage("开始检卡");
//        try {
//            mCardReader.searchCard(new RFSearchListener.Stub() {
//                @Override
//                public void onCardPass(int i) throws RemoteException {
//                    showMessage("卡片类型" + i);
//                }
//
//                @Override
//                public void onFail(int i, String s) throws RemoteException {
//                    showMessage("检卡错误：" + i + " s:" + s);
//                }
//            });
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void activateCard(View view) {
//        byte[] result = new byte[64];
//        try {
//            mCardReader.activate("1", result);
//            showMessage("激活卡片结果:" + HexUtil.bcd2str(result));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void call(View view) {
//        try {
//            String send = "00A404000E315041592E5359532E4444463031";
//            byte[] result = mCardReader.exchangeApdu(HexUtil.hexStringToByte(send));
//            showMessage("向RF发送的APDU指令:" + send);
//            showMessage("收到的应答:" + HexUtil.bcd2str(result));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void isExist(View view) {
//        try {
//            boolean r = mCardReader.isExist();
//            if (r)
//                showMessage("卡片在位");
//            else
//                showMessage("卡片不在位");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void powerOff(View view) {
//        try {
//            mCardReader.halt();
//            showMessage("关闭非接设备成功");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void authenticateBlock(View view) {
//        try {
//            byte[] keyA = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//                    (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
//            int i = mCardReader.authBlock(getBlock(), KEY_A, keyA);
//            showMessage("认证结束:" + i);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void authenticateBlock1(View view) {
//        try {
//            byte[] keyA = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
//                    (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
//            int i = mCardReader.authSector(getBlock(), KEY_A, keyA);
//            showMessage("认证结束:" + i);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void writeDataBlock(View view) {
//        try {
//            int i = mCardReader.writeBlock(getBlock(), new byte[]{
//                    (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
//                    (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08,
//                    (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04,
//                    (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08});
//            showMessage("写块数据结束:" + i);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void readDataBlock(View view) {
//        try {
//            byte[] result = new byte[16];
//            int r = mCardReader.readBlock(getBlock(), result);
//            if (r == 0) {
//                showMessage("读取块号6数据为：" + HexUtil.bcd2str(result));
//            } else {
//                showMessage("读取块号失败");
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void incrementOperation(View view) {
//        try {
//            byte[] redData = new byte[]{0x00, 0x00, 0x00, 0x01};
//            int i = mCardReader.increaseValue(getBlock(), HexUtil.bytes2int(redData));
//            showMessage("加值结束:" + i);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void decrementOperation(View view) {
//        try {
//            byte[] redData = new byte[]{0x00, 0x00, 0x00, 0x01};
//            int i = mCardReader.decreaseValue(getBlock(), HexUtil.bytes2int(redData));
//            showMessage("减值结束:" + i);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private int getBlock() {
//        EditText editText = findViewById(R.id.block_et);
//        String block = editText.getText().toString();
//        if (TextUtils.isEmpty(block)) {
//            return 6;
//        }
//        try {
//            return Integer.valueOf(block);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 6;
//    }
//    public void stopSearch(View view) {
//        try {
//            mCardReader.stopSearch();
//            showMessage("非接停止检卡成功");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void cardReset(View view) {
//        try {
//            mCardReader.cardReset(0);
//            showMessage("卡片复位成功");
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void authSector(View view) {
////        mCardReader.authSector();
//    }
//}
