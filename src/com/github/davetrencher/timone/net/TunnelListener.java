package com.github.davetrencher.timone.net;

/**
 * @author boruvka
 */
public interface TunnelListener {

    void newCall(Call call);

    void endCall(Call call);

    void tunnelStarted();

    void tunnelStopped();

}
