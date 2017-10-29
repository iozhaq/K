package com.kaishengit.proxy;

public class Dell implements Sales {
    @Override
    public void salePc() {
        System.out.println("Dell 销售了一台电脑");
    }
}
