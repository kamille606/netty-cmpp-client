package cn.lain.cmpp.client.model.request;

import cn.lain.cmpp.client.model.CmppHead;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.CMPP_TERMINATE;

public class Terminate extends CmppHead {

    public Terminate() {
        commandId = CMPP_TERMINATE;
    }

    @Override
    protected void processHead() {
        totalLength = 12;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {

    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {

    }

}
