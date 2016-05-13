package com.github.davetrencher.timone.net;

import com.github.davetrencher.timone.TunnelPlugin;
import com.github.davetrencher.timone.ui.CallsPanel;
import com.github.davetrencher.timone.ui.TunnelPanel;
import com.intellij.openapi.ui.Messages;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dave on 21/03/16.
 */
public class TunnelManager {

    private static ExecutorService executorService = Executors.newFixedThreadPool(20);

    private static Map<Integer,Tunnel> runningTunnels = new HashMap<>();

    public static void start(Tunnel tunnel) {

        executorService.submit(() -> {

            try {

                addListeners(tunnel);
                tunnel.start();


            } catch (TunnelException e) {

                Messages.showMessageDialog("Error when starting server: "
                        + e.getMessage(), "Error", Messages.getErrorIcon());
                e.printStackTrace();

            }

            return tunnel;
        });

        runningTunnels.put(tunnel.getSrcPort(),tunnel);

    }

    public static void stop(int srcPort) {
        executorService.submit(() -> {
            Tunnel tunnel = runningTunnels.get(srcPort);
            if (tunnel != null) {
                tunnel.stop();
                runningTunnels.remove(srcPort);
            } else {
                System.out.println("Attempt to non existent tunnel running on port: " +srcPort);
            }
        });

    }

    public static void shutdownAll() {
        runningTunnels.forEach((srcPort,tunnel) -> stop(srcPort));
    }

    public static boolean isTunnelRunningOnPort(int port) {

        return runningTunnels.values().stream().filter(tunnel -> tunnel.getSrcPort() == port).findFirst().isPresent();

    }

    public static boolean isTunnelRunning(Tunnel tunnel) {
        if (tunnel == null) {
            return false;
        }

        Tunnel runningTunnel = runningTunnels.get(tunnel.getSrcPort());
        return tunnel.matches(runningTunnel);

    }

    private static void addListeners(Tunnel tunnel) {

//        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
//        CallsPanel list = tunnelPanel.getCallsPanelListener();
//
//        tunnel.addTunnelListener(list);

    }

}
