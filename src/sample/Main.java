package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.constant.AppConstant;
import sample.module.TabSystemModule;
import sample.module.TabNodeModule;
import sample.module.ConfigBaseInfolModule;
import sample.module.base.BaseTabModule;
import sample.utils.AlertUtils;

public class Main extends Application {
    private Controller controller;
    private List<BaseTabModule> moduleList = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle(AppConstant.APP_NAME + " V" + AppConstant.VERSION_NAME + " author by michael");
        primaryStage.setResizable(false);
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root, controller.getRoot_parent().getMaxWidth(), controller.getRoot_parent().getMaxHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
        initialize(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }


    private void initialize(Stage primaryStage) {
        ConfigBaseInfolModule configBaseInfolModule = new ConfigBaseInfolModule() {
            @Override
            public void baseDirectorySettingChanged() {
                super.baseDirectorySettingChanged();
                for (int i = 0; i < moduleList.size(); i++) {
                    moduleList.get(i).baseDirectorySettingChanged();
                }
            }
        };
        configBaseInfolModule.initialize(controller);

        TabNodeModule tabNodeModule = new TabNodeModule();
        tabNodeModule.initialize(controller);
        moduleList.add(tabNodeModule);

        TabSystemModule tabSystemModule = new TabSystemModule();
        tabSystemModule.initialize(controller);
        moduleList.add(tabSystemModule);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
//                if (tabFilmModule != null && tabFilmModule.getFilmCollectionArrayList().size() != 0) {
//                    event.consume();
//                    AlertUtils.showError("错误", "无法关闭，模块正在运行中！");
//                    return;
//                }
                if (AlertUtils.showConfirm("退出", "请确认您的操作", "您真的要关闭" + AppConstant.APP_NAME + "吗？")) {
                    System.exit(0);
                } else {
                    event.consume();
                }

            }
        });
    }
}
