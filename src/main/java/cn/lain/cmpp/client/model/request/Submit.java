package cn.lain.cmpp.client.model.request;

import cn.lain.cmpp.client.model.CmppHead;
import com.google.common.primitives.UnsignedBytes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.APP_SUBMIT;

@Getter
@Setter
public class Submit extends CmppHead {
    /**
     * msgId:信息标识,由SP侧短信网关本身产生,本处填空
     * pkTotal:相同msgId的信息总条数,从1开始
     * pkNumber:相同msgId的信息序号，从1开始
     * registeredDelivery:是否要求返回状态确认报告-0不需要-1需要-2产生SMC话单
     */
    private byte[] msgId = new byte[8];
    private byte pkTotal;
    private byte pkNumber;
    private byte registeredDelivery;
    /**
     * msgLevel:信息级别
     * serviceId:业务类型,是数字,字母和符号的组合
     * feeUserType:计费用户类型字段-0对目的终端MSISDN计费-1对源终端MSISDN计费-2对SP计费-3表示本字段无效
     * feeTerminalId:被计费用户的号码,本字段与Fee_UserType字段互斥
     */
    private byte msgLevel;
    private byte[] serviceId = new byte[10];
    private byte feeUserType;
    private byte[] feeTerminalId = new byte[21];
    //private byte feeTerminalType;
    /**
     * tppId:GSM协议类型
     * tpUdhi:GSM协议类型
     * msgFmt:信息格式-0ASCII串-3短信写卡操作-4二进制信息-8UCS2编码-15含GB汉字
     */
    private byte tppId;
    private byte tpUdhi;
    private byte msgFmt;
    /**
     * msgSrc:信息内容来源(SP_Id)
     * feeType:资费类别-01对"计费用户号码"免费
     *               -02对"计费用户号码"按条计信息费
     *               -03对"计费用户号码"按包月收取信息费
     *               -04对"计费用户号码"的信息费封顶
     *               -05对"计费用户号码"的收费是由SP实现
     * feeCode:资费代码(以分为单位)
     */
    private byte[] msgSrc = new byte[6];
    private byte[] feeType = new byte[2];
    private byte[] feeCode = new byte[6];
    /**
     * validTime:存活有效期,格式遵循SMPP3.3协议
     * atTime:定时发送时间,格式遵循SMPP3.3协议
     * srcId:源号码,SP的服务代码或前缀为服务代码的长号码,
     *       网关将该号码完整的填到SMPP协议Submit_SM消息相应的source_addr字段,
     *       该号码最终在用户手机上显示为短消息的主叫号码
     * destUsrTl:接收信息的用户数量(小于100个用户)
     */
    private byte[] validTime = new byte[17];
    private byte[] atTime = new byte[17];
    private byte[] srcId = new byte[21];
    @Setter(AccessLevel.NONE)
    private byte destUsrTl;
    /**
     * destTerminalIds:接收短信的MSISDN号码
     * msgLength:信息长度(Msg_Fmt值为0时:<160个字节;其它<=140个字节)
     * msgContent:信息内容
     * reserve:保留
     */
    private byte[] destTerminalIds;
    private int msgLength;
    @Setter(AccessLevel.NONE)
    private byte[] msgContent;
    private byte[] reserve = new byte[8];

    public Submit() {
        commandId = APP_SUBMIT;
    }

    public void setDestUsrTl(byte destUsrTl) {
        this.destUsrTl = destUsrTl;
        destTerminalIds = new byte[destUsrTl * feeTerminalId.length];
    }

    public void setMsgContent(byte[] msgContent) {
        this.msgContent = msgContent;
        msgLength = msgContent.length;
    }

    @Override
    protected void processHead() {
        totalLength = 138 + 21 * destUsrTl + msgLength;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(msgId);
        bb.put(pkTotal);
        bb.put(pkNumber);
        bb.put(registeredDelivery);
        bb.put(msgLevel);
        bb.put(serviceId);
        bb.put(feeUserType);
        bb.put(feeTerminalId);
        bb.put(tppId);
        bb.put(tpUdhi);
        bb.put(msgFmt);
        bb.put(msgSrc);
        bb.put(feeType);
        bb.put(feeCode);
        bb.put(validTime);
        bb.put(atTime);
        bb.put(srcId);
        bb.put(destUsrTl);
        bb.put(destTerminalIds);
        bb.put((byte) msgLength);
        bb.put(msgContent);
        bb.put(reserve);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(msgId);
        pkTotal = bb.get();
        pkNumber = bb.get();
        registeredDelivery = bb.get();
        msgLevel = bb.get();
        bb.get(serviceId);
        feeUserType = bb.get();
        bb.get(feeTerminalId);
        tppId = bb.get();
        tpUdhi = bb.get();
        msgFmt = bb.get();
        bb.get(msgSrc);
        bb.get(feeType);
        bb.get(feeCode);
        bb.get(validTime);
        bb.get(atTime);
        bb.get(srcId);
        setDestUsrTl(bb.get());
        bb.get(destTerminalIds);
        msgLength = UnsignedBytes.toInt(bb.get());
        msgContent = new byte[msgLength];
        bb.get(msgContent);
        bb.get(reserve);
    }

}
