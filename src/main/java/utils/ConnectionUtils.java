package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * 连接的工具类，用于从数据源中获取一个连接，并且实现和线程的绑定
 */
@Component
public class ConnectionUtils {
    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    @Resource(name = "dataSource")
    private DataSource dataSource;

    /**
     * 获取当前线程上的连接
     *
     * @return
     */
    public Connection GetCurrentThreadConnection() {
        try {
            Connection conn = tl.get();
            if (conn == null) {
                conn = dataSource.getConnection();
                tl.set(conn);
            }

            return conn;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * conn在close的时候其实是还回连接池中，所以下次在获得线程时，每个线程都还绑定着connection
     *
     * 把连接和线程解绑
     */
    public void RemoveCurrentThreadConnection(){
        tl.remove();
    }
}
