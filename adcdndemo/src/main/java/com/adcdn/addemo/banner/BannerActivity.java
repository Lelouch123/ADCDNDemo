package com.adcdn.addemo.banner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.TTAdNative;
import com.yunxia.addemo.R;
import com.yunxia.adsdk.tpadmobsdk.ad.banner.AdcdnBannerView;
import com.yunxia.adsdk.tpadmobsdk.ad.listener.AdcdnBannerAdListener;

/**
 * @author : xnn
 * @date : 2018/6/28
 * @description : Banner广告获取demo
 */

public class BannerActivity extends Activity {
    private static final String TAG = "ADCDN_Log";
    private FrameLayout flContainer;
    private AdcdnBannerView adcdnBannerView;
    private TTAdNative ttAdNative;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        flContainer = findViewById(R.id.flContainer);

        // 初始化Banner广告
        adcdnBannerView = new AdcdnBannerView(this,"1010032");
        // 不设置banner广告尺寸大小则默认比例为: 640*100;
//        adcdnBannerView.setADSize(w,h);//期望模板广告view的size,单位dp
        // 设置广告监听（不设置也行）
        adcdnBannerView.setListener(new AdcdnBannerAdListener() {
            @Override
            public void onADExposure() {
                Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
            }

            @Override
            public void onADFailed(String s) {
                Log.e(TAG, "广告获取失败了 ::::: " + s);
            }

            @Override
            public void onADReceiv() {
                Log.e(TAG, "广告获取成功了 ::::: ");
            }

            @Override
            public void onADClick() {
                Log.e(TAG, "广告被点击了 ::::: ");
            }

            @Override
            public void onAdClose() {
                Log.e(TAG, "广告被关闭了，改回调不一定会有 ::::: ");
            }
        });
        // 把广告控件添加到容器
        flContainer.addView(adcdnBannerView);
        // 开始获取广告
        adcdnBannerView.loadAd();

    }

    @Override
    protected void onDestroy() {
        // 释放广告资源
        if (adcdnBannerView != null) {
            adcdnBannerView.destroy();
        }
        super.onDestroy();
    }

    public static void jumpHere(Context context) {
        context.startActivity(new Intent(context, BannerActivity.class));
    }
}
