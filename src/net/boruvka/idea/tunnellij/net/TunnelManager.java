package net.boruvka.idea.tunnellij.net;

import com.intellij.openapi.ui.Messages;

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
}
