package org.raspinloop.fmi.plugin.editor.selection;

import com.intellij.ui.ColoredListCellRenderer;

public class HardwareConfigurationItem {
    private String name;

    public HardwareConfigurationItem(String text){

    }

    public String getName() {
        return name;
    }

    public void render(ColoredListCellRenderer<HardwareConfigurationItem> coloredListCellRenderer, boolean selected) {
    }

    String getPresentableText(){
        return "";
    }

}
