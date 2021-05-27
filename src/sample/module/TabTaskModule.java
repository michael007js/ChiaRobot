package sample.module;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller;
import sample.dao.OnTaskModuleCallBack;
import sample.module.base.BaseTabModule;
import sample.utils.LogUtils;

/**
 * 任务模块
 */
public class TabTaskModule extends BaseTabModule implements EventHandler<ActionEvent> , OnTaskModuleCallBack {
    /**
     * 主控
     */
    private Controller controller;



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
        LogUtils.e(cacheDirectory,targetDirectory);
    }
}
