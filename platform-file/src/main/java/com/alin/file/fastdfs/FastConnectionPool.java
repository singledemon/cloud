
package com.alin.file.fastdfs;

/**
 * FastDFS client 连接池实现
 */
/*
@Component
public class FastConnectionPool {


    //最大连接数,可以写配置文件
    //@Value("${fastDFS.maxConnectionSize}")
    private Integer size=5;

    //fastdfs 服务器地址
    //@Value("${fastDFS.hostServerUrl}")
   // @Value("#{boostrap['fastDFS.hostServerUrl']}")
    private String hostServerUrl="192.168.13.18";

    //@Value("${fastDFS.hostServerPort}")
    private Integer hostServerPort=22122;

    //被使用的连接
    private ConcurrentHashMap<StorageClient1,Object> busyConnectionPool = null;
    //空闲的连接
    private ArrayBlockingQueue<StorageClient1> idleConnectionPool = null;

    private Object obj = new Object();

   */
/* private static FastConnectionPool instance = new FastConnectionPool();

    public static FastConnectionPool getFastConnectionPool(){
        return instance;
    }*//*


    //取出连接
    public StorageClient1 checkout(int waitTime){
        StorageClient1 storageClient1 = null;
        try {
            storageClient1 = idleConnectionPool.poll(waitTime, TimeUnit.SECONDS);
            System.out.println(storageClient1);
            if(storageClient1 != null){
                busyConnectionPool.put(storageClient1, obj);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            storageClient1 = null;
            e.printStackTrace();
        }
        return storageClient1;
    }

    //回收连接
    public void checkin(StorageClient1 storageClient1){
        if(busyConnectionPool.remove(storageClient1) != null){
            idleConnectionPool.add(storageClient1);
        }
    }

    //如果连接无效则抛弃，新建连接来补充到池里
    public void drop(StorageClient1 storageClient1){
        if(busyConnectionPool.remove(storageClient1) != null){
            TrackerServer trackerServer = null;
            TrackerClient trackerClient = new TrackerClient();
            try {
                trackerServer = trackerClient.getConnection();
                StorageClient1 newStorageClient1 = new StorageClient1(trackerServer,null);
                idleConnectionPool.add(newStorageClient1);
                System.out.println("------------------------- :connection +1");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally{
                if(trackerServer != null){
                    try {
                        trackerServer.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //单例
    private FastConnectionPool(){
        busyConnectionPool = new ConcurrentHashMap<StorageClient1, Object>();
        idleConnectionPool = new ArrayBlockingQueue<StorageClient1>(size);
        init(size);
    }

    //初始化连接池
    private void init(int size){
        initClientGlobal();
        TrackerServer trackerServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            //只需要一个tracker server连接
            trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient1 storageClient1 = null;
            for(int i=0; i<size; i++){
                storageClient1 = new StorageClient1(trackerServer,storageServer);
                idleConnectionPool.add(storageClient1);
                System.out.println("------------------------- :connection +1");
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if(trackerServer != null){
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    //初始化客户端
    private void initClientGlobal(){
        //连接超时时间
        ClientGlobal.setG_connect_timeout(2000);
        //网络超时时间
        ClientGlobal.setG_network_timeout(3000);
        ClientGlobal.setG_anti_steal_token(false);
        // 字符集
        ClientGlobal.setG_charset("UTF-8");
        ClientGlobal.setG_secret_key(null);
        // HTTP访问服务的端口号
        ClientGlobal.setG_tracker_http_port(8030);

        InetSocketAddress[] trackerServers = new InetSocketAddress[1];
        trackerServers[0] = new InetSocketAddress(hostServerUrl,hostServerPort);
        //trackerServers[1] = new InetSocketAddress("10.64.2.172",22122);
        TrackerGroup trackerGroup = new TrackerGroup(trackerServers);
        //tracker server 集群
        ClientGlobal.setG_tracker_group(trackerGroup);
    }


}


*/

























