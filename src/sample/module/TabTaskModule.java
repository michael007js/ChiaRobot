package sample.module;

import com.sun.javafx.application.PlatformImpl;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Controller;
import sample.bean.TaskBean;
import sample.constant.AppConstant;
import sample.dao.OnTaskModuleCallBack;
import sample.module.base.BaseTabModule;
import sample.utils.MichaelUtils;
import sample.utils.UIUtils;

/**
 * 任务模块
 */
public class TabTaskModule extends BaseTabModule implements EventHandler<ActionEvent>, OnTaskModuleCallBack, Runnable {
    /**
     * 主控
     */
    private Controller controller;
    private ObservableList<TaskBean> data;
    /**
     * 内存统计计时器
     */
    private DisposableObserver<Long> disposableObserver;

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        startUpdateTaskInfo();

    }

    @Override
    public void baseDirectorySettingChanged() {

    }


    @SuppressWarnings("DuplicatedCode")
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == controller.buttonDiskCacheAdd) {

        }
    }

    @Override
    public void onCreateTaskDirectory(String cacheDirectory, String targetDirectory) {
        if (data == null) {
            data = FXCollections.observableArrayList();
        }
        TaskBean taskBean = new TaskBean();
        taskBean.setNumber(data.size() + 1);
        taskBean.setCache(cacheDirectory);
        taskBean.setTarget(targetDirectory);
        taskBean.setType("");
        taskBean.setRunning(true);
        taskBean.setThread(AppConstant.P_TASK_THREAD);
        taskBean.setMemory(AppConstant.P_TASK_MEMORY);
        data.add(taskBean);
    }


    @Override
    public void run() {
        if (data == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isRunning()) {
                data.get(i).setTime(data.get(i).getTime() + 1000);
            }
        }
        ObservableList<TableColumn> observableList = controller.tableViewTaskQueue.getColumns();
        for (int i = 0; i < observableList.size(); i++) {
            if ("缓存路径".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("cache"));
            } else if ("目标路径".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("target"));
            } else if ("线程".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("thread"));
            } else if ("内存".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("memory"));
            } else if ("序号".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("number"));
            } else if ("用时".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("timeFormat"));
            } else if ("类型".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("type"));
            } else if ("进度".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("progress"));
            }
        }
        controller.tableViewTaskQueue.setItems(data);
    }

    /**
     * 停止任务统计
     */
    private void stopUpdateTaskInfo() {
        if (disposableObserver != null && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }

    /**
     * 开始任务刷新
     */
    private void startUpdateTaskInfo() {
        disposableObserver = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        PlatformImpl.runLater(TabTaskModule.this);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
