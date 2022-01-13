package cn.lain.cmpp.client.constant;

public class CmppConst {
    /**
     * CMPP_CONNECT:请求连接
     * CMPP_TERMINATE:终止连接
     * APP_SUBMIT:提交短信
     * APP_DELIVER:短信下发
     * CMPP_QUERY:发送短信状态查询
     * CMPP_CANCEL:删除短信
     * APP_ACTIVE_TEST:激活测试
     */
    public static final int CMPP_CONNECT = 0x00000001;
    public static final int CMPP_CONNECT_RESP = 0x80000001;
    public static final int CMPP_TERMINATE = 0x00000002;
    public static final int CMPP_TERMINATE_RESP = 0x80000002;
    public static final int APP_SUBMIT = 0x00000004;
    public static final int APP_SUBMIT_RESP = 0x80000004;
    public static final int APP_DELIVER = 0x00000005;
    public static final int APP_DELIVER_RESP = 0x80000005;
    public static final int CMPP_QUERY = 0x80000006;
    public static final int CMPP_QUERY_RESP = 0x80000006;
    public static final int CMPP_CANCEL = 0x80000007;
    public static final int CMPP_CANCEL_RESP = 0x80000007;
    public static final int APP_ACTIVE_TEST = 0x00000008;
    public static final int APP_ACTIVE_TEST_RESP = 0x80000008;
}
