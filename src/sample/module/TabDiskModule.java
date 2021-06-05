package sample.module;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import sample.Controller;
import sample.adapter.BaseListViewAdapter;
import sample.constant.AppConstant;
import sample.dao.OnTaskModuleCallBack;
import sample.module.base.BaseTabModule;
import sample.utils.AlertUtils;
import sample.utils.StringUtils;
import sample.utils.UIUtils;

/**
 * 磁盘模块
 */
public class TabDiskModule extends BaseTabModule implements EventHandler<ActionEvent> {
    /**
     * 任务模块回调
     */
    private OnTaskModuleCallBack onDiskModuleCallBack;
    /**
     * 主控
     */
    private Controller controller;
    /**
     * 用来记录上次打开的目录
     */
    private String lastPath = "C:/";
    /**
     * 菜单
     */
    private ArrayList<MenuItem> menuItems = new ArrayList<>();

    {
        menuItems.add(new MenuItem("添加"));
        menuItems.add(new MenuItem("删除"));

    }

    /**
     * 缓存目录适配器
     */
    private BaseListViewAdapter cacheAdapter = new BaseListViewAdapter<>(new BaseListViewAdapter.OnBaseListViewAdapterCallBacK<String>() {
        @Override
        public Node bindView(String item) {
            return new Label(item);
        }

        @Override
        public void onItemClick(ArrayList<String> list, String item, int selectedIndex) {
            if (selectedIndex < list.size()) {
                UIUtils.setText(controller.labelDiskCacheSelect, list.get(selectedIndex));
                controller.buttonDiskCacheToTarget.setDisable(false);
                updateButtonStatus();

            }
        }

        @Override
        public void onMenuItemClick(MenuItem menuItem) {
            if ("添加".equals(menuItem.getText())) {
                String path = AlertUtils.showDirectory(lastPath);
                if (StringUtils.isEmpty(path)) {
                    AlertUtils.showError("", "缓存目录路径为空");
                    return;
                }
                for (int i = 0; i < cacheAdapter.getData().size(); i++) {
                    if (cacheAdapter.getData().get(i).equals(path)) {
                        AlertUtils.showError("", "缓存目录已存在路径");
                        return;
                    }
                }
                cacheAdapter.getData().add(path);
                lastPath = path;
                UIUtils.setData(cacheAdapter, controller.listViewDiskDirectoryCache, cacheAdapter.getData(), menuItems);
            } else if ("删除".equals(menuItem.getText())) {
                int selectIndex = controller.listViewDiskDirectoryCache.getSelectionModel().getSelectedIndex();
                if (selectIndex >= 0 && selectIndex < cacheAdapter.getData().size()) {
                    cacheAdapter.getData().remove(selectIndex);
                    cacheAdapter.setData(controller.listViewDiskDirectoryCache, cacheAdapter.getData(), menuItems);
                    UIUtils.setText(controller.labelDiskCacheSelect, "");
                    controller.buttonDiskCacheToTarget.setDisable(true);
                    updateButtonStatus();
                }
            }
        }
    });

    /**
     * 存放目录适配器
     */
    private BaseListViewAdapter targetAdapter = new BaseListViewAdapter<>(new BaseListViewAdapter.OnBaseListViewAdapterCallBacK<String>() {
        @Override
        public Node bindView(String item) {
            return new Label(item);
        }

        @Override
        public void onItemClick(ArrayList<String> list, String item, int selectedIndex) {
            if (selectedIndex < list.size()) {
                UIUtils.setText(controller.labelDiskTargetSelect, list.get(selectedIndex));
                controller.buttonDiskTargetToCache.setDisable(false);
                updateButtonStatus();
            }
        }

        @Override
        public void onMenuItemClick(MenuItem menuItem) {
            if ("添加".equals(menuItem.getText())) {
                String path = AlertUtils.showDirectory(lastPath);
                if (StringUtils.isEmpty(path)) {
                    AlertUtils.showError("", "存放目录路径为空");
                    return;
                }
                for (int i = 0; i < targetAdapter.getData().size(); i++) {
                    if (cacheAdapter.getData().get(i).equals(path)) {
                        AlertUtils.showError("", "存放目录已存在路径");
                        return;
                    }
                }
                targetAdapter.getData().add(path);
                lastPath = path;
                UIUtils.setData(targetAdapter, controller.listViewDiskDirectoryTarget, targetAdapter.getData(), menuItems);
            } else if ("删除".equals(menuItem.getText())) {
                int selectIndex = controller.listViewDiskDirectoryTarget.getSelectionModel().getSelectedIndex();
                if (selectIndex >= 0 && selectIndex < targetAdapter.getData().size()) {
                    targetAdapter.getData().remove(controller.listViewDiskDirectoryTarget.getSelectionModel().getSelectedIndex());
                    targetAdapter.setData(controller.listViewDiskDirectoryTarget, targetAdapter.getData(), menuItems);
                    UIUtils.setText(controller.labelDiskTargetSelect, "");
                    controller.buttonDiskTargetToCache.setDisable(true);
                    updateButtonStatus();
                }
            }
        }
    });


    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        controller.buttonDiskCacheToTarget.setOnAction(this);
        controller.buttonDiskTargetToCache.setOnAction(this);
        controller.buttonDiskAddToTaskQueue.setOnAction(this);

