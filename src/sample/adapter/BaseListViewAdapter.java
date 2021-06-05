package sample.adapter;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class BaseListViewAdapter<T> extends ListCell<T> implements Callback<ListView<T>, ListCell<T>> {

    /**
     * 数据集合
     */
    private ArrayList<T> data = new ArrayList<>();
    /**
     * 回调
     */
    private OnBaseListViewAdapterCallBacK<T> onBaseListViewAdapterCallBacK;
    /**
     * 显示标签
     */
    private Label label = new Label();
    /**
     * 鼠标当前位置
     */
    public Double screenX = 0.0, screenY = 0.0;

    public BaseListViewAdapter(OnBaseListViewAdapterCallBacK<T> onBaseListViewAdapterCallBacK) {
        this.onBaseListViewAdapterCallBacK = onBaseListViewAdapterCallBacK;
    }

    /**
     * 设置数据
     *
     * @param listView 列表
     */
    public void setData(ListView<T> listView) {
        setData(listView, data);
    }

    /**
     * 设置数据
     *
     * @param listView 列表
     * @param list     数据
     */
    public void setData(ListView<T> listView, ArrayList<T> list) {
        setData(listView, list, null);
    }

    /**
     * 设置数据
     *
     * @param listView  列表
     * @param list      数据
     * @param menuItems 右键菜单
     */
    public void setData(ListView<T> listView, ArrayList<T> list, ArrayList<MenuItem> menuItems) {
        this.data = list;
        listView.getItems().clear();
        listView.setItems(FXCollections.observableArrayList(data));
        listView.setCellFactory(this);
        listView.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<T>() {
                    @Override
                    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
                        //item select changed
                    }
                });
        listView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                screenX = event.getScreenX();
                screenY = event.getScreenY();
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    //item double click
                    if (onBaseListViewAdapterCallBacK != null) {
                        onBaseListViewAdapterCallBacK.onItemClick(data, data.get(listView.getSelectionModel().getSelectedIndex()), listView.getSelectionModel().getSelectedIndex());
                    }
                }
                if (menuItems != null && menuItems.size() > 0 && event.getButton() == MouseButton.SECONDARY) {
                    for (MenuItem menuItem : menuItems) {
                        menuItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if (onBaseListViewAdapterCallBacK != null) {
                                    onBaseListViewAdapterCallBacK.onMenuItemClick(menuItem);
                                }
                            }
                        });
                    }
                    ContextMenu contextMenu = new ContextMenu();
                    contextMenu.getItems().addAll(menuItems);
                    contextMenu.show(listView, screenX, screenY);
                }

            }
        });
    }

    /**
     * 获取数据集合
     */
    public ArrayList<T> getData() {
        return data == null ? new ArrayList<>() : data;
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
        } else {
            this.setGraphic(label);
        }
    }

    /**
     * listview适配器回调
     */
    public interface OnBaseListViewAdapterCallBacK<T> {

        /**
         * 绑定数据
         */
        Node bindView(T item);

        /**
         * cell被单击
         */
        void onItemClick(ArrayList<T> list, T item, int selectedIndex);

        /**
         * 菜单被单击
         */
        void onMenuItemClick(MenuItem menuItem);
    }

}
