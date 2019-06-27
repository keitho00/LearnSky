package com.learn.sky.proxy;

/**
 * @Author: JiuBuKong
 * @Date: 2019/6/27 2:20 PM
 */
public class TicketServiceImpl implements TicketService{
    @Override
    public void sellTicket() {
        System.out.println("\n\t售票.....\n");
    }

    @Override
    public void inquire() {
        System.out.println("\n\t问询。。。。\n");
    }

    @Override
    public void withdraw() {
        System.out.println("\n\t退票......\n");
    }

}
