# ADCDN广告接入说明文档
## 1.概述
尊敬的开发者朋友，欢迎您使用ADCDN广告sdk平台。通过本文档，您可以轻松的在几分钟之内完成广告的集成过程。
## 3.SDK接入流程
### 3.1 添加sdk到工程
接入环境：Androidstudio
可以复制Demo中libs文件目录下的依赖包到项目中。
```
android {
 ....
 defaultConfig {
 ...
  multiDexEnabled true//方法数量超量需要设置
 }
 //.添加依赖目录
 repositories {
   flatDir {
     dirs 'libs'
   }
   }
 }
   dependencies {
   ....
   //2.添加依赖包
     //必须的依赖包
 implementation(name:	'adcdnsdk-1.0',	ext:	'aar')
 implementation 'pl.droidsonroids.gif:android-gif-drawable:1.2.6'

 implementation(name:	'gdt-release',	ext:	'aar')
 implementation(name:	'gdt_1.0.5_20191028',	ext:	'aar')
 implementation(name:	'toutiao-release',	ext:	'aar')
 implementation(name:	'open_ad_sdk',	ext:	'aar')



```
**注意事项

*如果项目中集成了重复的其他广告平台sdk尽量移除避免冲突
*确保所使用的 android-support-v4.jar 包中的 android.support.v4.app.NotificationCompat.Builder 类包含 setProgress 方法，如果不包含此方法请升级 android 开发套件

### 3.2权限申请
使用sdk时可能需要以下权限，为了保证使用广告的正确，请在6.0以及以上的手机中使用sdk前及时申请
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> 
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> 
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> 
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> 
<uses-permission android:name="android.permission.GET_TASKS" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> 
<uses-permission android:name="android.permission.ACCESS_COARSE_UPDATES" /> 
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
```
PS:ACCESS_COARSE_LOCATION̵READ_PHONE_STATE̵WRITE_EXTERNAL_STORAGE̵ ACCESS_NETWORK_STATE̵ACCESS_WIFI_STATE这几个权限请确保获取，否则可能无法获取广告 （可参考Demo中的SplashActivity）
## 4. 接入代码
### 4.1 sdk初始化
提示：appid请联系商务获取，并在Application的onCreat()方法中进行SDK初始化

```
// TODO: 2018/10/22 修改为自己的appId
     AdcdnMobSDK.instance().initSdk(getApplicationContext(), APP_ID);
```
### 4.2 开屏广告示例
提示：
1.开屏广告默认为屏幕高度的100%，可自定义高度比例，但不能低于0.75
2.注意加载开屏广告时，请保证开屏view控件处于可见状态，否则会出现获取不到广告的情况
```
        adcdnSplashView = new AdcdnSplashView(this, "请填写对应的plcId", flContainer);
        adcdnSplashView.setListener(new AdcdnSplashAdListener() {
            @Override
            public void onADExposure() {
                Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
            }

            @Override
            public void onADFailed(String error) {
                Log.e(TAG, "广告获取失败了 ::::: " + error);
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
                Log.e(TAG, "广告被关闭了 ::::: ");
            }
        });
        flContainer.addView(adcdnSplashView);
        adcdnSplashView.loadAd();
