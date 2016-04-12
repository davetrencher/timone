package net.boruvka.idea.tunnellij.net;

import com.intellij.openapi.ui.Messages;
import net.boruvka.idea.tunnellij.TunnelPlugin;
import net.boruvka.idea.tunnellij.ui.CallsPanel;
import net.boruvka.idea.tunnellij.ui.ControlPanel;
import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    public static void stop(int srcPort) throws InterruptedException, ExecutionException {
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
        runningTunnels.forEach((srcPort,tunnel) -> {
            try {
                stop(srcPort);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println(String.format("Unable to shutdown tunnel on port: %S. %s",srcPort,e.getMessage()));
            }
        });
    }

    public static boolean isTunnelRunningOnPort(int port) {

        return runningTunnels.values().stream().filter(tunnel -> tunnel.getSrcPort() == port).findFirst().isPresent();

    }

    public static boolean isTunnelRunning(Tunnel tunnel) {

        Tunnel runningTunnel = runningTunnels.get(tunnel.getSrcPort());
        if (runningTunnel == null) {
            return false;
        }

        return runningTunnel.getDestHost().equals(tunnel.getDestHost())
                    && (runningTunnel.getDestPort() == tunnel.getDestPort());

    }

    private static void addListeners(Tunnel tunnel) {

        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel();
        CallsPanel list = tunnelPanel.getCallsPanelListener();

        tunnel.addTunnelListener(list);

    }

}
