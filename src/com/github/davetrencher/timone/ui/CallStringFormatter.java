package com.github.davetrencher.timone.ui;

import com.github.davetrencher.timone.net.Call;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author boruvka
 */
public class CallStringFormatter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SS");

    public synchronized static String format(Call call) {

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(DATE_FORMAT.format(new Date(call.getStart())));

        if (call.getOutput() != null) {
            byte[] arr = call.getOutput().toByteArray();
            if (arr != null) {
                int len = (arr.length > Call.CMD_LENGTH) ? Call.CMD_LENGTH
                        : arr.length;
                byte[] text = new byte[len];
                System.arraycopy(arr, 0, text, 0, len);
                sb.append("] " + new String(text) + "...");
            }
        }

        sb.append("; from ");
        sb.append(call.getSrcHost());
        sb.append(":");
        sb.append(call.getSrcPort());
        sb.append(" to ");
        sb.append(call.getDestHost());
        sb.append(":");
        sb.append(call.getDestPort());
        sb.append(", output: ");

        sb.append((call.getOutput() == null) ? " ? B" : call.getOutput()
                .toByteArray().length
                + " B");

        sb.append(", input: ");
        sb.append((call.getInput() == null) ? " ? B" : call.getInput()
                .toByteArray().length
                + " B");

        if (call.getEnd() != -1) {
            sb.append(", duration: " + (call.getEnd() - call.getStart())
                    + " ms");
        }

        return sb.toString();
    }
}
