package sample.module;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import sample.Controller;
import sample.adapter.BaseListViewAdapter;
import sample.bean.TaskBean;
import sample.constant.AppConstant;
import sample.dao.OnTaskModuleCallBack;
import sample.module.base.BaseTabModule;
import sample.utils.LogUtils;

/**
 * 任务模块
 */
public class TabTaskModule extends BaseTabModule implements EventHandler<ActionEvent>, OnTaskModuleCallBack {
    /**
     * 主控
     */
    private Controller controller;
    private ObservableList<TaskBean> data;

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;

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
        taskBean.setTime(3000);
        taskBean.setTarget(targetDirectory);
        taskBean.setType("");
        taskBean.setThread(AppConstant.P_TASK_THREAD);
        taskBean.setMemory(AppConstant.P_TASK_MEMORY);
        data.add(taskBean);

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
}