        cacheAdapter.getData().add("e:/chia_temp");
        lastPath = "e:/chia_temp";
        UIUtils.setData(cacheAdapter, controller.listViewDiskDirectoryCache, cacheAdapter.getData(), menuItems);

        targetAdapter.getData().add("e:/chia_target");
        lastPath = "e:/chia_target";
        UIUtils.setData(targetAdapter, controller.listViewDiskDirectoryTarget, targetAdapter.getData(), menuItems);
    }

    @Override
    public void baseDirectorySettingChanged() {
        updateButtonStatus();
    }


    @SuppressWarnings("DuplicatedCode")
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == controller.buttonDiskCacheToTarget) {
            if (StringUtils.isEmpty(controller.labelDiskCacheSelect.getText())) {
                AlertUtils.showError("", "请选中要转移到存放目录的路径");
                return;
            }
            int selectIndex = controller.listViewDiskDirectoryCache.getSelectionModel().getSelectedIndex();
            if (selectIndex >= 0 && selectIndex < cacheAdapter.getData().size()) {
                for (int i = 0; i < targetAdapter.getData().size(); i++) {
                    if (cacheAdapter.getData().get(selectIndex).equals(targetAdapter.getData().get(i))) {
                        AlertUtils.showError("", "存放目录已存在此路径");
                        return;
                    }
                }
                targetAdapter.getData().add((String) cacheAdapter.getData().get(selectIndex));
                cacheAdapter.getData().remove(selectIndex);
                cacheAdapter.setData(controller.listViewDiskDirectoryCache, cacheAdapter.getData(), menuItems);
                targetAdapter.setData(controller.listViewDiskDirectoryTarget, targetAdapter.getData(), menuItems);
                UIUtils.setText(controller.labelDiskCacheSelect, "");
                controller.buttonDiskCacheToTarget.setDisable(true);
                updateButtonStatus();
            }
        } else if (event.getSource() == controller.buttonDiskTargetToCache) {
            if (StringUtils.isEmpty(controller.labelDiskTargetSelect.getText())) {
                AlertUtils.showError("", "请选中要转移到缓存目录的路径");
                return;
            }
            int selectIndex = controller.listViewDiskDirectoryTarget.getSelectionModel().getSelectedIndex();
            if (selectIndex >= 0 && selectIndex < targetAdapter.getData().size()) {
                for (int i = 0; i < cacheAdapter.getData().size(); i++) {
                    if (targetAdapter.getData().get(selectIndex).equals(cacheAdapter.getData().get(i))) {
                        AlertUtils.showError("", "缓存目录已存在此路径");
                        return;
                    }
                }
                cacheAdapter.getData().add(targetAdapter.getData().get(selectIndex));
                targetAdapter.getData().remove(selectIndex);
                targetAdapter.setData(controller.listViewDiskDirectoryTarget, targetAdapter.getData(), menuItems);
                cacheAdapter.setData(controller.listViewDiskDirectoryCache, cacheAdapter.getData(), menuItems);
                UIUtils.setText(controller.labelDiskTargetSelect, "");
                controller.buttonDiskTargetToCache.setDisable(true);
                updateButtonStatus();
            }
        } else if (event.getSource() == controller.buttonDiskAddToTaskQueue) {
            if (onDiskModuleCallBack != null && !StringUtils.isEmpty(controller.labelDiskCacheSelect.getText()) && !StringUtils.isEmpty(controller.labelDiskTargetSelect.getText())) {
                onDiskModuleCallBack.onCreateTaskDirectory(controller.labelDiskCacheSelect.getText(), controller.labelDiskTargetSelect.getText());
            }
        }
    }

    /**
     * 设置任务模块回调
     */
    public void setOnTaskModuleCallBack(OnTaskModuleCallBack onDiskModuleCallBack) {
        this.onDiskModuleCallBack = onDiskModuleCallBack;
    }

    /**
     * 更新按钮状态
     */
    private void updateButtonStatus() {
        if (AppConstant.functionEnable &&
                !StringUtils.isEmpty(controller.labelDiskCacheSelect.getText()) &&
                !StringUtils.isEmpty(controller.labelDiskTargetSelect.getText()) &&
                AppConstant.keyBean != null &&
                AppConstant.chiaPTypeBean != null
        ) {
            controller.buttonDiskAddToTaskQueue.setDisable(false);
        } else {
            controller.buttonDiskAddToTaskQueue.setDisable(true);
        }
    }


}
