package cn.lain.cmpp.model.response;

import cn.lain.cmpp.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.constant.CmppConst.CMPP_CANCEL_RESP;

@Getter
@Setter
public class CancelResp extends CmppHead {

    /**
     * successId:成功标识-0成功-1失败
     */
    private byte successId;

    public CancelResp() {
        commandId = CMPP_CANCEL_RESP;
    }

    @Override
    protected void processHead() {
        totalLength = 13;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(successId);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        successId = bb.get();
    }

}
