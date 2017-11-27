package org.raspinloop.hwemulation;

public enum SpiChannel {

    CS0(0), CS1(1);

    private final short channel;

    private SpiChannel(int channel) {
        this.channel = (short) channel;
    }

    public short getChannel() {
        return channel;
    }
    
}
