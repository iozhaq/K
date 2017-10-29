package com.kaishengit.proxy;

public class Proxy implements Sales {

    //private Lenvon lenvon = new Lenvon();
    private Sales sales;

    public Proxy(Sales sales) {
        this.sales = sales;
    }


    @Override
    public void salePc() {
        try {
            //
            sales.salePc();
            //
        } catch (Exception ex) {
            //sendWixin();
        }
        //。。。
    }
}
