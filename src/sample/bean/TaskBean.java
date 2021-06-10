package sample.bean;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import sample.utils.TimeUtils;

public class TaskBean {

    /**
     * 最新一行日志
     */
    private String currentLineLog;
    /**
     * 完整日志
     */
    private String fullLog;

    /**
     * chia秘钥模型
     */
    private KeyBean keyBean;

    private TaskStatus taskStatus;


    /**
     * 格式化是否运行中
     */
    private SimpleStringProperty formatRun = new SimpleStringProperty();

    {
        formatRun.set("队列");
    }


    /**
     * 序号
     */
    private SimpleIntegerProperty number = new SimpleIntegerProperty();

    /**
     * 格式化用时
     */
    private SimpleStringProperty createTimeFormat = new SimpleStringProperty();
    /**
     * 创建时间
     */
    private SimpleLongProperty createTime = new SimpleLongProperty();

    {
        createTime.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                createTimeFormat.set(TimeUtils.millis2String(newValue.longValue()));
            }
        });
        createTime.set(System.currentTimeMillis());
    }

    /**
     * 格式化用时
     */
    private SimpleStringProperty timeFormat = new SimpleStringProperty();

    {
        timeFormat.set("-");
    }

    /**
     * 用时
     */
    private SimpleLongProperty time = new SimpleLongProperty();

    {
        time.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                timeFormat.set(TimeUtils.formatDayHourMinuteSecond(newValue.longValue(),true,""));
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
    private SimpleIntegerProperty type = new SimpleIntegerProperty();
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

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        if (this.taskStatus==TaskStatus.NORMAL){
            formatRun.set("队列");
        }else  if (this.taskStatus==TaskStatus.RUNNING){
            formatRun.set("运行");
        }else  if (this.taskStatus==TaskStatus.COMPLETE){
            formatRun.set("完成");
        }
    }

    public String getFormatRun() {
        return formatRun.get();
    }

    public SimpleStringProperty formatRunProperty() {
        return formatRun;
    }

    public void setFormatRun(String formatRun) {
        this.formatRun.set(formatRun);
    }

    public String getCurrentLineLog() {
        return currentLineLog;
    }

    public void setCurrentLineLog(String currentLineLog) {
        this.currentLineLog = currentLineLog;
    }

    public long getCreateTime() {
        return createTime.get();
    }

    public SimpleLongProperty createTimeProperty() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime.set(createTime);
    }

    public String getCreateTimeFormat() {
        return createTimeFormat.get();
    }

    public SimpleStringProperty createTimeFormatProperty() {
        return createTimeFormat;
    }

    public void setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat.set(createTimeFormat);
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

    public int getType() {
        return type.get();
    }

    public SimpleIntegerProperty typeProperty() {
        return type;
    }

    public void setType(int type) {
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

    public String getFullLog() {
        return fullLog;
    }

    public void setFullLog(String fullLog) {
        this.fullLog = fullLog;
    }

    /**
     * 任务状态枚举
     */
    public enum TaskStatus {
        /**
         * 通常未处理状态，处于队列中
         */
        NORMAL,
        /**
         * 运行状态
         */
        RUNNING,
        /**
         * 完成状态
         */
        COMPLETE
    }
}
