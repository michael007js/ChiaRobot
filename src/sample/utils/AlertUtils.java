package sample.utils;

import com.sun.javafx.application.PlatformImpl;

import java.io.File;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class AlertUtils {
    public static void showInfo(String title, String content) {
        showInfo(title,content,null);
    }

    public static void showInfo(String title, String content, Node node) {
        PlatformImpl.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(title);
                alert.setContentText(content);
                if (node!=null) {
                    alert.getDialogPane().setExpandableContent(node);
                }
                alert.show();
            }
        });
    }
    public static void showError(String title, String content) {
        PlatformImpl.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setContentText(content);
                alert.show();
            }
        });

    }

    public static void showWarn(String title, String content) {
        PlatformImpl.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(title);
                alert.setContentText(content);
                alert.show();
            }
        });
    }

    public static boolean showConfirm(String title, String headerText, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        if (headerText != null) {
            alert.setHeaderText(headerText);
        }
        alert.setContentText(content);
        Optional<ButtonType> buttonType = alert.showAndWait();
        return buttonType.get().getButtonData().equals(ButtonBar.ButtonData.OK_DONE);
    }

    public static String showDirectory() {
        return showDirectory(null);
    }

    public static String showDirectory(String path) {
        File file = null;
        if (!StringUtils.isEmpty(path)) {
            file=new File(path);
        }
        DirectoryChooser directoryChooser = new DirectoryChooser();
        if (file!=null&&(file.isDirectory()||file.isFile())){
            directoryChooser.setInitialDirectory(file);
        }
        File directory = directoryChooser.showDialog(new Stage());
        return directory != null ? directory.getAbsolutePath() : "";
    }

}
