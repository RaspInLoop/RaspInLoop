package org.raspinloop.fmi.plugin.editor.component;

import com.intellij.ui.AddEditRemovePanel;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GPIOHardware;
import org.raspinloop.config.HardwareProperties;

public class HardwareListTableModel extends AddEditRemovePanel.TableModel<HardwareProperties> {

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Nullable
    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0 : return "Name";
            case 1 : return "Usage description";
            case 2 : return "Simulated provider name";
            default: return "";
        }
    }

    @Override
    public Object getField(HardwareProperties o, int columnIndex) {
        switch (columnIndex){
            case 0 : return o.getComponentName();
            case 1 : return buildUsageDescription(o);
            case 2 : return o.getSimulatedProviderName();
            default: return null;
        }
    }

    private String buildUsageDescription(HardwareProperties o) {
        StringBuilder desc = new StringBuilder();
        if (o instanceof GPIOHardware)
            desc.append("Inputs:" + ((GPIOHardware) o).getInputPins().size() + " Outputs:" + ((GPIOHardware) o).getOutputPins().size());
        if (o instanceof BoardHardware)
            desc.append(" Components:" + ((BoardHardware) o).getAllComponents().size());

        if (desc.toString().isEmpty())
            return "no information available";
        else
            return desc.toString();
    }
}
