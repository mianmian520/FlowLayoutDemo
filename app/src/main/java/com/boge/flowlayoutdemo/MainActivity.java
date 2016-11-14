package com.boge.flowlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.boge.library.FlowLayout;
import com.boge.library.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.flowLayout)
    FlowLayout flowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final TagAdapter<String> adapter = new TagAdapter<>(this);
        final List<String> datas = getData();

        adapter.setmDataList(datas);
        flowLayout.setAdapter(adapter);
        flowLayout.setOnTagItemClick(new FlowLayout.OnTagItemClick() {
            @Override
            public void onItemCilck(View view, int position) {
                Toast.makeText(MainActivity.this, datas.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public List<String> getData(){
        List<String> list = new ArrayList<String>();
        list.add("艾欧尼亚");
        list.add("祖安");
        list.add("诺克萨斯");
        list.add("班德尔城");
        list.add("皮尔特沃夫");
        list.add("战争学院");
        list.add("巨神峰");
        list.add("雷瑟守备");
        list.add("裁决之地");
        list.add("黑色玫瑰");
        list.add("暗影岛");
        list.add("钢铁烈阳");
        list.add("均衡教派");
        list.add("水晶之痕");
        list.add("影流");
        list.add("守望之海");
        list.add("征服之海");
        list.add("卡拉曼达");
        list.add("皮城警备");
        list.add("比尔吉沃特");
        list.add("德玛西亚");
        list.add("弗雷尔卓德");
        list.add("无畏先锋");
        list.add("恕瑞玛");
        list.add("扭曲丛林");
        list.add("巨龙之巢");
        return list;
    }
}
