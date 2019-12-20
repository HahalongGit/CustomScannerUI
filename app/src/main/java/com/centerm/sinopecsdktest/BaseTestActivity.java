package com.centerm.sinopecsdktest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import posapp.com.qgsypos.sdk.aidl.IDeviceManager;

/**
 *
 */
public abstract class BaseTestActivity extends Activity {

    public static final int SHOW_MSG = 0;
    public static final String SERVICE_ACTION = "posapp.com.qgsypos.sdk.action";
    public static final String PACKAGE_NAME = "posapp.com.qgsypos.sdk";
    private int showLineNum = 0;
    protected LinearLayout linearLayout, left_scroll;
    protected ScrollView scrollView;
    private TextView textView1;
    private TextView textView2;


    // 设别服务连接桥
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder serviceBinder) {
            Log.d(MainActivity.TAG, "IDeviceService服务连接成功");
            if (serviceBinder != null) { // 绑定成功
                IDeviceManager serviceManager = IDeviceManager.Stub
                        .asInterface(serviceBinder);
                onDeviceConnected(serviceManager);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(MainActivity.TAG, "IDeviceService服务断开了");
        }
    };

    // 绑定服务
    public void bindService() {
        Intent intent = new Intent();
        intent.setPackage(PACKAGE_NAME);
        intent.setAction(SERVICE_ACTION);
//        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this, intent));
        boolean flag = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        if (flag) {
            Log.d(MainActivity.TAG, "服务绑定成功");
        } else {
            Log.d(MainActivity.TAG, "服务绑定失败");
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String msg1 = bundle.getString("msg1");
            String msg2 = bundle.getString("msg2");
            int color = bundle.getInt("color");
            updateView(msg1, msg2, color);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService();

    }

    /**
     * 显示信息
     *
     * @param color
     * @createtor：Administrator
     * @date:2014-9-15 下午9:45:18
     */
    public void updateView(final String msg1, final String msg2, final int color) {
//        if (showLineNum % 300 == 0) { // 显示够20行的时候重新开始
//            linearLayout.removeAllViews();
//            showLineNum = 0;
//        }
        showLineNum++;
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.show_item, null);
        textView1 = (TextView) v.findViewById(R.id.tip1);
        textView2 = (TextView) v.findViewById(R.id.tip2);
        textView1.setText(msg1);
        textView2.setText(msg2);
        textView1.setTextColor(color);
        textView2.setTextColor(color);
        textView1.setTextSize(20);
        textView2.setTextSize(20);
        linearLayout.addView(v);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    /**
     * 更新UI
     *
     * @param msg1
     * @param msg2
     * @param color
     * @createtor：Administrator
     * @date:2014-11-29 下午7:01:16
     */
    public void showMessage(final String msg1, final String msg2,
                            final int color) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("msg1", msg1);
        bundle.putString("msg2", msg2);
        bundle.putInt("color", color);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    // 显示单条信息
    public void showMessage(final String msg1, final int color) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("msg1", msg1);
        bundle.putString("msg2", "");
        bundle.putInt("color", color);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    public void showMessage(String str) {
        this.showMessage(str, Color.BLACK);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(conn);
    }

    /**
     * 服务连接成功时回调
     *
     * @param serviceManager
     * @createtor：Administrator
     * @date:2015-8-4 上午7:38:08
     */
    public  void onDeviceConnected(IDeviceManager serviceManager){

    }

    public static Intent createExplicitFromImplicitIntent(Context context,
                                                          Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent,
                0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    public void clear(View v) {
        linearLayout.removeAllViews();
        showLineNum = 0;
    }
}
