package com.xiefei.mvpstructure.view;

/**
 * Created by xiefei on 2016/3/16.
 */
public interface MvpLceView<M> extends MvpView{
    /*该方法显示加载数据时的loading视图
     */
    void showLoading(boolean isPullToRefresh);
    /*显示contentView
     */
    void showContent();
    /*显示错误信息
     */
    void showError(Throwable throwable, boolean isPullToRefresh);
    /*设置数据
     */
    void setData(M data);
}
