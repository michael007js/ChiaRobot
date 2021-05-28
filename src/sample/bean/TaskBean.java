package sample.bean;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sample.utils.TimeUtils;

public class TaskBean {

    /**
     * 是否运行中
     */
    private boolean isRunning;

    /**
     * chia秘钥模型
     */
    private KeyBean keyBean;

    /**
     * 序号
     */
    private SimpleIntegerProperty number = new SimpleIntegerProperty();

    /**
     * 格式化用时
     */
    private SimpleStringProperty timeFormat = new SimpleStringProperty();
    /**
     * 用时
     */
    private SimpleLongProperty time = new SimpleLongProperty();{
        time.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                timeFormat.set(TimeUtils.getFormatHMS(newValue.longValue()));
            }
        });
    }
    /**
     * 缓存路径
     */
    private SimpleStringProperty cache = new SimpleStringProperty();
    /**
     * 目标路径
     */
    private SimpleStringProperty target = new SimpleStringProperty();
    /**
     * 类型
     */
    private SimpleStringProperty type = new SimpleStringProperty();
    /**
     * 进度
     */
    private SimpleIntegerProperty progress = new SimpleIntegerProperty();
    /**
     * 线程数量
     */
    private SimpleIntegerProperty thread = new SimpleIntegerProperty();
    /**
     * 内存
     */
    private SimpleIntegerProperty memory = new SimpleIntegerProperty();
    /**
     * 公共指纹
     */
    private SimpleStringProperty finger = new SimpleStringProperty();

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public String getTimeFormat() {
        return timeFormat.get();
    }

    public SimpleStringProperty timeFormatProperty() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat.set(timeFormat);
    }

    public long getTime() {
        return time.get();
    }

    public SimpleLongProperty timeProperty() {
        return time;
    }

    public void setTime(long time) {
        this.time.set(time);
    }

    public String getCache() {
        return cache.get();
    }

    public SimpleStringProperty cacheProperty() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache.set(cache);
    }

    public String getTarget() {
        return target.get();
    }

    public SimpleStringProperty targetProperty() {
        return target;
    }

    public void setTarget(String target) {
        this.target.set(target);
    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getProgress() {
        return progress.get();
    }

    public SimpleIntegerProperty progressProperty() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress.set(progress);
    }

    public int getThread() {
        return thread.get();
    }

    public SimpleIntegerProperty threadProperty() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread.set(thread);
    }

    public int getMemory() {
        return memory.get();
    }

    public SimpleIntegerProperty memoryProperty() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory.set(memory);
    }

    public String getFinger() {
        return finger.get();
    }

    public SimpleStringProperty fingerProperty() {
        return finger;
    }

    public void setFinger(String finger) {
        this.finger.set(finger);
    }

    public KeyBean getKeyBean() {
        return keyBean;
    }

    public void setKeyBean(KeyBean keyBean) {
        this.keyBean = keyBean;
        setFinger(keyBean.getFingerprint());
    }
}
