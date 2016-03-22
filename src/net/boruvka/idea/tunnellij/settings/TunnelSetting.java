package net.boruvka.idea.tunnellij.settings;

/**
 * Created by dave on 22/03/16.
 */
public class TunnelSetting {

    private int srcPort;

    private String destHost;

    private int destPort;

    public TunnelSetting() {
    }

    public TunnelSetting(int srcPort, String destHost, int destPort) {
        this.srcPort = srcPort;
        this.destHost = destHost;
        this.destPort = destPort;
    }

    public int getSrcPort() {
        return srcPort;
    }

    public void setSrcPort(int srcPort) {
        this.srcPort = srcPort;
    }

    public String getDestHost() {
        return destHost;
    }

    public void setDestHost(String destHost) {
        this.destHost = destHost;
    }

    public int getDestPort() {
        return destPort;
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }
}
