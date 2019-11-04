package top.xuxuzhaozhao.dao;

import top.xuxuzhaozhao.domain.Account;

import java.util.List;

public interface IAccountDao {
    List<Account> findAll();

    Account findAccountByName(Integer uid);

    void updateAccount(Account account);
}
