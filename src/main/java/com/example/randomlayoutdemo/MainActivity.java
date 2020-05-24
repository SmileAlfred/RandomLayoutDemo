package com.example.randomlayoutdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.randomlayoutdemo.randomLayout.StellarMap;

import java.util.Random;

/**
 * 只是来源于网络，回归于网络；如有侵权，请联系我；CSDN：https://blog.csdn.net/liusaisaiV1
 */
public class MainActivity extends Activity {
    private StellarMap stellarMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        stellarMap = findViewById(R.id.random_stellar_map);
        initData();
    }

    //提供装配的数据
    private String[] datas = new String[]{
            "认罪认罚从宽",
            "法不能向不法让步",
            "AG赢了",
            "公益诉讼",
            "是非的Android学习之路",
            "群众信访件件有回复",
            "乃万甩话筒",
            "CSDN粉丝12",
            "大学老师购买车辆",
            "天气热",
            "美人鱼影视拍摄投资",
            "Android培训老师自己周转",
            "开斋节",
            "北京代表团",
            "喻言变温柔了",
            "待你不薄"
    };

    /**
     * 声明两个子数组
     */
    private String[] oneDatas = new String[datas.length / 2];
    private String[] twoDatas = new String[datas.length - datas.length / 2];

    private Random random = new Random();

    protected void initData() {

        //初始化子数组的数据
        for (int i = 0; i < datas.length; i++) {
            if (i < datas.length / 2) {
                oneDatas[i] = datas[i];
            } else {
                twoDatas[i - datas.length / 2] = datas[i];
            }
        }

        StellarAdapter adapter = new StellarAdapter();
        stellarMap.setAdapter(adapter);

        //设置stellarMap的内边距
        int leftPadding = dp2px(20);
        int topPadding = dp2px(10);
        int rightPadding = dp2px(10);
        int bottomPadding = dp2px(10);
        stellarMap.setInnerPadding(leftPadding, topPadding, rightPadding, bottomPadding);

        //必须调用如下的两个方法，否则stellarMap不能显示数据
        //设置显示的数据在x轴、y轴方向上的稀疏度
        stellarMap.setRegularity(5, 3);
        //设置初始化显示的组别，以及是否需要使用动画
        stellarMap.setGroup(0, true);

    }

    //提供Adapter的实现类
    class StellarAdapter implements StellarMap.Adapter {

        //获取组的个数
        @Override
        public int getGroupCount() {
            return 2;
        }

        //返回每组中显示的数据的个数
        @Override
        public int getCount(int group) {
            if (group == 0) {
                return datas.length / 2;
            } else {
                return datas.length - datas.length / 2;
            }
        }

        //返回具体的view.
        //position:不同的组别，position都是从0开始。
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView tv = new TextView(MainActivity.this);

            //设置属性;设置文本的内容
            if (group == 0) {
                tv.setText(oneDatas[position]);
            } else {
                tv.setText(twoDatas[position]);
            }
            //设置字体的大小
            tv.setTextSize(dp2px(3) +dp2px(random.nextInt(6)));
            //设置字体的颜色
            int red = random.nextInt(211);//00 ~ ff ; 0 ~ 255
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            tv.setTextColor(Color.rgb(red, green, blue));

            //设置TextView的点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            return tv;
        }

        //返回下一组显示平移动画的组别。查看源码发现，此方法从未被调用。所以可以不重写
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }

        //返回下一组显示缩放动画的组别。
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (group == 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }


        public int dp2px(int dp) {
            //获取手机密度
            float density = getApplicationContext().getResources().getDisplayMetrics().density;
            return (int) (dp * density);

        }
}
