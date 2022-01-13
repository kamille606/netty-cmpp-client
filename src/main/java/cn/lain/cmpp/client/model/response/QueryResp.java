package cn.lain.cmpp.client.model.response;

import cn.lain.cmpp.client.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.CMPP_QUERY_RESP;

@Getter
@Setter
public class QueryResp extends CmppHead {
    /**
     * time:时间YYYYMMDD(精确至日)
     * queryType:查询类别-0总数查询-1按业务类型查询
     * queryCode:查询码
     * mtTLMsg:从SP接收信息总数
     * mtTlusr:从SP接收用户总数
     * mtScs:成功转发数量
     * mtWt:待转发数量
     * mtFl:转发失败数量
     * moScs:向SP成功送达数量
     * moWt:向SP待送达数量
     * moFl:向SP送达失败数量
     */
    private byte[] time = new byte[8];
    private byte queryType;
    private byte[] queryCode = new byte[10];
    private byte[] mtTLMsg = new byte[4];
    private byte[] mtTlusr = new byte[4];
    private byte[] mtScs = new byte[4];
    private byte[] mtWt = new byte[4];
    private byte[] mtFl = new byte[4];
    private byte[] moScs = new byte[4];
    private byte[] moWt = new byte[4];
    private byte[] moFl = new byte[4];

    public QueryResp() {
        commandId = CMPP_QUERY_RESP;
    }

    @Override
    protected void processHead() {
        totalLength = 63;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(time);
        bb.put(queryType);
        bb.put(queryCode);
        bb.put(mtTLMsg);
        bb.put(mtTlusr);
        bb.put(mtScs);
        bb.put(mtWt);
        bb.put(mtFl);
        bb.put(moScs);
        bb.put(moWt);
        bb.put(moFl);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(time);
        queryType = bb.get();
        bb.get(queryCode);
        bb.get(mtTLMsg);
        bb.get(mtTlusr);
        bb.get(mtScs);
        bb.get(mtWt);
        bb.get(mtFl);
        bb.get(moScs);
        bb.get(moWt);
        bb.get(moFl);
    }

}
