# android组件化工程

本工程使用现在最流行的组件化方案，多 module 方案，并使用 aRouter 作为支撑。

![image](https://github.com/tangbei/android-arouter/blob/master/build_model.png)


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
