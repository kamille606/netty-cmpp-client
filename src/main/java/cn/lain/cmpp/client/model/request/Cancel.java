package cn.lain.cmpp.client.model.request;

import cn.lain.cmpp.client.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.CMPP_CANCEL;

@Setter
@Getter
public class Cancel extends CmppHead {
    /**
     * msgId:信息标识(SP想要删除的信息标识)
     */
    private byte[] msgId = new byte[8];

    public Cancel() {
        commandId = CMPP_CANCEL;
    }

    @Override
    protected void processHead() {
        totalLength = 20;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(msgId);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(msgId);
    }

}
