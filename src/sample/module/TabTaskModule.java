package sample.module;

import com.sun.javafx.application.PlatformImpl;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.bean.TaskBean;
import sample.constant.AppConstant;
import sample.dao.OnTaskModuleCallBack;
import sample.module.base.BaseTabModule;
import sample.utils.AlertUtils;

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
    /**
     * 菜单
     */
    private ArrayList<MenuItem> menuItems = new ArrayList<>();

    {
        menuItems.add(new MenuItem("添加"));
        menuItems.add(new MenuItem("删除"));
        menuItems.add(new MenuItem("运行日志"));

    }

    /**
     * 鼠标当前位置
     */
    public Double screenX = 0.0, screenY = 0.0;

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        controller.tableViewTaskQueue.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                screenX = event.getScreenX();
                screenY = event.getScreenY();
            }
        });
        controller.tableViewTaskQueue.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (menuItems != null && menuItems.size() > 0 && event.getButton() == MouseButton.SECONDARY) {
                    for (MenuItem menuItem : menuItems) {
                        int index = controller.tableViewTaskQueue.getSelectionModel().getSelectedIndex();
                        if ("删除".equals(menuItem.getText())) {
                            //当前选中下标是否有效
                            boolean effectiveIndex = index >= 0 && index < getTask().size();
                            boolean effectiveCurrentIndexRun = false;
                            if (effectiveIndex) {
                                //当前选中下标item是否处于未运行状态
                                effectiveCurrentIndexRun = !getTask().get(index).isRunning();
                            }
                            if (effectiveIndex && effectiveCurrentIndexRun) {
                                menuItem.setDisable(false);
                            } else {
                                menuItem.setDisable(true);
                            }

                        }
                        if ( "运行日志".equals(menuItem.getText())) {
                            //当前选中下标是否有效
                            boolean effectiveIndex = index >= 0 && index < getTask().size();
                            if (effectiveIndex) {
                                menuItem.setDisable(false);
                            } else {
                                menuItem.setDisable(true);
                            }

                        }
                        menuItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if ("删除".equals(menuItem.getText())) {
                                    for (int i = 0; i < getTask().size(); i++) {
                                        if (i == controller.tableViewTaskQueue.getSelectionModel().getSelectedIndex()) {
                                            getTask().remove(i);
                                            controller.tableViewTaskQueue.setItems(getTask());
                                            break;
                                        }
                                    }

                                } else if ("添加".equals(menuItem.getText())) {
                                    AlertUtils.showInfo("提示", "请从磁盘配置中添加任务");
                                } else if ("运行日志".equals(menuItem.getText())) {
                                    AlertUtils.showInfo("提示", getTask().get(controller.tableViewTaskQueue.getSelectionModel().getSelectedIndex()).getLog());
                                }
                            }
                        });
                    }
                    ContextMenu contextMenu = new ContextMenu();
                    contextMenu.getItems().addAll(menuItems);
                    contextMenu.show(controller.tableViewTaskQueue, screenX, screenY);
                }

            }
        });
        startUpdateTaskInfo();

    }

    @Override
    public void baseDirectorySettingChanged() {

    }


    @SuppressWarnings("DuplicatedCode")
    @Override
    public void handle(ActionEvent event) {

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
        taskBean.setRunning(false);
        taskBean.setThread(AppConstant.P_TASK_THREAD);
        taskBean.setMemory(AppConstant.P_TASK_MEMORY);
        taskBean.setKeyBean(AppConstant.keyBean);
        taskBean.setType(AppConstant.chiaPTypeBean.getType());
        data.add(taskBean);
    }

    @Override
    public ObservableList<TaskBean> getTask() {
        return data == null ? FXCollections.observableArrayList() : data;
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
            } else if ("创建时间".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("createTimeFormat"));
            } else if ("类型".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("type"));
            } else if ("进度".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("progress"));
            } else if ("公共指纹".equals(observableList.get(i).getText())) {
                observableList.get(i).setCellValueFactory(new PropertyValueFactory<TaskBean, String>("finger"));
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
