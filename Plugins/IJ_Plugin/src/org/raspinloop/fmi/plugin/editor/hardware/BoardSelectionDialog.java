package org.raspinloop.fmi.plugin.editor.hardware;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.hwemulation.HardwareClassFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BoardSelectionDialog extends HardwareSelectionDialog {

    public BoardSelectionDialog(Project project) {
        super(project);
    }

    @NotNull
    protected Model getModel() {

        List<HardwareSelectionItem> items  = wrap(getAllBoard());
        return new Model("Choose a board", "No hardware found", items);
    }

    private Collection<HardwareProperties> getAllBoard() {
        return HardwareClassFactory.INSTANCE().buildListImplementing(BoardHardware.class)
                .stream()
                .filter(this::isRealImplementation)
                .collect(Collectors.toList());
    }


}
