package cn.lain.cmpp.utils;

import cn.lain.cmpp.model.CmppHead;
import cn.lain.cmpp.model.request.Connect;

/**
 * cmpp消息创建工具类
 */
public class CmppUtil {

    public static CmppHead initConnect() {
        Connect connect = new Connect();
        //connect.setSequenceId(null);
        return connect;
    }

}