/*
public class FastConnectionPool {

    // 原conf 格式文件配置
    private static final String FDFS_CONFIG = "";//"properties/fdfs_client.conf";

    // properties 格式文件配置
    private static final String FASTDFS_PROPERTIES = "fastdfs/fastdfs-client.properties";

    //空闲的连接池
    private LinkedBlockingQueue<StorageClient1> idleConnectionPool = null;

    //连接池默认最小连接数
    private long minPoolSize = 10;

    //连接池默认最大连接数
    private long maxPoolSize = 30;

    //默认等待时间（单位：秒）
    private long waitTimes = 200;

    //fastdfs客户端创建连接默认1次
    private static final int COUNT = 1;

    TrackerServer trackerServer = null;

    public FastConnectionPool() {
     //初始化连接池
        poolInit();
    }

    public FastConnectionPool(long minPoolSize, long maxPoolSize, long waitTimes) {
        System.out.println(
            "[线程池构造方法(ConnectionPool)][默认参数：minPoolSize=" + minPoolSize + ",maxPoolSize=" + maxPoolSize + ",waitTimes="
                + waitTimes + "]");
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.waitTimes = waitTimes;
        //初始化连接池
        poolInit();
    }

    private void poolInit() {
        try {
            //加载配置文件
            initClientGlobal();

            //初始化空闲连接池
            idleConnectionPool = new LinkedBlockingQueue<StorageClient1>();

            //初始化忙碌连接池
            // busyConnectionPool = new ConcurrentHashMap<StorageClient1, Object>();
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            int flag = 0;
            while (trackerServer == null && flag < 5) {
                System.out.println("[创建TrackerServer(createTrackerServer)][第" + flag + "次重建]");
                flag++;
                initClientGlobal();
                trackerServer = trackerClient.getConnection();
            }
            // 测试 Tracker活跃情况
            // ProtoCommon.activeTest(trackerServer.getSocket());
            //往线程池中添加默认大小的线程
            createTrackerServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[FASTDFS (poolInit)--Exception]");
        }
    }

    public void createTrackerServer() {

        System.out.println("[创建TrackerServer(createTrackerServer)]");
        TrackerServer trackerServer = null;

        try {

            for (int i = 0; i < minPoolSize; i++) {
                // 把client1添加到连接池
                StorageServer storageServer = null;
                StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);
                idleConnectionPool.add(client1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[创建TrackerServer(createTrackerServer)][异常：{}]");
        }

    }

    public StorageClient1 checkout() {

        StorageClient1 client1 = idleConnectionPool.poll();

        if (client1 == null) {
            if (idleConnectionPool.size() < maxPoolSize) {
                createTrackerServer();
                try {
                    client1 = idleConnectionPool.poll(waitTimes, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("[(checkout)-error][error:timeout:{}]");
                }
            }
        }

        // 添加到忙碌连接池
        // busyConnectionPool.put(client1, obj);
        System.out.println("[(checkout)][Success]");
        return client1;
    }

    public void checkin(StorageClient1 client1) {

        System.out.println("[释放当前连接(checkin)]");

        client1 = null;
        if (idleConnectionPool.size() < minPoolSize) {
            createTrackerServer();
        }

    }

    private void initClientGlobal() throws Exception {
        // 加载原 conf 格式文件配置：
        ClientGlobal.init(FDFS_CONFIG);
        // 加载 properties 格式文件配置：
        ClientGlobal.initByProperties(FASTDFS_PROPERTIES);


        */
/*加载 Properties 对象配置：
        Properties props = new Properties();
        props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, "10.0.11.101:22122,10.0.11.102:22122");
        ClientGlobal.initByProperties(props);

        加载 trackerServers 字符串配置：
        String trackerServers = "10.0.11.101:22122,10.0.11.102:22122";
        ClientGlobal.initByTrackers(trackerServers);*//*


    }

    public LinkedBlockingQueue<StorageClient1> getIdleConnectionPool() {
        return idleConnectionPool;
    }

    public long getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(long minPoolSize) {
        if (minPoolSize != 0) {
            this.minPoolSize = minPoolSize;
        }
    }

    public long getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(long maxPoolSize) {
        if (maxPoolSize != 0) {
            this.maxPoolSize = maxPoolSize;
        }
    }

    public long getWaitTimes() {
        return waitTimes;
    }

    public void setWaitTimes(int waitTimes) {
        if (waitTimes != 0) {
            this.waitTimes = waitTimes;
        }
    }
}
*/
