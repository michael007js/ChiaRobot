package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Controller implements Initializable {
    @FXML
    private GridPane root_parent;
    @FXML
    private Button btn_node_sync_by_user;
    @FXML
    private CheckBox cb_node_sync_by_auto;
    @FXML
    private TextField tf_normal_program_directory;
    @FXML
    private Button btn_normal_change_program_directory;
    @FXML
    private TextField tf_normal_config_file_directory;
    @FXML
    private TextField tf_normal_app_version_directory;
    @FXML
    private Button btn_normal_change_config_file_directory;
    @FXML
    private Label lb_normal_current_calculation_power;
    @FXML
    private Label lb_normal_current_price;
    @FXML
    private TextArea ta_node_log;
    @FXML
    private Label lb_system_memory_current;
    @FXML
    private Label lb_system_memory_total;
    @FXML
    private TextField tf_system_memory_value;
    @FXML
    private Slider slider_system_memory_change;
    @FXML
    private TextField tf_system_thread_value;
    @FXML
    private Slider slider_system_thread_change;
    @FXML
    private TextField tf_system_farmer_public_key;
    @FXML
    private TextField tf_system_pool_public_key;
    @FXML
    private ChoiceBox cb_system_key;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public GridPane getRoot_parent() {
        return root_parent;
    }

    public Button getBtn_node_sync_by_user() {
        return btn_node_sync_by_user;
    }

    public CheckBox getCb_node_sync_by_auto() {
        return cb_node_sync_by_auto;
    }

    public TextField getTf_normal_program_directory() {
        return tf_normal_program_directory;
    }

    public Button getBtn_normal_change_program_directory() {
        return btn_normal_change_program_directory;
    }

    public Label getLb_normal_current_calculation_power() {
        return lb_normal_current_calculation_power;
    }

    public Label getLb_normal_current_price() {
        return lb_normal_current_price;
    }

    public TextArea getTa_node_log() {
        return ta_node_log;
    }

    public TextField getTf_normal_config_file_directory() {
        return tf_normal_config_file_directory;
    }

    public Button getBtn_normal_change_config_file_directory() {
        return btn_normal_change_config_file_directory;
    }

    public TextField getTf_normal_app_version_directory() {
        return tf_normal_app_version_directory;
    }

    public Label getLb_system_memory_current() {
        return lb_system_memory_current;
    }

    public Label getLb_system_memory_total() {
        return lb_system_memory_total;
    }

    public TextField getTf_system_memory_value() {
        return tf_system_memory_value;
    }

    public Slider getSlider_system_memory_change() {
        return slider_system_memory_change;
    }

    public TextField getTf_system_thread_value() {
        return tf_system_thread_value;
    }

    public Slider getSlider_system_thread_change() {
        return slider_system_thread_change;
    }

    public TextField getTf_system_farmer_public_key() {
        return tf_system_farmer_public_key;
    }

    public TextField getTf_system_pool_public_key() {
        return tf_system_pool_public_key;
    }

    public ChoiceBox getCb_system_key() {
        return cb_system_key;
    }
}