```
### 4.3 banner广告示例
Banner广告控件容器保证不低于50dp，建议使用自适应
```
 // 初始化Banner广告
        adcdnBannerView = new AdcdnBannerView(this,"请填写对应的plcId");
        // 不设置banner广告尺寸大小则默认比例为: 640*100;
		//adMobGenBannerView.setADSize(640,100);
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
```

### 4.5 原生模板广告示例
```
 adcdnNativeExpressView = new AdcdnNativeExpressView(this, "请填写对应的plcId");
        adcdnNativeExpressView.setAdCount(3);//请求广告的数量（1~3），最多一次请求3个广告
        //adcdnNativeExpressView.setADSize(new MyADSize(MyADSize.FULL_WIDTH, MyADSize.AUTO_HEIGHT));//可选，单位dp
 adcdnNativeExpressView.loadAd(new AdcdnNativeExpressAdListener() {

            @Override
            public void onADLoaded(List<NativeExpressADDatas> adList) {
                Log.e(TAG, "广告下载成功 ::::: ");
                adView.removeAllViews();
                adView2.removeAllViews();
                adView3.removeAllViews();
                if (adList.size() > 0) {
                    adView.addView(adList.get(0).getADView());
                    adList.get(0).render();
                }
                if (adList.size() > 1) {
                    adView2.addView(adList.get(1).getADView());
                    adList.get(1).render();
                }
                if (adList.size() > 2) {
                    adView3.addView(adList.get(2).getADView());
                    adList.get(2).render();
                }
            }

            @Override
            public void onRenderSuccess(View view) {
                Log.e(TAG, "广告渲染成功 ::::: ");
//                Toast.makeText(NativeExpressActivity.this, "广告下载成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onADError(String error) {
                Toast.makeText(NativeExpressActivity.this, "广告下载失败" + error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onExposured(View view) {
                Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
            }

            @Override
            public void onClicked(View view) {
                Log.e(TAG, "广告被点击了 ::::: ");

            }

            @Override
            public void onAdClose(View view) {

            }


        });
```


### 4.6 插屏广告示例
```
 adcdnInsertView = new AdcdnInsertView(InterstitialActivity.this,"请填写对应的plcId");
        adcdnInsertView.setListener(new AdcdnInsertitailAdListener() {
            @Override
            public void onADExposure() {
                Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
            }

            @Override
            public void onADOpen() {
                Log.e(TAG, "广告打开成功了 ::::: ");
            }

            @Override
            public void onADLeftApplication() {
                Log.e(TAG, "广告onADLeftApplication ::::: ");
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
        adcdnInsertView.loadAd();
```
### 4.7 激励视屏广告示例
```
 AdVideoSlot adSlot = new AdVideoSlot.Builder()
                .setCodeId("请填写对应的plcId")
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setRewardName("金币") //奖励的名称
                .setRewardAmount(3)  //奖励的数量
                .setUserID("user123") //必传参数，表来标识应用侧唯一用户；若非服务器回调模式或不需sdk透传//可设置为空字符串
                .setMediaExtra("media_extra") //附加参数，可选
                .setOrientation(AdcdnVideoView.VERTICAL) //必填参数，期望视频的播放方向：AdcdnVideoView.HORIZONTAL 或 AdcdnVideoView.VERTICAL
                .build();
 adcdnVideoView = new AdcdnVideoView(this, adSlot);
    adcdnVideoView.setListener(new AdcdnVideoAdListener() {
  
              @Override
              public void onVideoDownloadSuccess() {
                  Log.e(TAG, "广告下载完成了 ::::: ");
                  adcdnVideoView.showAd();
                  Toast.makeText(VideoActivity.this, "广告下载成功", Toast.LENGTH_SHORT).show();
              }
  
              @Override
              public void onVideoDownloadFailed() {
                  Log.e(TAG, "广告下载失败了 ::::: ");
              }
  
              @Override
              public void playCompletion() {//这个回调后可以给用户奖励
                  Log.e(TAG, "广告播放完成 ::::: ");
              }
  
              @Override
              public void onRewardVerify(boolean b, AdVideoSlot adVideoSlot) {//服务器对服务器的回调时用到
                  Log.e(TAG, " amount:" + adVideoSlot.getRewardAmount() +
                          " name:" + adVideoSlot.getRewardName() + " userId:" + adVideoSlot.getUserID());
              }
  
              @Override
              public void onAdShow() {
                  Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
              }
  
              @Override
              public void onAdClick() {
                  Log.e(TAG, "广告被点击了 ::::: ");
              }
  
              @Override
              public void onAdClose() {
                  Log.e(TAG, "广告被关闭了，该回调不一定会有 ::::: ");
              }
  
              @Override
              public void onAdFailed(String s) {
                  Log.e(TAG, "广告加载失败了 ::::: " + s);
              }
          });

//下载点击按钮
          btnLoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adcdnVideoView.loadAd();
                    }
                });
//展示广告点击按钮
          btnShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adcdnVideoView.showAd();
                    }
                });
