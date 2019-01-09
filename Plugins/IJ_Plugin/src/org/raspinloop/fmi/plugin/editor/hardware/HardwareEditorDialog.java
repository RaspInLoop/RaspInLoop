package org.raspinloop.fmi.plugin.editor.hardware;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;
import org.raspinloop.config.HardwareProperties;

import javax.swing.*;

public abstract class HardwareEditorDialog<T  extends HardwareProperties> extends DialogWrapper {

    protected HardwareEditorDialog(@Nullable Project project) {
        super(project);
    }
    public abstract void init(T property);
    public abstract Class<T> getSupportedPropertiesForEdition();
    public abstract T getEdited();
}
