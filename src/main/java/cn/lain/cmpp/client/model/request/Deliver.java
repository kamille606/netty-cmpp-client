package cn.lain.cmpp.client.model.request;

import cn.lain.cmpp.client.model.CmppHead;
import com.google.common.primitives.UnsignedBytes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.APP_DELIVER;

@Getter
@Setter
public class Deliver extends CmppHead {

    private byte[] msgId = new byte[8];
    private byte[] destId = new byte[21];
    private byte[] serviceId = new byte[10];
    private byte tpPid;
    private byte tpUdhi;
    private byte msgFmt;
    private byte[] srcTerminalId = new byte[21];
    @Setter(AccessLevel.NONE)
    private byte registeredDelivery;
    private int msgLength;//由于符号位关系，这里用int替代byte
    private byte[] msgContent;

    private byte[] msg_Id = new byte[8];//状态报告中msgid与响应中对应
    private byte[] stat = new byte[7];
    private byte[] submitTime = new byte[10];
    private byte[] doneTime = new byte[10];
    private byte[] destTerminalId;
    private byte[] smscSequence = new byte[4];

    private byte[] reserved = new byte[8];

    public Deliver() {
        commandId = APP_DELIVER;
    }

    public void setRegisteredDelivery(byte registeredDelivery) {
        this.registeredDelivery = registeredDelivery;
        if (1 == registeredDelivery) {
            destTerminalId = new byte[21];
        }
    }

    @Override
    protected void processHead() {
        if (null == msgContent) {
            totalLength = 145;
        } else {
            msgLength = registeredDelivery == 1 ? 60 : msgContent.length;
            totalLength = 85 + msgLength;
        }
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(msgId);
        bb.put(destId);
        bb.put(serviceId);
        bb.put(tpPid);
        bb.put(tpUdhi);
        bb.put(msgFmt);
        bb.put(srcTerminalId);
        bb.put(registeredDelivery);
        bb.put((byte) msgLength);
        if (msgContent != null) {
            bb.put(msgContent);
        } else {
            bb.put(msg_Id);
            bb.put(stat);
            bb.put(submitTime);
            bb.put(doneTime);
            bb.put(destTerminalId);
            bb.put(smscSequence);
        }
        bb.put(reserved);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(msgId);
        bb.get(destId);
        bb.get(serviceId);
        tpPid = bb.get();
        tpUdhi = bb.get();
        msgFmt = bb.get();
        bb.get(srcTerminalId);
        setRegisteredDelivery(bb.get());
        msgLength = UnsignedBytes.toInt(bb.get());
        if (registeredDelivery == 1) {
            bb.get(msg_Id);
            bb.get(stat);
            bb.get(submitTime);
            bb.get(doneTime);
            bb.get(destTerminalId);
            bb.get(smscSequence);
        } else {
            msgContent = new byte[msgLength];
            bb.get(msgContent);
        }
        bb.get(reserved);
    }

}
