package sample.dao;

import javafx.collections.ObservableList;
import sample.bean.TaskBean;

/**
 * 任务模块回调
 **/
public interface OnTaskModuleCallBack {

    /**
     * 创建任务目录
     * @param cacheDirectory  缓存目录
     * @param targetDirectory 目标目录
     */
    void onCreateTaskDirectory(String cacheDirectory, String targetDirectory);

    /**
     * 获取任务
     */
    ObservableList<TaskBean> getTask();
}
