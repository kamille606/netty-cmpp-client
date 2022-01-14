package cn.lain.cmpp.model.response;

import cn.lain.cmpp.model.CmppHead;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.constant.CmppConst.CMPP_TERMINATE_RESP;

public class TerminateResp extends CmppHead {

    public TerminateResp() {
        commandId = CMPP_TERMINATE_RESP;
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
