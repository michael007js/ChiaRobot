package sample.module;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import sample.Controller;
import sample.adapter.BaseListViewAdapter;
import sample.module.base.BaseTabModule;
import sample.utils.AlertUtils;
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
    private BaseListViewAdapter cacheAdapter = new BaseListViewAdapter<>(new BaseListViewAdapter.OnBaseListViewAdapterCallBacK<String>() {
        @Override
        public Node bindView(String item) {
            return new Label(item);
        }

        @Override
        public void onItemClick(String item, int selectedIndex) {
            if (selectedIndex < cacheList.size()) {
                UIUtils.setText(controller.labelDiskCacheSelect, cacheList.get(selectedIndex));
                controller.buttonDiskCacheToTarget.setDisable(false);
            }
        }
    });
    /**
     * 缓存目录列表数据
     */
    private ArrayList<String> cacheList = new ArrayList<>();
    /**
     * 存放目录适配器
     */
    private BaseListViewAdapter targetAdapter = new BaseListViewAdapter<>(new BaseListViewAdapter.OnBaseListViewAdapterCallBacK<String>() {
        @Override
        public Node bindView(String item) {
            return new Label(item);
        }

        @Override
        public void onItemClick(String item, int selectedIndex) {
            if (selectedIndex < targetList.size()) {
                UIUtils.setText(controller.labelDiskTargetSelect, targetList.get(selectedIndex));
                controller.buttonDiskTargetToCache.setDisable(false);
            }
        }
    });
    /**
     * 存放目录列表数据
     */
    private ArrayList<String> targetList = new ArrayList<>();

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        controller.buttonDiskCacheAdd.setOnAction(this);
        controller.buttonDiskCacheDelete.setOnAction(this);
        controller.buttonDiskCacheToTarget.setOnAction(this);
        controller.buttonDiskTargetAdd.setOnAction(this);
        controller.buttonDiskTargetDelete.setOnAction(this);
        controller.buttonDiskTargetToCache.setOnAction(this);
    }

    @Override
    public void baseDirectorySettingChanged() {

    }


    @SuppressWarnings("DuplicatedCode")
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == controller.buttonDiskCacheAdd) {
            String path = AlertUtils.showDirectory(lastPath);
            if (StringUtils.isEmpty(path)) {
                AlertUtils.showError("", "缓存目录路径为空");
                return;
            }
            for (int i = 0; i < cacheList.size(); i++) {
                if (cacheList.get(i).equals(path)) {
                    AlertUtils.showError("", "缓存目录已存在路径");
                    return;
                }
            }
            cacheList.add(path);
            lastPath = path;
            UIUtils.setData(cacheAdapter, controller.listViewDiskDirectoryCache, cacheList);
        } else if (event.getSource() == controller.buttonDiskCacheDelete) {
            int selectIndex = controller.listViewDiskDirectoryCache.getSelectionModel().getSelectedIndex();
            if (selectIndex >= 0 && selectIndex < cacheList.size()) {
                cacheList.remove(selectIndex);
                cacheAdapter.setData(controller.listViewDiskDirectoryCache, cacheList);
                UIUtils.setText(controller.labelDiskCacheSelect, "");
                controller.buttonDiskCacheToTarget.setDisable(true);
            }
        } else if (event.getSource() == controller.buttonDiskCacheToTarget) {
            if (StringUtils.isEmpty(controller.labelDiskCacheSelect.getText())) {
                AlertUtils.showError("", "请选中要转移到存放目录的路径");
                return;
            }
            int selectIndex = controller.listViewDiskDirectoryCache.getSelectionModel().getSelectedIndex();
            if (selectIndex >= 0 && selectIndex < cacheList.size()) {
                for (int i = 0; i < targetList.size(); i++) {
                    if (cacheList.get(selectIndex).equals(targetList.get(i))) {
                        AlertUtils.showError("", "存放目录已存在此路径");
                        return;
                    }
                }
                targetList.add(cacheList.get(selectIndex));
                cacheList.remove(selectIndex);
                cacheAdapter.setData(controller.listViewDiskDirectoryCache, cacheList);
                targetAdapter.setData(controller.listViewDiskDirectoryTarget, targetList);
                UIUtils.setText(controller.labelDiskCacheSelect, "");
                controller.buttonDiskCacheToTarget.setDisable(true);
            }
        } else if (event.getSource() == controller.buttonDiskTargetAdd) {
            String path = AlertUtils.showDirectory(lastPath);
            if (StringUtils.isEmpty(path)) {
                AlertUtils.showError("", "存放目录路径为空");
                return;
            }
            for (int i = 0; i < targetList.size(); i++) {
                if (cacheList.get(i).equals(path)) {
                    AlertUtils.showError("", "存放目录已存在路径");
                    return;
                }
            }
            targetList.add(path);
            lastPath = path;
            UIUtils.setData(targetAdapter, controller.listViewDiskDirectoryTarget, targetList);
        } else if (event.getSource() == controller.buttonDiskTargetDelete) {
            int selectIndex = controller.listViewDiskDirectoryTarget.getSelectionModel().getSelectedIndex();
            if (selectIndex >= 0 && selectIndex < targetList.size()) {
                targetList.remove(controller.listViewDiskDirectoryTarget.getSelectionModel().getSelectedIndex());
                targetAdapter.setData(controller.listViewDiskDirectoryTarget, targetList);
                UIUtils.setText(controller.labelDiskTargetSelect, "");
                controller.buttonDiskTargetToCache.setDisable(true);
            }
        } else if (event.getSource() == controller.buttonDiskTargetToCache) {
            if (StringUtils.isEmpty(controller.labelDiskTargetSelect.getText())) {
                AlertUtils.showError("", "请选中要转移到缓存目录的路径");
                return;
            }
            int selectIndex = controller.listViewDiskDirectoryTarget.getSelectionModel().getSelectedIndex();
            if (selectIndex >= 0 && selectIndex < targetList.size()) {
                for (int i = 0; i < cacheList.size(); i++) {
                    if (targetList.get(selectIndex).equals(cacheList.get(i))) {
                        AlertUtils.showError("", "缓存目录已存在此路径");
                        return;
                    }
                }
                cacheList.add(targetList.get(selectIndex));
                targetList.remove(selectIndex);
                targetAdapter.setData(controller.listViewDiskDirectoryTarget, targetList);
                cacheAdapter.setData(controller.listViewDiskDirectoryCache, cacheList);
                UIUtils.setText(controller.labelDiskTargetSelect, "");
                controller.buttonDiskTargetToCache.setDisable(true);
            }
        }
    }

}
