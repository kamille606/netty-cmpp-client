package cn.lain.cmpp.model.response;

import cn.lain.cmpp.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.constant.CmppConst.CMPP_CONNECT_RESP;

@Getter
@Setter
public class ConnectResp extends CmppHead {
    /**
     * status:状态:0正确,1消息结构错误,2非法源地址,3认证错,4版本太高,5~其他错误
     * authenticatorISMG:ISMG认证码,用于鉴别ISMG,其值通过单向MD5 hash计算得出
     * 表示如下 authenticatorISMG = MD5(Status + AuthenticatorSource + shared secret) 认证出错时,此项为空
     * version:服务器支持的最高版本号
     */
    private byte status;
    private byte[] authenticatorISMG;
    private byte version;

    public ConnectResp() {
        commandId = CMPP_CONNECT_RESP;
    }

    @Override
    protected void processHead() {
        totalLength = 30;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(status);
        bb.put(authenticatorISMG);
        bb.put(version);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        status = bb.get();
        bb.get(authenticatorISMG);
        version = bb.get();
    }

}
