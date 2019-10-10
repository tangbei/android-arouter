# android组件化工程

本工程使用现在最流行的组件化方案，多 module 方案，并使用 aRouter 作为支撑。

![image](https://github.com/tangbei/android-arouter/blob/master/build_model.png)


# liveDataBus

项目中添加了liveDataBus，果断抛弃eventBus。有太多优点了，轻量、便于理解。
具体可以 点击 [liveDataBus](https://juejin.im/post/5b5ac0825188251acd0f3777)

在此说明一点，如果app有做混淆，则必须添加以下混淆：

```aidl
-keep class android.arch.lifecycle.** { *; }
-keep class android.arch.core.** { *; }
```
这样 liveDataBus中的**hook**才会生效。



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


#### 工程结构
1. app 工程主入口，只有启动页和跳转的scheme。
2. common 工程的公共代码存放位置。
3. frame 工程框架写入位置，采用mvp+retorfit+okHttp3+Rxjava2+rxlifeStyle开发。
4. resource 工程公共资源写入位置、以及通用依赖写入位置
5. module-... 开发使用的主module，根据项目和需求可以创建若干个、相互之间独立，使用aRouter作为连接。

#### 单一工程和组件化比较

#### 一、单一工程
1. 对工程的任意修改调试都要编译整个工程，效率十分低下。
2. 不利于多人团队协同开发。
3. 无法做到功能复用。
4. 业务模块间耦合严重。

#### 二、组件化工程
1. 极大提高工程编译速度
2. 业务模块解耦，有利于多人团队协作开发
3. 组件化是功能重用的基石
