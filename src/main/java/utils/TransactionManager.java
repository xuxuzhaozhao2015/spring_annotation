package utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 开启事务，提交事务，回滚事务，释放连接
 * <p>
 * 注解的aspect顺序：before -> after -> afterreturning/afterthrowing
 * 所以，用注解的话要使用环绕通知：before -> afterreturning/afterthrowing -> after
 */
@Component("txManager")
@Aspect
public class TransactionManager {
    @Autowired
    private ConnectionUtils connectionUtils;

    @Pointcut("execution(* top.xuxuzhaozhao.service.impl.*.*(..))")
    private void pt1() {
    }

    //    @Before("pt1()")
    public void beginTransaction() {
        try {
            System.out.println("Before");
            connectionUtils.GetCurrentThreadConnection().setAutoCommit(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    @AfterReturning("pt1()")
    public void commit() {
        try {
            System.out.println("AfterReturning");
            connectionUtils.GetCurrentThreadConnection().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    @AfterThrowing("pt1()")
    public void rollBack() {
        try {
            System.out.println("AfterThrowing");
            connectionUtils.GetCurrentThreadConnection().rollback();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    @After("pt1()")
    public void close() {
        try {
            System.out.println("After");
            connectionUtils.GetCurrentThreadConnection().close();//还回连接池中，所以下次在获得线程时，每个线程都还绑定着connection

            connectionUtils.RemoveCurrentThreadConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Around("pt1()")
    public Object aroundAdvice(ProceedingJoinPoint pdjp) {
        Object returnValue = null;
        try {
            Object[] args = pdjp.getArgs();
            this.beginTransaction();

            returnValue = pdjp.proceed(args);

            this.commit();
            return returnValue;
        } catch (Throwable e) {
            this.rollBack();
            throw new RuntimeException(e);
        } finally {
            this.close();
        }
    }
}
