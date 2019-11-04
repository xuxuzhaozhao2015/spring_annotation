package top.xuxuzhaozhao.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.xuxuzhaozhao.dao.IAccountDao;
import top.xuxuzhaozhao.domain.Account;
import utils.ConnectionUtils;

import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {
    @Autowired
    private QueryRunner runner;

    @Autowired
    private ConnectionUtils connectionUtils;

    public List<Account> findAll() {
        try {
            return runner.query(connectionUtils.GetCurrentThreadConnection(),"select * from account;", new BeanListHandler<Account>(Account.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Account findAccountByName(Integer uid) {
        try {
            List<Account> accounts = runner.query(connectionUtils.GetCurrentThreadConnection(),"select * from account where uid = ?;", new BeanListHandler<Account>(Account.class), uid);
            if (accounts == null || accounts.size() == 0) {
                return null;
            }
            if (accounts.size() > 1) {
                throw new RuntimeException("结果集不唯一，数据有异常！");
            }
            return accounts.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try {
            runner.update(connectionUtils.GetCurrentThreadConnection(),"update account set uid=?,money=? where id=?",account.getUid(),account.getMoney(),account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
