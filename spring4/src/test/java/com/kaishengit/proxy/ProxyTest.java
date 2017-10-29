package com.kaishengit.proxy;

import org.junit.Test;

public class ProxyTest {

    @Test
    public void proxy() {
        Lenvon lenvon = new Lenvon();
        Dell dell = new Dell();

        Proxy proxy = new Proxy(dell);
        proxy.salePc();


       /* LenvonProxy proxy = new LenvonProxy();
        proxy.salePc();*/
    }

}