```
#### 4.7.1 服务器到服务器回调(可选)
服务器到服务器回调让您判定是否提供奖励给观看广告的用户。当用户成功看完广告时，您可以在ADCDN平台配置从ADCDN服务器到您自己的服务器的回调链接，以通知您用户完成了操作。

**回调方式说明**

ADCDN服务器会以 GET 方式请求第三方服务的回调链接，并拼接以下参数回传：
```
user_id=%s&trans_id=%s&reward_name=%s&reward_amount=%d&extra=%s&sign=%s
```
字段定义 | 字段名称 | 字段类型 | 备注 
:-: | :-: | :-: | :-: | 
sign | 签名 | string | 签名 |
user_id	| 用户id	 | string |	调用SDK透传，应用对用户的唯一标识 |
trans_id |	交易id |	string | 完成观看的唯一交易ID |
reward_amount |	奖励数量 | int | 媒体平台配置或调用SDK传入 |
reward_name | 奖励名称 |	string | 媒体平台配置或调用SDK传入 |
extra |	Extra |	string | 调用SDK传入并透传，如无需要则为空 |

**签名生成方式**

appSecurityKey: 您在ADCDN媒体平台新建奖励视频代码位获取到的密钥 transId：交易id sign = sha256(appSecurityKey:transId)

**返回约定**

返回 json 数据，字段如下：

字段定义 | 字段名称 | 字段类型 | 备注 
:-: | :-: | :-: | :-: | 
isValid | 校验结果 | bool | 判定结果，是否发放奖励|

示例：
```
{
    "isValid": true
}
```


### 4.8 全屏视屏广告示例
```
 //activity,位置id，期望视频方向(横屏AdcdnFullVideoView.HORIZONTAL，竖屏AdcdnFullVideoView.VERTICAL)
   adcdnFullVideoView = new AdcdnFullVideoView(this, "请填写对应的plcId",AdcdnFullVideoView.VERTICAL);
         // 设置广告监听（不设置也行）
         adcdnFullVideoView.setListener(new AdcdnVideoFullAdListener() {
 
             @Override
             public void onAdShow() {
                 Log.e(TAG, "广告展示曝光回调，但不一定是曝光成功了，比如一些网络问题导致上报失败 ::::: ");
             }
 
             @Override
             public void onAdVideoBarClick() {
                 Log.e(TAG, "广告被点击了 ::::: ");
             }
 
             @Override
             public void onAdClose() {
                 Log.e(TAG, "广告被关闭了，该回调不一定会有 ::::: ");
             }
 
             @Override
             public void onVideoComplete() {
                 Log.e(TAG, "广告播放完成 ::::: ");
             }
 
             @Override
             public void onSkippedVideo() {
                 Log.e(TAG, "广告被跳过了 ::::: ");
             }
 
             @Override
             public void onFullScreenVideoCached() {
                 Log.e(TAG, "广告下载完成了 ::::: ");
             }
 
             @Override
             public void onError(String s) {
                 Log.e(TAG, "广告加载失败了 ::::: " + s);
             }
 
 
         });

//下载点击按钮
           btnLoad.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          adcdnFullVideoView.loadAd();
                      }
                  });
//展示广告点击按钮
          btnShow.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         adcdnFullVideoView.showAd(FullVideoActivity.this);
                     }
                 });
```

### 4.9 场景广告示例
```
 AdGameSlot adSlot = new AdGameSlot.Builder()
                 .setAppId("10001")//场景的appId，必传
                 .setGameId("10001")//场景的id，必传
//                .setUserId("112245")//用户id，未登录可以不传，登录后必传，否则影响用户唯一性
//                .setNickname("用户123")//用户昵称，没有就不传
//                .setAvatarUrl("图片地址")//用户头像，没有就不传
                 .setExpressAdId("1010038")//原生模板广告id（上文下浮层）
                 .setExpressAdId2("1010039")//原生模板广告id（文字浮层）
                 .setVideoAdId("1010152")//激励视频id
                 .build();
        adcdnGameAdView = new AdcdnGameAdView(this, adSlot);
        adcdnGameAdView.setGameListener(new GameADDatas() {
            @Override
            public void startShare(AdcdnShareDatas adcdnShareDatas) {
                //客户端自行调用分享
                Log.e(TAG, "分享" + adcdnShareDatas.getUrl() + adcdnShareDatas.getTitle() + adcdnShareDatas.getDesc());
                adcdnShareDatas.beanShare();//分享后需要调用！
            }

            @Override
            public void startLogin() {
                //客户端可以在这里处理登录
                Log.e(TAG, "登录");

            }
        });
        adcdnGameAdView.loadWebView();
        flContainer.addView(adcdnGameAdView);
        
        //销毁时调用
          adcdnGameAdView.destroy();//注意要在 super.onDestroy()之前调用
          
          
        //注意：需要在activity的onBackPressed()调用以下方法来处理内部H5页面的物理返回按钮
         @Override
            public void onBackPressed() {
                if (!adcdnGameAdView.backWebview()) {
                    finish();
                }
            }
