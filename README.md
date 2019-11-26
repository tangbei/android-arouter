# android组件化工程

本工程使用的是多model合一的组件化方案，并使用 aRouter 作为支撑，框架灵活、业务和功能分离。

![image](https://github.com/tangbei/android-component/blob/master/readme_image/build_model.png)




由于本工程中使用的是：
```aidl
implementation project(path: ':common')
```
方式引入module,如果之后的业务需求增多，可能会导致**module**数量增多，app可能会引用多个**module**，导致编译速度变慢。可以把组件module生成aar形式上传
到maven库，或公司的私有maven库上。使用的时候只需要 依赖 一下就可以了。
* [android studio生产aar](https://blog.csdn.net/u011511601/article/details/80579543)
* [aar上传maven](https://blog.csdn.net/a568478312/article/details/80166281)


## butterknife

在android组件化工程中使用[黄油刀butterknife](https://github.com/JakeWharton/butterknife)，会存在在library工程中使用的问题：

正常情况下 在工程的app工程中配置：
```aidl
api "com.jakewharton:butterknife:10.2.0"
annotationProcessor(rootProject.ext.dependencies['butterknife-compiler'])
```
即可。本工程中是统一放在 resoure 下的gradle中。


而组件化项目需要在根**build.gradle**的buildscript中添加
```aidl
dependencies {
        classpath 'com.jakewharton:butterknife-gradle-plugin:10.2.0'
    }
```
同时需要在相应 library 的**build.gradle**中添加
```aidl
apply plugin: 'com.jakewharton.butterknife'


dependencies {
    annotationProcessor(rootProject.ext.dependencies['butterknife-compiler'])
}

```

在library工程使用时需要把 R2 替换 R：
![image](https://github.com/tangbei/android-component/blob/androidx/readme_image/20191012143829.png)

![image](https://github.com/tangbei/android-component/blob/androidx/readme_image/20191012144543.png)


#### 注意

如果不是使用的androidx,则butterknife使用9.0.0版本即可，使用方式和上面一样，但是会报下面警告，但是不影响编译和使用：

```aidl
Configure project :app
WARNING: API ‘variant.getJavaCompiler()’ is obsolete and has been replaced with ‘variant.getJavaCompileProvider()’.
It will be removed at the end of 2019.
For more information, see https://d.android.com/r/tools/task-configuration-avoidance.
To determine what is calling variant.getJavaCompiler(), use -Pandroid.debug.obsoleteApi=true on the command line to display a stack trace.


```


## ARouter

自从阿里[ARouter](https://github.com/alibaba/ARouter)框架开源以来，model间不在有更多的耦合，android的mvp、mvvm框架也变成了其中的一部分。组件化后，
mvp和mvvm都变得可以随意替换或共存。团队协作也不再变得嘈杂。具体原理可以 [点击](https://github.com/alibaba/ARouter)

在工程common中的**ARouterManager.class**中，有统一写跳转方式和跳转动画。


```aidl
 /**
     * 不带参数的跳转
     * @param url
     */
    public static void startActivity(Activity activity,String url){
        ARouter.getInstance()
                .build(url)
                .withTransition(startTransition(0,Constant.AnimationType.LEFT),startTransition(1,Constant.AnimationType.LEFT))
                .navigation(activity);
    }
    
     /**
         * 带参数跳转
         * @param url 跳转的路由地址
         * @param bundle 携带的参数
         */
        public static void startActivity(Activity activity,String url, Bundle bundle){
            // 对象传递
            ARouter.getInstance()
                    .build(url)
                    .with(bundle)
                    .withTransition(startTransition(0,Constant.AnimationType.LEFT),startTransition(1,Constant.AnimationType.LEFT))
                    .navigation(activity);
        }
```

同时路由参数可以在module中统一配置：

```aidl
/**
 * 描述: 本工程的所有路由跳转,统一维护
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/11.
 */
public class CommonRouter {

    /**
     * app
     */
    public static final String PATH_APP_SPLASH_ACTIVITY = "/app/SplashActivity";

    public static final String PATH_APP_TEST_ACTIVITY = "/app/TestActivity";

    /**
     * main
     */
    public static final String PATH_APP_MAIN_ACTIVITY = "/main/MainActivity";

    public static final String PATH_APP_TESTS_ACTIVITY = "/main/TestActivity";

}
```

也可以统一处理跳转拦截等：

```aidl
/**
 * 描述: 登录拦截
 * 作者 : Tong
 * e-mail : itangbei@sina.com
 * 创建时间: 2019/9/17.
 */
@Interceptor(priority = 8, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        LogUtil.d("tang--->","我是登录拦截器哦");
        callback.onContinue(postcard);  // 处理完成，交还控制权
    }

    @Override
    public void init(Context context) {
        LogUtil.d("tang--->","我是登录拦截器哦init");
    }
}

```



## liveDataBus

项目中添加了liveDataBus，果断抛弃eventBus。有太多优点了，轻量、便于理解。
具体可以 点击 [liveDataBus](https://juejin.im/post/5b5ac0825188251acd0f3777)

在此说明一点，如果app有做混淆，则必须添加以下混淆：

```aidl
-keep class android.arch.lifecycle.** { *; }
-keep class android.arch.core.** { *; }
```
这样 liveDataBus中的**hook**才会生效。

**如果是在androidx上使用liveDataBus,则把混淆改成如下。同时还得 记得在下面的混淆方案中，把androidx的混淆加上**

```aidl
-keep class androidx.arch.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }
```



## 混淆方案

在组件化方案中，只要app模块开了混淆，子模块无论是否打开混淆都是默认开启的。子模块的混淆规则是无法影响app模块的。

1. 在各个子模块中添加相应的混淆方案。
```子模块中添加
release {
        consumerProguardFiles   'proguard-rules.pro'
    }
```
2. 在主模块app 统一添加混淆操作。

这时可能就会想到说，在各个子模块中添加相对应的混淆方式。但是不推荐使用，两者比较：
1. 在子 model 中添加混淆，耦合度低、组件化程度高。
2. 主app中统一添加混淆，会导致所有model混淆都在app下，会导致混淆规则的冗余。
3. 在子model中混淆，一旦业务组件的代码被混淆，而这时候代码中又出现了bug，将很难根据日志找出导致bug的原因。
4. 各子模块中都有混淆、不便于维护和修改。

3. androidx混淆中需添加：
```aidl
# ----------------------------- androidx -----------------------------
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**
```


## 工程结构
1. app 工程主入口，只有启动页和跳转的scheme。
2. common 工程的公共代码存放位置。
3. frame 工程框架写入位置，采用mvp+retorfit+okHttp3+Rxjava2+rxlifeStyle开发。
4. resource 工程公共资源写入位置、以及通用依赖写入位置
5. module-... 开发使用的主module，根据项目和需求可以创建若干个、相互之间独立，使用aRouter作为连接。

## 单一工程和组件化比较

#### 一、单一工程
1. 对工程的任意修改调试都要编译整个工程，效率十分低下。
2. 不利于多人团队协同开发。
3. 无法做到功能复用。
4. 业务模块间耦合严重。

#### 二、组件化工程
1. 极大提高工程编译速度
2. 业务模块解耦，有利于多人团队协作开发
3. 组件化是功能重用的基石
