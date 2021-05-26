package sample.adapter;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

public class BaseChoiceBoxAdapter<T> {
    public OnBaseChoiceBoxAdapterCallBack onBaseChoiceBoxAdapterCallBack;

    public void setOnBaseChoiceBoxAdapterCallBack(OnBaseChoiceBoxAdapterCallBack onBaseChoiceBoxAdapterCallBack) {
        this.onBaseChoiceBoxAdapterCallBack = onBaseChoiceBoxAdapterCallBack;
    }

    public void setData(ChoiceBox<T> choiceBox, List<T> data) {
        setData(choiceBox, data, -1);
    }

    public void setData(ChoiceBox<T> choiceBox, List<T> data, int selectPosition) {
        choiceBox.setItems(FXCollections.observableArrayList(data));
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (onBaseChoiceBoxAdapterCallBack != null && Math.min(newValue.intValue(), data.size() - 1) >= 0) {
                    onBaseChoiceBoxAdapterCallBack.onItemClick(data.get(Math.min(newValue.intValue(), data.size() - 1)));
                }
            }
        });
        if (selectPosition >= 0 && selectPosition < data.size() && data.size() > 0) {
            choiceBox.valueProperty().setValue(data.get(selectPosition));
            if (onBaseChoiceBoxAdapterCallBack != null) {
                onBaseChoiceBoxAdapterCallBack.onItemClick(data.get(selectPosition));
            }
        }
    }

    public interface OnBaseChoiceBoxAdapterCallBack<T> {
        void onItemClick(T item);
    }
}
