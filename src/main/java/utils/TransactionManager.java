package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 开启事务，提交事务，回滚事务，释放连接
 */
@Component("txManager")
public class TransactionManager {
    @Autowired
    private ConnectionUtils connectionUtils;

    public void beginTransaction(){
        try {
            connectionUtils.GetCurrentThreadConnection().setAutoCommit(false);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void commit(){
        try {
            connectionUtils.GetCurrentThreadConnection().commit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void rollBack(){
        try {
            connectionUtils.GetCurrentThreadConnection().rollback();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void close(){
        try {
            connectionUtils.GetCurrentThreadConnection().close();//还回连接池中，所以下次在获得线程时，每个线程都还绑定着connection

            connectionUtils.RemoveCurrentThreadConnection();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
