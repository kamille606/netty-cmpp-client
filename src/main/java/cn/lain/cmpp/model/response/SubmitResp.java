package cn.lain.cmpp.model.response;

import cn.lain.cmpp.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.constant.CmppConst.APP_SUBMIT_RESP;

@Getter
@Setter
public class SubmitResp extends CmppHead {
    /**
     * msgId:信息标识，生成算法如下
     *       采用64位(8字节)的整数,各部分如不能填满，左补零，右对齐
     *       (1)时间（格式为月日时分秒）:bit64~bit39,其中
     *              bit64~bit61：月份的二进制表示；
     *              bit60~bit56：日的二进制表示；
     *              bit55~bit51：小时的二进制表示；
     *              bit50~bit45：分的二进制表示；
     *              bit44~bit39：秒的二进制表示；
     *       (2)短信网关代码:bit38~bit17,把短信网关的代码转换为整数填写到该字段中
     *       (3)序列号:bit16~bit1,顺序增加,步长为1,循环使用
     *       (SP根据请求和应答消息的Sequence_Id一致性就可得到CMPP_Submit消息的Msg_Id)
     * result:结果-0正确-1消息结构错误-2命令字错误-3消息序号重读
     *           -4消息长度错-5资费代码错-6超过最大信息长-7业务代码错-8流量控制错-9~其他错误
     */
    private byte[] msgId = new byte[8];
    private byte result;

    public SubmitResp() {
        commandId = APP_SUBMIT_RESP;
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
