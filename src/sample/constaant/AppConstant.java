package sample.constaant;


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

    public static final long CACHE_HALF_ONE_HOUR = 1000 * 60 * 30;
    public static final long CACHE_ONE_HOUR = CACHE_HALF_ONE_HOUR * 2;

    /**
     * chia程序目录
     */
    public static String CHIA_PROGRAM_DIRECTORY = "";
    /**
     * chia配置文件目录
     */
    public static String CHIA_CONFIG_FILE_DIRECTORY = "";

    /**
     *chia APP目录
     */
    public static String CHIA_APP_DIRECTORY_NAME = "";

    public static final String CHIA_ONLINE_NODE_URL = "http://chia-ip.onepool.com/";
    /**
     * 全网算力
     */
    public static final String CHIA_CALCULATION_POWER_URL = "https://api2.chiaexplorer.com/chart/netSpace?period=2w";
    /**
     * 实时价格
     */
    public static final String CHIA_PRICE_URL = "https://api2.chiaexplorer.com/chart/xchPriceChart";
    /**
     * 节点同步地址
     * https://www.chiaxch.net/chiahqnodes.php?block_height=true&geoip=true
     */
    public static final String CHIA_NODE_URL = "https://wakuang.tv/chia";

}
