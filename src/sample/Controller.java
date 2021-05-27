package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Controller implements Initializable {
    @FXML
    public GridPane GridPaneRootParent;
    @FXML
    public Button buttonNodeSyncByUser;
    @FXML
    public CheckBox checkBoxNodeSyncByAuto;
    @FXML
    public TextField textFieldNormalProgramDirectory;
    @FXML
    public Button buttonNormalChangeProgramDirectory;
    @FXML
    public TextField textFieldNormalConfigFileDirectory;
    @FXML
    public TextField textFieldNormalAppVersionDirectory;
    @FXML
    public Button buttonNormalChangeConfigFileDirectory;
    @FXML
    public Label labelNormalCurrentCalculationPower;
    @FXML
    public Label labelNormalCurrentPrice;
    @FXML
    public TextArea textAreaNodeLog;
    @FXML
    public Label labelSystemMemoryCurrent;
    @FXML
    public Label labelSystemMemoryTotal;
    @FXML
    public TextField textFieldSystemMemoryValue;
    @FXML
    public Slider sliderSystemMemoryChange;
    @FXML
    public TextField textFieldSystemThreadValue;
    @FXML
    public Slider sliderSystemThreadChange;
    @FXML
    public TextField textFieldSystemFarmerPublicKey;
    @FXML
    public TextField textFieldSystemPoolPublicKey;
    @FXML
    public ChoiceBox choiceBoxSystemKey;
    @FXML
    public Button buttonNormalStartPTask;
    @FXML
    public Button buttonDiskCacheAdd;
    @FXML
    public Button buttonDiskCacheDelete;
    @FXML
    public Button buttonDiskTargetAdd;
    @FXML
    public Button buttonDiskTargetDelete;
    @FXML
    public Button buttonDiskCacheToTarget;
    @FXML
    public Button buttonDiskTargetToCache;
    @FXML
    public ListView listViewDiskDirectoryTarget;
    @FXML
    public ListView listViewDiskDirectoryCache;
    @FXML
    public Label labelDiskCacheSelect;
    @FXML
    public Label labelDiskTargetSelect;
    @FXML
    public Label labelDiskWaitQueue;
    @FXML
    public ListView listViewDiskWaitQueue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}