package sample.module.base;

import com.sun.istack.internal.NotNull;

import sample.Controller;

public abstract class BaseTabModule {

    /**
     * 模块初始化
     * @param mainController 主控
     */
    public abstract void initialize(@NotNull Controller mainController);

    /**
     * 基础设置被改变
     */
    public abstract void baseDirectorySettingChanged();
}