```

注意事项：
在不使用的时候（退出activity等）记得及时释放广告资源

## 5.适配Android7.0
如果您的应用需要适配7.0以上，请在AndroidManifest中添加以下代码：
```
       
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/adcdn_file_paths" />
        </provider>
      
        <provider
            android:name="com.bytedance.sdk.openadsdk.TTFileProvider"
            android:authorities="${applicationId}.TTFileProvider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/adcdn_file_paths" />
        </provider>

        <provider
            android:name="com.bytedance.sdk.openadsdk.multipro.TTMultiProvider"
            android:authorities="${applicationId}.TTMultiProvider"
            android:exported="false" />



        <uses-library
            android:name="org.apache.http.legacy"
            android:required="false" />
```
** 注意：各个平台的provider在需要是添加，不需要时移除掉，否则会出现异常
在res/xml目录下，新建一个XML文件ﬁle_paths,在该文件中添加如下代码：
```
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- 头条下载配置-->
    <external-files-path
        name="external_files_path"
        path="Download" />
    <!-- 腾讯下载配置-->
        <external-cache-path
            name="gdt_sdk_download_path1"
            path="com_qq_e_download" />
        <cache-path
            name="gdt_sdk_download_path2"
            path="com_qq_e_download" />
</paths>
```
为了适配下载和安装相关功能，在工程中引用包 com.android.support:support-v4:24.2.0 使用24.2.0以及以上版本

## 6. 混淆配置
广告sdk内部混淆，若您项目需要进行混淆则需要在混淆文件中添加以下配置


-dontwarn com.yunxia.adsdk.**

-keep class com.yunxia.adsdk.**{*;}

-keep interface com.yunxia.adsdk.**{*;}

-keep class com.android.**{*;}

-keep class com.yunxia.adsdk.admobhttp.**{	*;	}

-keep class com.jaredrummler.android.processes.**{*;}

-keep class com.jaredrummler.android.processes.models.**{*;}

-keep class it.sauronsoftware.base64.**{*;}

-dontwarn org.apache.commons.**

-keep class org.apache.**{	*;	}

-ignorewarnings

-dontnote android.net.http.*

-dontnote org.apache.commons.codec.**

-dontnote org.apache.http.**

#webview + js

-keepattributes *JavascriptInterface*

-keepattributes *Annotation*

#广点通sdk

-keep class com.qq.e.**	{public protected *;}

-keep class android.support.v4.**{public *;}

-keep class android.support.v7.**{public *;}

-keep class MTT.ThirdAppInfoNew	{*;}

-keep class com.tencent.**	{*;}

-dontwarn com.androidquery.**

-keep class com.androidquery.** { *;}

-dontwarn tv.danmaku.**

-keep class tv.danmaku.** { *;}

-dontwarn androidx.**

#头条 穿山甲 sdk

-keep class com.bytedance.sdk.openadsdk.**	{	*;	}

-keep class com.androidquery.callback.**	{*;}

-keep class com.bytedance.sdk.openadsdk.service.TTDownloadProvider

-keep class com.androidquery.auth.TwitterHandle.**	{	*;	}

-keep class com.androidquery.**	{*;}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-keep class com.bytedance.sdk.openadsdk.** {*;}

-keep class com.androidquery.callback.** {*;}

-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}

-keep class com.ss.sys.ces.* {*;}



#adView

-keepclassmembers class * {public *;}

-keep public class com.kyview.** {*;}

-keep public class com.kuaiyou.** {*;}

#百度sdk

-keepclassmembers class * extends android.app.Activity	{public void *(android.view.View);}

-keepclassmembers enum *	{public static **[]	values();
public static ** valueOf(java.lang.String);}

-keep class com.baidu.mobads.*.**{*;}

#360

-keep class com.ak.** {*;}

-keep class android.support.v4.** {
    public *;
}

#小米

-keep class com.xiaomi.ad.** {*;}

-keep class com.miui.zeus.**{*;}

#谷歌

-keep class com.google.android.gms.** {*;}

## 7. 常见问题
get ad ﬁled 广告未放量，请联系商务

platams is empty 广告渠道未接入，请联系商务



