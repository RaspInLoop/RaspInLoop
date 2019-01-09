package org.raspinloop.fmi.plugin.editor.hardware;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.AddEditRemovePanel;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.plugin.editor.component.HardwareListTableModel;
import org.raspinloop.fmi.plugin.utils.HardwareAccessor;
import org.raspinloop.fmi.plugin.utils.PreferenceHardwareAcesseor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HarwareListDialog extends DialogWrapper {
    private final AddEditRemovePanel<HardwareProperties> hardwareTable;

    private List<HardwareProperties> hardwares = new ArrayList<>();
    private HardwareAccessor hardwareAccessor = new PreferenceHardwareAcesseor();

    public HarwareListDialog(Project project) {
        super(project, true, IdeModalityType.PROJECT);
        setTitle("RaspInLoop Hardware List");
        hardwares.addAll( hardwareAccessor.getList());
        hardwareTable = new AddEditRemovePanel<HardwareProperties>(new HardwareListTableModel(), hardwares, "Add/Remove or modify simulated hardware platform"){

            @Nullable
            @Override
            protected HardwareProperties addItem() {
                // add Board
                HardwareSelectionDialog cs = new BoardSelectionDialog(project);
                cs.show();
                HardwareProperties selectedHardware = cs.getSelected();
                HardwareEditorDialog editor = HardwareEditorDialogFactory.INSTANCE().create(project, selectedHardware.getClass());
                editor.init(selectedHardware);
                editor.show();
                if (editor.isOK()) {
                    selectedHardware = editor.getEdited();
                    return hardwareAccessor.add(selectedHardware);
                } else {
                    return null;
                }
            }

            @Override
            protected boolean removeItem(HardwareProperties o) {
                return hardwareAccessor.delete(o);
            }

            @Nullable
            @Override
            protected HardwareProperties editItem(HardwareProperties o) {
                HardwareEditorDialog editor = HardwareEditorDialogFactory.INSTANCE().create(project, o.getClass());
                editor.init(o);
                editor.show();
                if (editor.isOK()) {
                    HardwareProperties selectedHardware = editor.getEdited();
                    return hardwareAccessor.update(selectedHardware);
                } else {
                    return null;
                }
            }
        };
        hardwareTable.getTable().setShowColumns(true);
        hardwareTable.setPreferredSize(new Dimension(550,300)); // TODO: must be dynamic
        init();
    }

    public Collection<HardwareProperties> getHardwares(){
        return hardwares;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return hardwareTable;
    }


}
