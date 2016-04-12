package com.github.davetrencher.timone;

import java.util.PropertyResourceBundle;

/**
 * @author boruvka
 */
public class TunnelBundle {

    private static java.util.ResourceBundle bundle;

    public static java.util.ResourceBundle getBundle() {
        if (bundle == null)
            bundle = PropertyResourceBundle
                    .getBundle("com/github/davetrencher/timone/TunnelPlugin");
        return bundle;
    }
}
