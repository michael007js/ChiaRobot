package sample.constant;


@SuppressWarnings("ALL")
public class AppConstant {

    /**
     * 打印调试日志
     */
    public static boolean IS_DEBUG = false;
    /**
     * 打印网络日志
     */
    public static boolean IS_PRINT_NETWORK_LOG = false;
    /**
     * 应用名
     */
    public static String APP_NAME = "Chia Robot";
    /**
     * 可视版本号
     */
    public static String VERSION_NAME = "1.0.0";

    /**
     * 功能启用
     */
    public static boolean functionEnable;

    public static final long CACHE_HALF_ONE_HOUR = 1000 * 60 * 30;
    public static final long CACHE_ONE_HOUR = CACHE_HALF_ONE_HOUR * 2;
    /******************************************************运行配置开始******************************************************/
    /**
     * 每个任务所分配的内存
     */
    public static int EACH_TASK_MEMORY = 4096;
    /**
     * 每个任务所分配的线程
     */
    public static int EACH_TASK_THREAD = 2;
    /******************************************************运行配置结束******************************************************/
    /**
     * chia程序目录
     */
    public static String CHIA_PROGRAM_DIRECTORY = "";
    /**
     * chia配置文件目录
     */
    public static String CHIA_CONFIG_FILE_DIRECTORY = "";

    /**
     * chia APP版本目录名
     */
    public static String CHIA_APP_VERSION_DIRECTORY_NAME = "";

    public static final String CHIA_ONLINE_NODE_URL = "http://chia-ip.onepool.com/";
    /**
     * 全网算力地址
     */
    public static final String CHIA_CALCULATION_POWER_URL = "https://api2.chiaexplorer.com/chart/netSpace?period=2w";
    /**
     * 实时价格地址
     */
    public static final String CHIA_PRICE_URL = "https://api2.chiaexplorer.com/chart/xchPriceChart";
    /**
     * 节点同步地址
     */
    public static final String CHIA_NODE_URL = "https://www.chiaxch.net/chiahqnodes.php?block_height=true&geoip=true";
}
