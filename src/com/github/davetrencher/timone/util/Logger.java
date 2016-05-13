package com.github.davetrencher.timone.util;

import com.github.davetrencher.timone.TunnelPlugin;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.ui.awt.RelativePoint;

/**
 * Created by dave on 20/04/16.
 */
public class Logger {

    private static final StatusBar statusBar = TunnelPlugin.getStatusBar();

    private static void log(String message, MessageType messageType) {

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                        Balloon.Position.atRight);
    }


    public static void info(String message) {
        Logger.log(message, MessageType.INFO);
    }
}
