package top.xuxuzhaozhao.service.impl;

import org.springframework.stereotype.Service;
import top.xuxuzhaozhao.dao.IAccountDao;
import top.xuxuzhaozhao.domain.Account;
import top.xuxuzhaozhao.service.IAccountService;

import javax.annotation.Resource;
import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    @Resource(name = "accountDao")
    private IAccountDao accountDao;

    public List<Account> findAll() {
        List<Account> accounts = accountDao.findAll();
        return accounts;
    }

    public Account finAccountByName(Integer uid) {
        return accountDao.findAccountByName(uid);
    }

    /**
     * 应该只有一个Connection，需要使用ThreadLocal对象把Connection和当前线程绑定，使一个线程只能有一个控制事务的对象
     * 事务应该在业务层而非持久层
     *
     * @param sourceName
     * @param targetName
     * @param money
     */
    public void transfer(Integer sourceName, Integer targetName, Double money) {
        //1、根据名称查询转出账户；
        Account source = accountDao.findAccountByName(sourceName);
        //2、根据名称查询转入账户；
        Account target = accountDao.findAccountByName(targetName);
        //3、转出账户减钱；
        source.setMoney(source.getMoney() - money);
        //4、转入账户加钱；
        target.setMoney(target.getMoney() + money);
        //5、更新转出账户；
        accountDao.updateAccount(source);

//        int x = 12 / 0;
        //6、更新转入账户；
        accountDao.updateAccount(target);
    }
}
