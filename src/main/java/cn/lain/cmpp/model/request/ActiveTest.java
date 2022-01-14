package cn.lain.cmpp.model.request;

import cn.lain.cmpp.model.CmppHead;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.constant.CmppConst.APP_ACTIVE_TEST;

public class ActiveTest extends CmppHead {

    public ActiveTest() {
        commandId = APP_ACTIVE_TEST;
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
