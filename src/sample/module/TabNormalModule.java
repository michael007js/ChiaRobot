package sample.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import sample.Controller;
import sample.bean.ChiaCalculationPowerBean;
import sample.constaant.AppConstant;
import sample.http.HttpCallBack;
import sample.http.HttpService;
import sample.module.base.BaseTabModule;
import sample.utils.*;

public class TabNormalModule extends BaseTabModule {
    private Controller controller;
    private DisposableObserver disposableObserver;

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        UIUtils.setText(controller.getLb_normal_current_calculation_power(), "Chia全网算力:获取中...");
        UIUtils.setText(controller.getLb_normal_current_price(), "Chia当前价格:获取中...");
        createCalculationPowerTimer();
        AppConstant.CHIA_PROGRAM_DIRECTORY = "C:/Users/" + System.getProperty("user.name") + "/AppData/Local/chia-blockchain";
        UIUtils.setText(controller.getTf_normal_program_directory(), AppConstant.CHIA_PROGRAM_DIRECTORY);
        AppConstant.CHIA_CONFIG_FILE_DIRECTORY = "C:/Users/" + System.getProperty("user.name") + "/.chia";
        UIUtils.setText(controller.getTf_normal_config_file_directory(), AppConstant.CHIA_CONFIG_FILE_DIRECTORY);
        getChiaAppDirectory();
    }

    @Override
    public void baseDirectorySettingChanged() {

    }


    private void getChiaAppDirectory() {
        File file = new File(AppConstant.CHIA_PROGRAM_DIRECTORY);
        List<File> appDirs = new ArrayList<>();
        File[] children = file.listFiles();
        if (children != null) {
            for (int i = 0; i < children.length; i++) {
                if (children[i].isDirectory() && children[i].getName().contains("app-")) {
                    appDirs.add(children[i]);
                }
            }
        }
        if (appDirs.size() > 0) {
            Collections.sort(appDirs, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            AppConstant.CHIA_APP_DIRECTORY_NAME = appDirs.get(appDirs.size() - 1).getName();
        }
    }


    private void createCalculationPowerTimer() {
        if (disposableObserver != null && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
        disposableObserver = Observable.timer(1, TimeUnit.MINUTES)
                .subscribeWith(new DisposableObserver<Long>() {

                    @Override
                    protected void onStart() {
                        super.onStart();
                        onNext(0L);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        HttpService.getInstance().getByOkHttp(AppConstant.CHIA_CALCULATION_POWER_URL, new HttpCallBack() {
                            @Override
                            public void success(String success) {
                                ChiaCalculationPowerBean chiaCalculationPowerBean = JsonUtils.formatToObject(success, ChiaCalculationPowerBean.class);
                                if (chiaCalculationPowerBean == null || chiaCalculationPowerBean.getData() == null || chiaCalculationPowerBean.getData().size() == 0) {
                                    UIUtils.setText(controller.getLb_normal_current_calculation_power(), "Chia全网算力:获取失败");
                                } else {
                                    UIUtils.setText(controller.getLb_normal_current_calculation_power(), "Chia全网算力:" + StringUtils.double2String(chiaCalculationPowerBean.getData().get(chiaCalculationPowerBean.getData().size() - 1), 3) + "PiB");
                                }
                            }

                            @Override
                            public void fail(String fail) {
                                UIUtils.setText(controller.getLb_normal_current_price(), "Chia全网算力:获取失败" + fail);
                            }
                        });

                        HttpService.getInstance().getByOkHttp(AppConstant.CHIA_PRICE_URL, new HttpCallBack() {
                            @Override
                            public void success(String success) {
                                ChiaCalculationPowerBean chiaCalculationPowerBean = JsonUtils.formatToObject(success, ChiaCalculationPowerBean.class);
                                if (chiaCalculationPowerBean == null || chiaCalculationPowerBean.getData() == null || chiaCalculationPowerBean.getData().size() == 0) {
                                    UIUtils.setText(controller.getLb_normal_current_price(), "Chia当前价格:获取失败");
                                } else {
                                    UIUtils.setText(controller.getLb_normal_current_price(), "Chia当前价格:$" + StringUtils.double2String(chiaCalculationPowerBean.getData().get(chiaCalculationPowerBean.getData().size() - 1), 2));
                                }
                            }

                            @Override
                            public void fail(String fail) {
                                UIUtils.setText(controller.getLb_normal_current_price(), "Chia当前价格:获取失败" + fail);
                            }
                        });

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
