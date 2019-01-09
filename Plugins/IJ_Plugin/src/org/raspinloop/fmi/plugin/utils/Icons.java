package org.raspinloop.fmi.plugin.utils;

import javax.swing.*;
import java.net.URL;

public class Icons {

    private static final Icons INSTANCE = new Icons();
    private Icon icon;

    public static Icons getInstance() {
        return INSTANCE;
    }

    public Icon getIcon() {
        if (icon == null)
            icon = loadIcon();

        return icon;
    }

    private static Icon loadIcon() {
        URL resource = INSTANCE.getClass().getResource("/rilfmi-16.png");
        return new ImageIcon(resource);
    }
}
