package cn.lain.cmpp;

import cn.lain.cmpp.client.NettyClient;
import cn.lain.cmpp.model.request.Submit;

public class Main {

    public static void main(String[] args) {
        NettyClient client = new NettyClient("localhost", 8080, "username", "pwd");
        client.start();

        Submit submit = new Submit();
        client.submit(submit);
    }

}
