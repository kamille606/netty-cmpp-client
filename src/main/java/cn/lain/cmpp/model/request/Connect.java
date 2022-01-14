package cn.lain.cmpp.model.request;

import cn.lain.cmpp.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.constant.CmppConst.CMPP_CONNECT;

@Getter
@Setter
public class Connect extends CmppHead {
    /**
     * sourceAddr:源地址,此处为SP_Id,即SP的企业代码
     * authenticatorSource:用于鉴别源地址,其值通过单向MD5 hash计算得出
     * 表示如下 AuthenticatorSource = MD5(Source_Addr + 9字节的0 + shared secret + timestamp)
     * version:双方协商的版本号(高位4bit表示主版本号,低位4bit表示次版本号)
     * timeStamp:时间戳的明文,由客户端产生,格式为月日时分秒,10位数字的整型,右对齐
     */
    private byte[] sourceAddr = new byte[6];
    private byte[] authenticatorSource = new byte[16];
    private byte version;
    private byte[] timeStamp = new byte[4];

    public Connect() {
        commandId = CMPP_CONNECT;
    }

    @Override
    protected void processHead() {
        totalLength = 39;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(sourceAddr);
        bb.put(authenticatorSource);
        bb.put(version);
        bb.put(timeStamp);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(sourceAddr);
        bb.get(authenticatorSource);
        version = bb.get();
        bb.get(timeStamp);
    }

}
