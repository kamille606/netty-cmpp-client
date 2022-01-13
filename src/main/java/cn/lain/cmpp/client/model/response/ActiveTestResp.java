package cn.lain.cmpp.client.model.response;

import cn.lain.cmpp.client.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.APP_ACTIVE_TEST_RESP;

@Getter
@Setter
public class ActiveTestResp extends CmppHead {
    /**
     * reserved:处理
     */
    private byte reserved;

    public ActiveTestResp() {
        commandId = APP_ACTIVE_TEST_RESP;
    }

    @Override
    protected void processHead() {
        totalLength = 13;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(reserved);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        reserved = bb.get();
    }

}
