package top.xuxuzhaozhao.service;

import top.xuxuzhaozhao.domain.Account;

import java.util.List;

public interface IAccountService {
    List<Account> findAll();

    Account finAccountByName(Integer uid);

    void transfer(Integer sourceName, Integer targetName, Double money);
}
