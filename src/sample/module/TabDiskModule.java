package sample.module;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import sample.Controller;
import sample.adapter.BaseListViewAdapter;
import sample.constant.AppConstant;
import sample.module.base.BaseTabModule;
import sample.utils.AlertUtils;
import sample.utils.LogUtils;
import sample.utils.StringUtils;
import sample.utils.UIUtils;

/**
 * 磁盘模块
 */
public class TabDiskModule extends BaseTabModule implements EventHandler<ActionEvent> {
    /**
     * 主控
     */
    private Controller controller;
    /**
     * 用来记录上次打开的目录
     */
    private String lastPath = "C:/";
    /**
     * 缓存目录适配器
     */
    private BaseListViewAdapter cacheAdapter;
    /**
     * 缓存目录列表数据
     */
    private ArrayList<String> cacheList = new ArrayList<>();
    /**
     * 存放目录适配器
     */
    private BaseListViewAdapter targetAdapter;
    /**
     * 存放目录列表数据
     */
    private ArrayList<String> targetList = new ArrayList<>();

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        controller.getBtn_disk_cache_add().setOnAction(this);
        controller.getBtn_disk_cache_delete().setOnAction(this);
        controller.getBtn_disk_cache_to_target().setOnAction(this);
        controller.getBtn_disk_target_add().setOnAction(this);
        controller.getBtn_disk_target_delete().setOnAction(this);
        controller.getBtn_disk_target_to_cache().setOnAction(this);
    }

    @Override
    public void baseDirectorySettingChanged() {

    }


    @SuppressWarnings("DuplicatedCode")
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == controller.getBtn_disk_cache_add()) {
            String path = AlertUtils.showDirectory(lastPath);
            if (StringUtils.isEmpty(path)) {
                return;
            }
            for (int i = 0; i < cacheList.size(); i++) {
                if (cacheList.get(i).equals(path)) {
                    return;
                }
            }
            cacheList.add(path);
            lastPath = path;
            if (cacheAdapter == null) {
                cacheAdapter = new BaseListViewAdapter<>(new BaseListViewAdapter.OnBaseListViewAdapterCallBacK<String>() {
                    @Override
                    public Node bindView(String item) {
                        return new Label(item);
                    }

                    @Override
                    public void onItemClick(String item) {

                    }
                });
            }
            UIUtils.setData(cacheAdapter, controller.getLv_disk_directory_cache(), cacheList);
        } else if (event.getSource() == controller.getBtn_disk_cache_delete()) {

        } else if (event.getSource() == controller.getBtn_disk_cache_to_target()) {

        } else if (event.getSource() == controller.getBtn_disk_target_add()) {
            String path = AlertUtils.showDirectory(lastPath);
            if (StringUtils.isEmpty(path)) {
                return;
            }
            for (int i = 0; i < targetList.size(); i++) {
                if (cacheList.get(i).equals(path)) {
                    return;
                }
            }
            targetList.add(path);
            lastPath = path;
            if (targetAdapter == null) {
                targetAdapter = new BaseListViewAdapter<>(new BaseListViewAdapter.OnBaseListViewAdapterCallBacK<String>() {
                    @Override
                    public Node bindView(String item) {
                        return new Label(item);
                    }

                    @Override
                    public void onItemClick(String item) {

                    }
                });
            }
            UIUtils.setData(targetAdapter, controller.getLv_disk_directory_target(), targetList);
        } else if (event.getSource() == controller.getBtn_disk_target_delete()) {

        } else if (event.getSource() == controller.getBtn_disk_target_to_cache()) {
            LogUtils.e(controller.getLv_disk_directory_cache().getSelectionModel().getSelectedItem());
        }
    }

}
