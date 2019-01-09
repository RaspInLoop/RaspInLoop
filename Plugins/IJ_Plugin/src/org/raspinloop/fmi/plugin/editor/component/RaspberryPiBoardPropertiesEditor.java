package org.raspinloop.fmi.plugin.editor.component;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.ui.AddEditRemovePanel;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.config.*;

import org.raspinloop.fmi.plugin.editor.hardware.HardwareEditorDialog;
import org.raspinloop.fmi.plugin.editor.hardware.HardwareEditorDialogFactory;
import org.raspinloop.pi4j.io.gpio.RaspiGpioSimulatorProperties;

import javax.swing.*;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RaspberryPiBoardPropertiesEditor extends HardwareEditorDialog<RaspiGpioSimulatorProperties> {

    private JPanel topLevelPanel;

    private LabeledComponent<JTextField> componentName;
    private org.raspinloop.fmi.plugin.editor.component.GpioAssignation gpioAssignation1;
    private AddEditRemovePanel addEditRemovePanel;
    private AddEditRemovePanel<HardwareProperties> hardwareTable;

    @Nullable
    private Project project;
    private RaspiGpioSimulatorProperties board;

    public RaspberryPiBoardPropertiesEditor(Project project) {
        super(project);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return topLevelPanel;
    }

    @Override
    public void init(RaspiGpioSimulatorProperties property) {
        board = property;
        gpioAssignation1.setBoard(property);
        init();
    }

    @Override
    public Class<RaspiGpioSimulatorProperties> getSupportedPropertiesForEdition() {
        return RaspiGpioSimulatorProperties.class;
    }

    @Override
    public RaspiGpioSimulatorProperties getEdited() {
        return board;
    }

    private void createUIComponents() {
        componentName = LabeledComponent.create(new JTextField(), "Board name");
        gpioAssignation1 = new org.raspinloop.fmi.plugin.editor.component.GpioAssignation("Pin usage");
        hardwareTable = new AddEditRemovePanel<HardwareProperties>(new org.raspinloop.fmi.plugin.editor.component.HardwareListTableModel(), toPinOrderedList(board.getAllComponents()), "Add/Remove or modify simulated hardware platform"){

            @Nullable
            @Override
            protected HardwareProperties addItem() {
                // add Board
                org.raspinloop.fmi.plugin.editor.hardware.HardwareSelectionDialog cs = new org.raspinloop.fmi.plugin.editor.component.ComponentSelectionDialog(project);
                cs.show();
                HardwareProperties selectedHardware = cs.getSelected();
                HardwareEditorDialog editor = HardwareEditorDialogFactory.INSTANCE().create(project, selectedHardware.getClass());
                editor.init(selectedHardware);
                editor.show();
                if (editor.isOK()) {
                    selectedHardware = editor.getEdited();
                    return selectedHardware;
                } else {
                    return null;
                }
            }

            @Override
            protected boolean removeItem(HardwareProperties o) {
                return true;
            }

            @Nullable
            @Override
            protected HardwareProperties editItem(HardwareProperties o) {
                HardwareEditorDialog editor = HardwareEditorDialogFactory.INSTANCE().create(project, o.getClass());
                editor.init(o);
                editor.show();
                if (editor.isOK()) {
                    return editor.getEdited();
                } else {
                    return null;
                }
            }
        };
    }

    private List<HardwareProperties> toPinOrderedList(Collection<HardwareProperties> allComponents) {
        return allComponents.stream()
               .sorted(Comparator.comparingInt(this::getLowestPin) )
               .collect(Collectors.toList());
    }

    private int getLowestPin(HardwareProperties c1) {
       return    c1.getUsedPins()
                .stream()
                .sorted( Comparator.comparingInt(Pin::getAddress))
                .mapToInt(Pin::getAddress)
                .findFirst().orElse(0);

    }
}
