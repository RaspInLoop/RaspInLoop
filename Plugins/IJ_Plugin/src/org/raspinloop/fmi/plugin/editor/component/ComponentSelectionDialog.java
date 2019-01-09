package org.raspinloop.fmi.plugin.editor.component;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.raspinloop.config.*;
import org.raspinloop.fmi.plugin.editor.hardware.HardwareSelectionDialog;
import org.raspinloop.hwemulation.HardwareClassFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ComponentSelectionDialog extends HardwareSelectionDialog {

    protected ComponentSelectionDialog(Project project) {
        super(project);
    }

    @NotNull
    protected HardwareSelectionDialog.Model getModel() {

        List<HardwareSelectionItem> items  = wrap(getAllComponent());
        return new Model("Choose a component", "No component found", items);
    }

    private Collection<HardwareProperties> getAllComponent() {
        Collection<HardwareProperties> collection = HardwareClassFactory.INSTANCE().buildListImplementing(BoardExtentionHardware.class);
        collection.addAll(HardwareClassFactory.INSTANCE().buildListImplementing(SPIComponent.class));
        collection.addAll(HardwareClassFactory.INSTANCE().buildListImplementing(I2CComponent.class));
        collection.addAll(HardwareClassFactory.INSTANCE().buildListImplementing(UARTComponent.class));
        return collection.stream()
                .filter(this::isRealImplementation)
                .collect(Collectors.toList());
    }
}
