package sample.module;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sample.Controller;
import sample.adapter.BaseChoiceBoxAdapter;
import sample.bean.KeyBean;
import sample.constant.AppConstant;
import sample.module.base.BaseTabModule;
import sample.utils.MichaelUtils;
import sample.utils.StringUtils;
import sample.utils.UIUtils;

public class TabSystemModule extends BaseTabModule {
    /**
     * 主控
     */
    private Controller controller;

    /**
     * 内存统计计时器
     */
    private DisposableObserver<Long> disposableObserver;

    private BaseChoiceBoxAdapter<KeyBean> keyAdapter = new BaseChoiceBoxAdapter<>();

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        controller.sliderSystemMemoryChange.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                AppConstant.P_TASK_MEMORY = newValue.intValue();
                UIUtils.setText(controller.textFieldSystemMemoryValue, AppConstant.P_TASK_MEMORY + "");
            }
        });
        controller.sliderSystemThreadChange.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                AppConstant.P_TASK_THREAD = newValue.intValue();
                UIUtils.setText(controller.textFieldSystemThreadValue, AppConstant.P_TASK_THREAD + "");
            }
        });
        startMemoryStatistics();
        getKeys();

    }

    @Override
    public void baseDirectorySettingChanged() {
        if (AppConstant.functionEnable) {
            getKeys();
        } else {
            UIUtils.setText(controller.textFieldSystemFarmerPublicKey, "");
            UIUtils.setText(controller.textFieldSystemPoolPublicKey, "");
            UIUtils.setData(keyAdapter, controller.choiceBoxSystemKey, new ArrayList<>());
        }

    }

    /**
     * 获取密钥
     */
    private void getKeys() {
        if (!AppConstant.functionEnable) {
            return;
        }
        String result = MichaelUtils.runByCMD(AppConstant.CHIA_PROGRAM_DIRECTORY + "/" + AppConstant.CHIA_APP_VERSION_DIRECTORY_NAME + "/resources/app.asar.unpacked/daemon/chia.exe" + " keys show ");
        String[] temp = result.split("\n" + "\n");
        ArrayList<KeyBean> list = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            if (!StringUtils.isEmpty(temp[i])) {
                String[] child = temp[i].split("\n");
                if (child.length == 5) {
                    KeyBean keyBean = new KeyBean();
                    keyBean.setFingerprint(MichaelUtils.getSubString(child[0], ": ", ""));
                    keyBean.setMasterPublicKey(MichaelUtils.getSubString(child[1], "): ", ""));
                    keyBean.setFarmerPublicKey(MichaelUtils.getSubString(child[2], "): ", ""));
                    keyBean.setPoolPublicKey(MichaelUtils.getSubString(child[3], "): ", ""));
                    keyBean.setFirstWalletAddress(MichaelUtils.getSubString(child[4], ": ", ""));
                    list.add(keyBean);
                }
            }
        }
        keyAdapter.setOnBaseChoiceBoxAdapterCallBack(new BaseChoiceBoxAdapter.OnBaseChoiceBoxAdapterCallBack<KeyBean>() {
            @Override
            public void onItemClick(KeyBean item) {
                UIUtils.setText(controller.textFieldSystemFarmerPublicKey, item.getFarmerPublicKey());
                UIUtils.setText(controller.textFieldSystemPoolPublicKey, item.getPoolPublicKey());
            }
        });
        UIUtils.setData(keyAdapter, controller.choiceBoxSystemKey, list, 0);
    }

    /**
     * 停止内存统计
     */
    private void stopMemoryStatistics() {
        if (disposableObserver != null && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }

    /**
     * 开始内存统计
     */
    private void startMemoryStatistics() {
        disposableObserver = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        UIUtils.setText(controller.labelSystemMemoryTotal, "总共安装内存:" + MichaelUtils.formatMemory(MichaelUtils.getMemory(true)));
                        UIUtils.setText(controller.labelSystemMemoryCurrent, "当前可用内存:" + MichaelUtils.formatMemory(MichaelUtils.getMemory(false)));
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
