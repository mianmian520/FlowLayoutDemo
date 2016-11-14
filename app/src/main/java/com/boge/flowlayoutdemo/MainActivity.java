package com.boge.flowlayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.boge.library.FlowLayout;
import com.boge.library.TagAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.flowLayout)
    FlowLayout flowLayout;

    private String[] mDatas = new String[]{"QQ",
            "视频",
            "放开那三国",
            "电子书",
            "酒店",
            "单机",
            "小说",
            "斗地主",
            "优酷",
            "网游",
            "WIFI万能钥匙",
            "播放器",
            "捕鱼达人2",
            "机票",
            "游戏",
            "熊出没之熊大快跑",
            "美图秀秀",
            "浏览器",
            "单机游戏",
            "我的世界",
            "电影电视",
            "QQ空间",
            "旅游",
            "免费游戏",
            "2048",
            "刀塔传奇",
            "壁纸",
            "节奏大师",
            "锁屏",
            "装机必备",
            "天天动听",
            "备份",
            "网盘",
            "海淘网",
            "大众点评",
            "爱奇艺视频",
            "腾讯手机管家",
            "百度地图",
            "猎豹清理大师",
            "谷歌地图",
            "hao123上网导航",
            "京东",
            "有你",
            "万年历-农历黄历",
            "支付宝钱包"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final TagAdapter<String> adapter = new TagAdapter<>(this);
        final List<String> datas = Arrays.asList(mDatas);

        adapter.setmDataList(datas);
        flowLayout.setAdapter(adapter);
        flowLayout.setOnTagItemClick(new FlowLayout.OnTagItemClick() {
            @Override
            public void onItemCilck(View view, int position) {
                Toast.makeText(MainActivity.this, datas.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
