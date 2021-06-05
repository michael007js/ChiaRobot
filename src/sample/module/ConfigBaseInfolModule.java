package sample.module;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sample.Controller;
import sample.bean.ChiaCalculationPowerBean;
import sample.constant.AppConstant;
import sample.dao.OnCmdStringResponseCallBack;
import sample.dao.OnTaskModuleCallBack;
import sample.http.HttpCallBack;
import sample.http.HttpService;
import sample.module.base.BaseTabModule;
import sample.utils.*;

/**
 * 基础配置信息模块
 */
@SuppressWarnings("ALL")
public class ConfigBaseInfolModule extends BaseTabModule implements EventHandler<ActionEvent> {
    /**
     * 任务模块回调
     */
    private OnTaskModuleCallBack onTaskModuleCallBack;
    /**
     * 主控制器
     */
    private Controller controller;
    /**
     * 算力/价格定时器
     */
    private DisposableObserver<Long> powerPriceDisposableObserver;
    /**
     * 算力/价格定时器
     */
    private DisposableObserver<Long> pTaskDisposableObserver;
    /**
     * chia程序目录与chia配置文件目录初始化状态
     */
    private boolean statusInitChiaProgramDirectory, statusInitChiaConfigFileDirectory;

    @Override
    public void initialize(Controller mainController) {
        this.controller = mainController;
        UIUtils.setText(controller.labelNormalCurrentCalculationPower, "全网算力:获取中...");
        UIUtils.setText(controller.labelNormalCurrentPrice, "当前价格:获取中...");
        createCalculationPowerAndPriceTimer(false);
        statusInitChiaProgramDirectory = initChiaProgramDirectory(false, "C:/Users/" + System.getProperty("user.name") + "/AppData/Local/chia-blockchain");
        statusInitChiaConfigFileDirectory = initChiaConfigFileDirectory(false, "C:/Users/" + System.getProperty("user.name") + "/.chia");
        AppConstant.functionEnable = statusInitChiaProgramDirectory && statusInitChiaConfigFileDirectory;
        controller.buttonNormalChangeProgramDirectory.setOnAction(this::handle);
        controller.buttonNormalChangeConfigFileDirectory.setOnAction(this::handle);
        controller.buttonNormalStartPTask.setOnAction(this::handle);

        controller.textFieldNormalRunningTask.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (StringUtils.isEmpty(newValue)) {
                    controller.textFieldNormalRunningTask.setText("1");
                    AppConstant.P_RUNNING_TASK = 1;
                    return;
                }
                if (!newValue.matches("^[1-9]\\d*$")) {
                    controller.textFieldNormalRunningTask.setText("1");
                    AppConstant.P_RUNNING_TASK = 1;
                    return;
                }
                AppConstant.P_RUNNING_TASK = Integer.parseInt(newValue);
            }
        });
    }

    public void setOnTaskModuleCallBack(OnTaskModuleCallBack onTaskModuleCallBack) {
        this.onTaskModuleCallBack = onTaskModuleCallBack;
    }

    @Override
    public void baseDirectorySettingChanged() {
        //TODO 本模块中无实际使用意义，仅在new对象时重写此方法，基础设置被用户改变后可触发回调通知调用者
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == controller.buttonNormalChangeProgramDirectory) {
            String path = AlertUtils.showDirectory(AppConstant.CHIA_PROGRAM_DIRECTORY);
            if (!StringUtils.isEmpty(path)) {
                statusInitChiaProgramDirectory = initChiaProgramDirectory(true, path);
                AppConstant.functionEnable = statusInitChiaProgramDirectory && statusInitChiaConfigFileDirectory;
                baseDirectorySettingChanged();
            }
        } else if (event.getSource() == controller.buttonNormalChangeConfigFileDirectory) {
            String path = AlertUtils.showDirectory(AppConstant.CHIA_CONFIG_FILE_DIRECTORY);
            if (!StringUtils.isEmpty(path)) {
                statusInitChiaConfigFileDirectory = initChiaConfigFileDirectory(true, path);
                AppConstant.functionEnable = statusInitChiaProgramDirectory && statusInitChiaConfigFileDirectory;
                baseDirectorySettingChanged();
            }
        } else if (event.getSource() == controller.buttonNormalStartPTask) {
            if ("开始P盘".equals(controller.buttonNormalStartPTask.getText())) {
                if (onTaskModuleCallBack.getTask().size() == 0) {
                    AlertUtils.showError("错误", "请先创建P盘任务");
                    return;
                }
                createTaskTimer();
                controller.buttonNormalStartPTask.setText("停止");
            } else {
                clearTaskTimer();
                controller.buttonNormalStartPTask.setText("开始P盘");
            }


        }
    }

    /**
     * 初始化程序目录
     *
     * @param byUser 是否为用户手动触发
     */
    private boolean initChiaProgramDirectory(boolean byUser, String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            if (!byUser) {
                AlertUtils.showError("错误", "Chia程序目录未找到，请手动选择");
            }
            if (byUser) {
                AlertUtils.showError("错误", "Chia程序目录未找到，请手动选择");
            }
            AppConstant.CHIA_PROGRAM_DIRECTORY = "";
            UIUtils.setText(controller.textFieldNormalProgramDirectory, AppConstant.CHIA_PROGRAM_DIRECTORY);
            initChiaDirectoryName(byUser, AppConstant.CHIA_PROGRAM_DIRECTORY);
            return false;
        }
        AppConstant.CHIA_PROGRAM_DIRECTORY = file.getAbsolutePath();
        UIUtils.setText(controller.textFieldNormalProgramDirectory, AppConstant.CHIA_PROGRAM_DIRECTORY);
        return initChiaDirectoryName(byUser, AppConstant.CHIA_PROGRAM_DIRECTORY);
    }

    /**
     * 初始化配置文件目录
     *
     * @param byUser 是否为用户手动触发
     */
    private boolean initChiaConfigFileDirectory(boolean byUser, String path) {
        File file = new File(path);
        if (!file.exists() || !file.isDirectory() || !file.getAbsolutePath().endsWith(".chia")) {
            if (!byUser) {
                AlertUtils.showError("错误", "Chia配置文件目录未找到，请手动选择");
            }
            if (byUser) {
                AlertUtils.showError("错误", "Chia配置文件目录不正确，\n该路径一般为C:\\Users\\Administrator\\.chia");
            }
            AppConstant.CHIA_CONFIG_FILE_DIRECTORY = "";
            UIUtils.setText(controller.textFieldNormalConfigFileDirectory, AppConstant.CHIA_CONFIG_FILE_DIRECTORY);
            return false;
        }
        AppConstant.CHIA_CONFIG_FILE_DIRECTORY = file.getAbsolutePath();
        UIUtils.setText(controller.textFieldNormalConfigFileDirectory, AppConstant.CHIA_CONFIG_FILE_DIRECTORY);
        return true;
    }

    /**
     * 初始化APP版本目录名
     *
     * @param byUser 是否为用户手动触发
     */
    private boolean initChiaDirectoryName(boolean byUser, String path) {
        File file = new File(path);
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
            AppConstant.CHIA_APP_VERSION_DIRECTORY_NAME = appDirs.get(appDirs.size() - 1).getName();
            UIUtils.setText(controller.textFieldNormalAppVersionDirectory, AppConstant.CHIA_APP_VERSION_DIRECTORY_NAME);
            return true;
        } else {
            if (!byUser) {
                AlertUtils.showError("错误", "Chia APP版本目录名未找到，请手动选择Chia程序目录");
            }
            if (byUser) {
                AlertUtils.showError("错误", "Chia APP版本目录名不正确，请确认Chia程序目录是否正确，\n该路径一般为C:\\Users\\Administrator\\AppData\\Local\\chia-blockchain");
            }
            AppConstant.CHIA_APP_VERSION_DIRECTORY_NAME = "";
            UIUtils.setText(controller.textFieldNormalAppVersionDirectory, AppConstant.CHIA_APP_VERSION_DIRECTORY_NAME);
            return false;
        }

    }

    /**
     * 创建P盘定时器
     *
     * @param byUser 是否为用户手动触发
     */
    private void createTaskTimer() {
        if (pTaskDisposableObserver != null) {
            return;
        }
        pTaskDisposableObserver = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        int total = 0;
                        for (int i = 0; i < onTaskModuleCallBack.getTask().size(); i++) {
                            if (onTaskModuleCallBack.getTask().get(i).isRunning()) {
                                total++;
                            }

                        }
                        if (total >= AppConstant.P_RUNNING_TASK) {
                            return;
                        }

                        for (int i = 0; i < onTaskModuleCallBack.getTask().size(); i++) {
                            if (!onTaskModuleCallBack.getTask().get(i).isRunning()) {
                                onTaskModuleCallBack.getTask().get(i).setRunning(true);
                                String baseCommend = AppConstant.CHIA_PROGRAM_DIRECTORY + "\\" + AppConstant.CHIA_APP_VERSION_DIRECTORY_NAME + "\\resources\\app.asar.unpacked\\daemon\\";
                                baseCommend += "chia plots create -k " + onTaskModuleCallBack.getTask().get(i).getType() + " -n 1 -t " + onTaskModuleCallBack.getTask().get(i).getCache() + " -d " + onTaskModuleCallBack.getTask().get(i).getTarget() + " -b " + onTaskModuleCallBack.getTask().get(i).getMemory() + " -r " + onTaskModuleCallBack.getTask().get(i).getThread() + " -u 128";
                                String finalBaseCommend = baseCommend;
                                int finalI = i;
                                new Thread(){
                                   @Override
                                   public void run() {
                                       super.run();
                                       MichaelUtils.runByCMD(new OnCmdStringResponseCallBack() {
                                           @Override
                                           public void onResult(String currentLine, String full, int lineCount) {
                                               onTaskModuleCallBack.getTask().get(finalI).setLog(full);
                                               onTaskModuleCallBack.getTask().get(finalI).setProgress(ProgressHelper.getProgress(lineCount));
                                           }
                                       }, "cmd.exe", "/c", finalBaseCommend);
                                   }
                               }.start();
                                total++;
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 清除P盘定时器
     */
    private void clearTaskTimer() {
        if (pTaskDisposableObserver != null && !pTaskDisposableObserver.isDisposed()) {
            pTaskDisposableObserver.dispose();
        }
    }


    /**
     * 创建算力/价格定时器
     *
     * @param byUser 是否为用户手动触发
     */
    private void createCalculationPowerAndPriceTimer(boolean byUser) {
        if (byUser) {
            getCalculationPowerAndPrice();
        } else {
            clearCalculationPowerAndPriceTimer();
            powerPriceDisposableObserver = Observable.interval(10, TimeUnit.MINUTES)
                    .subscribeWith(new DisposableObserver<Long>() {
                        @Override
                        protected void onStart() {
                            super.onStart();
                            onNext(0L);
                        }

                        @Override
                        public void onNext(Long aLong) {
                            getCalculationPowerAndPrice();
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

    /**
     * 清除算力/价格定时器
     */
    private void clearCalculationPowerAndPriceTimer() {
        if (powerPriceDisposableObserver != null && !powerPriceDisposableObserver.isDisposed()) {
            powerPriceDisposableObserver.dispose();
        }
    }

    /**
     * 获取算力/价格
     */
    private void getCalculationPowerAndPrice() {
        HttpService.getInstance().getByOkHttp(AppConstant.CHIA_CALCULATION_POWER_URL, new HttpCallBack() {
            @Override
            public void success(String success) {
                ChiaCalculationPowerBean chiaCalculationPowerBean = JsonUtils.formatToObject(success, ChiaCalculationPowerBean.class);
                if (chiaCalculationPowerBean == null || chiaCalculationPowerBean.getData() == null || chiaCalculationPowerBean.getData().size() == 0) {
                    UIUtils.setText(controller.labelNormalCurrentCalculationPower, "全网算力:获取失败");
                } else {
                    UIUtils.setText(controller.labelNormalCurrentCalculationPower, "全网算力:" + StringUtils.double2String(chiaCalculationPowerBean.getData().get(0), 3) + "PiB");
                }
            }

            @Override
            public void fail(String fail) {
                UIUtils.setText(controller.labelNormalCurrentPrice, "全网算力:获取失败" + fail);
            }
        });
        HttpService.getInstance().getByOkHttp(AppConstant.CHIA_PRICE_URL, new HttpCallBack() {
            @Override
            public void success(String success) {
                ChiaCalculationPowerBean chiaCalculationPowerBean = JsonUtils.formatToObject(success, ChiaCalculationPowerBean.class);
                if (chiaCalculationPowerBean == null || chiaCalculationPowerBean.getData() == null || chiaCalculationPowerBean.getData().size() == 0) {
                    UIUtils.setText(controller.labelNormalCurrentPrice, "当前价格:获取失败");
                } else {
                    UIUtils.setText(controller.labelNormalCurrentPrice, "当前价格:$" + StringUtils.double2String(chiaCalculationPowerBean.getData().get(0), 2));
                }
            }

            @Override
            public void fail(String fail) {
                UIUtils.setText(controller.labelNormalCurrentPrice, "当前价格:获取失败" + fail);
            }
        });
    }
}
