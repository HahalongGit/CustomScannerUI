package com.centerm.sinopecsdktest;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecyerViewAdpater.onItemListener {

    public static final String TAG = "SinopecSDKTest";
    private RecyclerView mRecyclerView;
    private static List<ModuleBean> mListItems;
    LinkedBlockingDeque<String> list = new LinkedBlockingDeque<>();

    static {
        mListItems = new ArrayList<>();
        mListItems.add(new ModuleBean("扫描模块", ScannerActivity.class));
//        mListItems.add(new ModuleBean("非接触式RF模块", RFCardActivity.class));
        mListItems.add(new ModuleBean("打印机模块", PrinterActivity.class));
        mListItems.add(new ModuleBean("系统接口", SystemActivity.class));
        mListItems.add(new ModuleBean("系统管理", ManagerActivity.class));
        mListItems.add(new ModuleBean("自定义扫码预览", PreviewScannActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        init();

    }

    private void init() {
        initWidget();
        initData();
    }

    private void initWidget() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_rv);
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new RecyerViewAdpater(mListItems, this, this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onClick(int pos) {
        startActivity(new Intent(MainActivity.this, mListItems.get(pos).getTargetClass()));
    }
}
