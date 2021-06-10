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
    public TabPane tabPane;
    @FXML
    public Button buttonNodeSyncByUser;
    @FXML
    public CheckBox checkBoxNodeSyncByAuto;
    @FXML
    public CheckBox checkBoxNodeSyncForChina;
    @FXML
    public TextField textFieldNormalProgramDirectory;
    @FXML
    public Button buttonNormalChangeProgramDirectory;
    @FXML
    public TextField textFieldNormalConfigFileDirectory;
    @FXML
    public TextField textFieldNormalAppVersionDirectory;
    @FXML
    public TextField textFieldNormalRunningTask;
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
    public TextField textFieldSystemPoolWallet;
    @FXML
    public Slider sliderSystemThreadChange;
    @FXML
    public TextField textFieldSystemFarmerPublicKey;
    @FXML
    public TextField textFieldSystemPoolPublicKey;
    @FXML
    public ChoiceBox choiceBoxSystemKey;
    @FXML
    public ChoiceBox choiceBoxSystemPType;
    @FXML
    public Button buttonNormalStartPTask;
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
    public Label labelTaskSelect;
    @FXML
    public Button buttonDiskAddToTaskQueue;
    @FXML
    public TableView tableViewTaskQueue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
