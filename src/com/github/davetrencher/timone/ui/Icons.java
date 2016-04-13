package com.github.davetrencher.timone.ui;

import javax.swing.ImageIcon;

/**
 * @author boruvka
 */
public class Icons {

    // my icons
    public static final ImageIcon ICON_WATCH = getIcon("icons/timone.png");

    public static final ImageIcon ICON_REMOVE = getIcon("icons/remove.png");

    public static final ImageIcon ICON_CLEAR = getIcon("icons/removeall.png");

    public static final ImageIcon ICON_WRAP = getIcon("icons/wrap.png");

    // Intellij icons
    // intellij-community/platform/icons/src/actions
    public static final ImageIcon ICON_SETTINGS = getIcon("general/gearPlain.png");

    public static final ImageIcon ICON_HELP = getIcon("actions/help.png");

    private static ImageIcon getIcon(String file) {
        try {
            java.net.URL url = Icons.class.getResource("/" + file);
            return new ImageIcon(url);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot find icon " + file);
            return null;
        }
    }

}
