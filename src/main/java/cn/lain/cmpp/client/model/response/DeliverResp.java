package cn.lain.cmpp.client.model.response;

import cn.lain.cmpp.client.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.APP_DELIVER_RESP;

@Getter
@Setter
public class DeliverResp extends CmppHead {

    private byte[] msgId = new byte[8];
    private byte result;

    public DeliverResp() {
        commandId = APP_DELIVER_RESP;
    }

    @Override
    protected void processHead() {
        totalLength = 21;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(msgId);
        bb.put(result);

    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(msgId);
        result = bb.get();
    }

}
