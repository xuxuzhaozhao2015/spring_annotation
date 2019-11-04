package top.xuxuzhaozhao.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.xuxuzhaozhao.service.IAccountService;
import utils.TransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class BeanProxyFactory {
    @Autowired
    private TransactionManager txManager;

    @Autowired
    private IAccountService accountService;

    public final void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 获取代理对象
     *
     * 添加事务的支持
     * @return
     */
    @Bean(name = "proxyAccountService")
    public IAccountService getAccountService() {
        Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object returnValue = null;
                        try {
                            txManager.beginTransaction();

                            returnValue = method.invoke(accountService, args);

                            txManager.commit();
                            return returnValue;
                        } catch (Exception e) {
                            txManager.rollBack();
                            throw new RuntimeException(e);
                        } finally {
                            txManager.close();
                        }
                    }
                });
        return accountService;
    }
}
