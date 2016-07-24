# OpenMusicPlayer
MFree——开源音乐播放器
```
背景：
为了维护一个良性项目，虽然音乐播放器原理简单，但是要制作一个复杂的音乐播放器，却涉及到了各个方面的知识：
1.UI界面：界面是安卓开发最基本的，也是难点之一。
2.数据库
3.网络编程
4.多线程
...
```
更新日志：
v1.0 主要搭建整体框架和实现播放逻辑。
技术点：
0.整体架构采用MVP
1.ViewPager+Fragment+RecyclerView+Loader机制实现本地音乐的获取。（其中Fragment踩坑较多）。
2.Retrofit+OkHttp+Rxjava实现网络歌曲的获取（某度api见源码）。
3.aidl Service实现音乐播放逻辑(接口设计借鉴原生安卓播放器)。
目前效果图：

![本地乐库.jpg](http://upload-images.jianshu.io/upload_images/1757399-926057386ebc9bbc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)
![本地乐库](http://upload-images.jianshu.io/upload_images/1757399-91483526e3b954ab.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)
![在线歌单](http://upload-images.jianshu.io/upload_images/1757399-2f278368e2fc992a.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)
![在线歌单](http://upload-images.jianshu.io/upload_images/1757399-6168f93f6b660075.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)
![在线歌单](http://upload-images.jianshu.io/upload_images/1757399-e78762c5665e38fc.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)
心得：
1.为了保存Fragment状态踩了很多坑，为了避免重复加载也做了很多精心的小设计，但是不免有很多BUG，慢慢去发现。
2.Service的设计，官方音乐播放器使用aidl设计，于是机智的我就拷贝了官方的aidl文件，模仿它进行设计的（感觉还是有很多欠缺，以后慢慢磨），以及简化调用而设计的utils。
计划：
1.完善Service（为了测试现只做了播放功能）、完成Service广播更新状态
2.封装下载器
3.UI（包括自定义View、动画等等）

每周更新（目前实习只有周末做）
