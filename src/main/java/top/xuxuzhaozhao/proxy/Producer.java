package top.xuxuzhaozhao.proxy;

public class Producer implements IProducer {

    public void saleProduct(double money) {
        System.out.println("销售，拿到钱:" + money);
    }

    public void afterService(double money) {
        System.out.println("售后，拿到钱" + money);
    }
}
