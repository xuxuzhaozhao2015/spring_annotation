package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    /**
     * 用于创建QueryRunner对象
     *
     * @Bean:用于把当前方法的返回值存入spring的容器中
     * name:"用于指定bean的Id"，默认值为当前方法名
     * 细节：当使用注解配置方法的时候，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象
     * 查找方式和AutoWried一样
     *
     * @Qualifier：指定哪一个bean
     */
//    @Bean(name = "runner")
//    @Scope("prototype")
//    public QueryRunner createQueryRunner(@Qualifier("dataSource2") DataSource dataSource) {
//        return new QueryRunner(dataSource);
//    }

    @Bean(name = "runner")
    @Scope("prototype")
    public QueryRunner createQueryRunner() {
        return new QueryRunner();
    }

    /**
     * 创建数据库对象
     *
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource createDataSource() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(driver);
            ds.setJdbcUrl(url);
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "dataSource2")
    public DataSource createDataSource2() {
        try {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            ds.setDriverClass(driver);
            ds.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis_test?useSSL=false");
            ds.setUser(username);
            ds.setPassword(password);
            return ds;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
