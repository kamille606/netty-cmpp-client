package cn.lain.cmpp.client.model;

import lombok.Getter;
import lombok.Setter;

import java.nio.ByteBuffer;

public abstract class CmppHead {

    @Getter
    @Setter
    protected int totalLength;/* 不允许自行设置，doEncode()的时候会自动计算 */
    @Getter
    @Setter
    protected int commandId; /* 不允许自行设置，对象创建时根据构造函数设置 */
    @Getter
    @Setter
    protected int sequenceId;/* 需要自行赋值，请求与响应的相对应，无论上下行 */

    protected byte[] msgBytes;

    protected abstract void processHead();

    /**
     * 子类字节获取，要负责父类中三属性数据生成
     */
    protected abstract void doSubEncode(ByteBuffer bb);

    /**
     * 子类解码，被父类回调
     */
    protected abstract void doSubDecode(ByteBuffer bb);

    /**
     * 对象编码为字节数组
     */
    public void doEncode() {
        processHead();
        ByteBuffer bb = ByteBuffer.allocate(totalLength);
        bb.putInt(totalLength);
        bb.putInt(commandId);
        bb.putInt(sequenceId);
        doSubEncode(bb);
        this.msgBytes = bb.array();
    }

    /**
     * 字节数组解码为对象
     */
    public void doDecode(byte[] bytes) {
        this.msgBytes = bytes;
        doDecode();
    }

    public void doDecode() {
        if (msgBytes == null) {
            throw new RuntimeException("Object Bytes is Null");
        }
        ByteBuffer bb = ByteBuffer.wrap(msgBytes);
        totalLength = bb.getInt();
        commandId = bb.getInt();
        sequenceId = bb.getInt();
        doSubDecode(bb);
    }

    protected byte[] getHead() {
        byte[] head = new byte[12];
        ByteBuffer byteBuffer = ByteBuffer.wrap(head);
        byteBuffer.putInt(totalLength);
        byteBuffer.putInt(commandId);
        byteBuffer.putInt(sequenceId);
        return byteBuffer.array();
    }

    protected void setHead(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        totalLength = byteBuffer.getInt();
        commandId = byteBuffer.getInt();
        sequenceId = byteBuffer.getInt();
    }

    public byte[] getMsgBytes() {
        return msgBytes;
    }

}
