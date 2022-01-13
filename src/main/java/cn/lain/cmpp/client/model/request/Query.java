package cn.lain.cmpp.client.model.request;

import cn.lain.cmpp.client.model.CmppHead;
import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

import static cn.lain.cmpp.client.constant.CmppConst.CMPP_QUERY;

@Getter
@Setter
public class Query extends CmppHead {
    /**
     * time:时间YYYYMMDD(精确至日)
     * queryType:查询类别-0总数查询-1按业务类型查询
     * queryCode:查询码,当queryType为0时,此项无效;当queryType为1时,此项填写业务类型Service_Id
     * reserve:保留字段
     */
    private byte[] time = new byte[8];
    private byte queryType;
    private byte[] queryCode = new byte[10];
    private byte[] reserve = new byte[8];

    public Query() {
        commandId = CMPP_QUERY;
    }

    @Override
    protected void processHead() {
        totalLength = 39;
    }

    @Override
    protected void doSubEncode(ByteBuffer bb) {
        bb.put(time);
        bb.put(queryType);
        bb.put(queryCode);
        bb.put(reserve);
    }

    @Override
    protected void doSubDecode(ByteBuffer bb) {
        bb.get(time);
        queryType = bb.get();
        bb.get(queryCode);
        bb.get(reserve);
    }

}
