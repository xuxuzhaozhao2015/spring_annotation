package top.xuxuzhaozhao.test;

import config.SpringConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.xuxuzhaozhao.domain.Account;
import top.xuxuzhaozhao.service.IAccountService;

import javax.annotation.Resource;
import java.util.List;

/**
 * spring整合junit
 * 1、加入坐标（spring-test）
 * 2、使用junit的注解把原有main方法替换为spring的
 * 3、告知spring运行器，spring的IOC是基于xml还是注解，并且要告知位置
 * 注意：当spring框架在5.x的时候，junit要求在4.12及以上
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        //locations = "classpath:bean.xml"  ->  表示在类路径下
        classes = SpringConfiguration.class
)
public class AccountServiceTest {
    @Resource(name = "accountService")
    IAccountService accountService;
    //ApplicationContext ac;

//    @Before
//    public void init(){
//        ac = new AnnotationConfigApplicationContext(SpringConfiguration.class);
//        accountService =ac.getBean("accountService",IAccountService.class);
//    }

    @Test
    public void TestFindAll() {
        List<Account> accounts = accountService.findAll();

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    @Test
    public void TestTransfer() {
        accountService.transfer(1, 2, (double) 100);
    }

    @Test
    public void TestFindAccountByName() {
        Account account = accountService.finAccountByName(1);
        System.out.println(account);
    }

//    @Test
//    public void TestRunner(){
//        QueryRunner runner1 = ac.getBean("runner",QueryRunner.class);
//        QueryRunner runner2 = ac.getBean("runner",QueryRunner.class);
//
//        System.out.println(runner1);
//        System.out.println(runner2);
//    }
}
