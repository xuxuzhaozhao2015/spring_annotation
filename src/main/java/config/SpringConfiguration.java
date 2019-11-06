package config;

import org.springframework.context.annotation.*;
import top.xuxuzhaozhao.factory.BeanProxyFactory;
import utils.ConnectionUtils;
import utils.TransactionManager;

/**
 * @Configuration:指定当前类是一个配置类 当配置类作为AnnotationConfigApplicationContext参数时，该注解可以不写,
 * 要么作为字节码传入参数，要么就写Config，并在主配置类中加入扫描的包
 * @ComponentScan:指定spring在创建容器时要扫面的包 等同于：<context:component-scan base-package="top.xuxuzhaozhao"/>
 * <p>
 * 效果如下：
 * <context:component-scan base-package="top.xuxuzhaozhao"/>
 *
 * <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
 * <constructor-arg name="ds" ref="dataSource"/>
 * </bean>
 * <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
 * <!-- mysql连接信息 -->
 * <property name="driverClass" value="com.mysql.jdbc.Driver"/>
 * <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/mybatis_test?useSSL=false"/>
 * <property name="user" value="root"/>
 * <property name="password" value="zywoaini1314"/>
 * </bean>
 * @Import：导入其他的配置类,使用Import注解后，有Import的配置类就是父配置类
 * @PropertySource：用于指定properties文件的位置，关键字classpath
 */
//@Configuration
//@ComponentScan(basePackages = {"top.xuxuzhaozhao","config"})
@ComponentScan(basePackages = {"top.xuxuzhaozhao", "utils"})
@Import({JdbcConfig.class})
@PropertySource("classpath:jdbcConfig.properties")
@EnableAspectJAutoProxy // 开启spring对TransactionManager注解的支持
public class SpringConfiguration {

//    /**
//     * 用于创建QueryRunner对象
//     *
//     * @Bean:用于把当前方法的返回值存入spring的容器中
//     * name:"用于指定bean的Id"，默认值为当前方法名
//     * 细节：当使用注解配置方法的时候，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象
//     * 查找方式和AutoWried一样
//     */
//    @Bean(name = "runner")
//    @Scope("prototype")
//    public QueryRunner createQueryRunner(DataSource dataSource) {
//        return new QueryRunner(dataSource);
//    }
//
//    /**
//     * 创建数据库对象
//     *
//     * @return
//     */
//    @Bean(name = "dataSource")
//    public DataSource createDataSource() {
//        try {
//            ComboPooledDataSource ds = new ComboPooledDataSource();
//            ds.setDriverClass("com.mysql.jdbc.Driver");
//            ds.setJdbcUrl("jdbc:mysql://localhost:3306/mybatis_test?useSSL=false");
//            ds.setUser("root");
//            ds.setPassword("zywoaini1314");
//            return ds;
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
}
