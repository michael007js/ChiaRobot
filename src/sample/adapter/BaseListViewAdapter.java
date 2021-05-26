package sample.adapter;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class BaseListViewAdapter<T> extends ListCell<T> implements Callback<ListView<T>, ListCell<T>> {
    private OnBaseListViewAdapterCallBacK<T> onBaseListViewAdapterCallBacK;
    private Label label=new Label();

    public BaseListViewAdapter(OnBaseListViewAdapterCallBacK<T> onBaseListViewAdapterCallBacK) {
        this.onBaseListViewAdapterCallBacK = onBaseListViewAdapterCallBacK;
    }

    public void setData(ListView<T> listView, ArrayList<T> list) {
        listView.getItems().clear();
        listView.setItems(FXCollections.observableArrayList(list));
        listView.setCellFactory(this);
        listView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<T>() {
                    @Override
                    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                        //item select changed
                    }
                });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    //item double click
                    if (onBaseListViewAdapterCallBacK != null) {
                        onBaseListViewAdapterCallBacK.onItemClick(list.get(listView.getSelectionModel().getSelectedIndex()));
                    }
                }
            }
        });

    }


    @Override
    public ListCell<T> call(ListView<T> param) {
        return new BaseListViewAdapter<T>(onBaseListViewAdapterCallBacK);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            if (onBaseListViewAdapterCallBacK != null) {
                this.setGraphic(onBaseListViewAdapterCallBacK.bindView(item));
            }
        }else {
            this.setGraphic(label);
        }
    }

    public interface OnBaseListViewAdapterCallBacK<T> {

        Node bindView(T item);

        void onItemClick(T item);
    }

}
