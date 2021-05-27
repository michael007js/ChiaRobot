package sample.module;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller;
import sample.bean.ChiaNodeBean;
import sample.constant.AppConstant;
import sample.http.HttpCallBack;
import sample.http.HttpService;
import sample.module.base.BaseTabModule;
import sample.utils.AlertUtils;
import sample.utils.JsonUtils;
import sample.utils.MichaelUtils;
import sample.utils.UIUtils;

/**
 * 节点模块
 */
public class TabNodeModule extends BaseTabModule implements EventHandler<ActionEvent> {
    /**
     * 主控制器
     */
    private Controller controller;
    /**
     * 节点同步计时器
     */
    private DisposableObserver<Long> disposableObserver;
    /**
     * 是否在同步节点
     */
    private boolean isSyncNode;

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        controller.buttonNodeSyncByUser.setOnAction(this::handle);
        controller.checkBoxNodeSyncByAuto.setOnAction(this::handle);
    }

    @Override
    public void baseDirectorySettingChanged() {
        if (!AppConstant.functionEnable && isSyncNode) {
            UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, "基础设置被改变且设置不正确，节点模块功能即将停止...");
            stopNodeSyncTimer(true);
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (!AppConstant.functionEnable) {
            AlertUtils.showError("错误", "基础配置不正常，请安装Chia客户端或确认Chia相关路径与版本号是否正确");
            return;
        }

        if (event.getSource() == controller.buttonNodeSyncByUser) {
            if (isSyncNode) {
                stopNodeSyncTimer(true);
                UIUtils.setText(controller.buttonNodeSyncByUser,  "停止中");
                controller.buttonNodeSyncByUser.setDisable(true);
                controller.checkBoxNodeSyncByAuto.setDisable(true);
            } else {
                UIUtils.setText(controller.buttonNodeSyncByUser,  "结束同步");
                startNodeSyncTimer();
                controller.checkBoxNodeSyncByAuto.setDisable(true);
            }

        } else if (event.getSource() == controller.checkBoxNodeSyncByAuto) {
            UIUtils.setText(controller.buttonNodeSyncByUser,  "开始同步");
            if (isSyncNode) {
                controller.buttonNodeSyncByUser.setDisable(true);
                controller.checkBoxNodeSyncByAuto.setSelected(false);
                stopNodeSyncTimer(true);
            } else {
                controller.buttonNodeSyncByUser.setDisable(true);
                controller.checkBoxNodeSyncByAuto.setSelected(true);
                startNodeSyncTimer();
            }


        }
    }


    /**
     * 停止节点同步
     * @param showLog 显示日志
     */
    private void stopNodeSyncTimer(boolean showLog) {
        if (showLog) {
            UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, "节点同步停止中...");
        }
        isSyncNode = false;
        if (disposableObserver != null && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }

    /**
     * 开始节点同步
     */
    private void startNodeSyncTimer() {
        int time=30;
        stopNodeSyncTimer(false);
        isSyncNode = true;
        disposableObserver = Observable.interval(time, TimeUnit.MINUTES)
                .subscribeWith(new DisposableObserver<Long>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, false, "开始同步节点" + (controller.checkBoxNodeSyncByAuto.isSelected() ? "("+time+"分钟同步一轮)" : ""));
                        onNext(0L);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        HttpService.getInstance().getByOkHttp(AppConstant.CHIA_NODE_URL, new HttpCallBack() {
                            @Override
                            public void success(String success) {
                                ChiaNodeBean chiaNodeBean=JsonUtils.formatToObject(success,ChiaNodeBean.class);

                                if (chiaNodeBean.getNodes()!=null){
                                    UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, "获取到" +chiaNodeBean.getNodes().size() + "个节点");
                                    if ( chiaNodeBean.getNodes().size() > 0) {
                                        UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, "正在同步节点中...");
                                    }
                                    for (int i = 0; i < chiaNodeBean.getNodes().size(); i++) {
                                        if (!isSyncNode) {
                                            stopNodeSyncTimer(false);
                                            break;
                                        }
                                        UIUtils.setTextAreaLog(controller.textAreaNodeLog, false, MichaelUtils.runByCMD(AppConstant.CHIA_PROGRAM_DIRECTORY + "/" + AppConstant.CHIA_APP_VERSION_DIRECTORY_NAME + "/resources/app.asar.unpacked/daemon/chia.exe" + " show -a " + chiaNodeBean.getNodes().get(i).getIp() + ":"+chiaNodeBean.getNodes().get(i).getPort()));
                                    }
                                    UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, "节点同步完成");
                                    isSyncNode=false;
                                    controller.buttonNodeSyncByUser.setDisable(false);
                                    controller.checkBoxNodeSyncByAuto.setDisable(false);
                                    UIUtils.setText(controller.buttonNodeSyncByUser,  "开始同步");
                                }




                            }

                            @Override
                            public void fail(String fail) {
                                UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, "同步节点失败" + fail);
                                isSyncNode=false;
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        UIUtils.setTextAreaLog(controller.textAreaNodeLog, true, "同步节点失败" + e.getLocalizedMessage()); isSyncNode=false;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}