package org.raspinloop.fmi.plugin.editor.hardware;

import com.intellij.openapi.project.Project;
import org.raspinloop.config.HardwareProperties;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Class used to create instance of dialog editor for each type of simulated hardware.
 * It scans all org.raspinloop.* classes that implements HardwarePropertiesEditor.
 */
public class HardwareEditorDialogFactory {


    private static HardwareEditorDialogFactory INSTANCE;
    private Reflections reflections = new Reflections("org.raspinloop", new Scanner[0]);

    public static HardwareEditorDialogFactory INSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new HardwareEditorDialogFactory();
        }

        return INSTANCE;
    }

    private HardwareEditorDialogFactory() {
    }

    @Nonnull
    public HardwareEditorDialog create(Project project, Class<? extends HardwareProperties> aClass) {

        Collection<HardwareEditorDialog> editors = getAllEditors(project);
        for (HardwareEditorDialog editor : editors){
            if (editor.getSupportedPropertiesForEdition().equals(aClass)){
                return editor;
            }
        }
        //if not found -> use default
        return new DefaultHardwareEditorDialog(project);
    }

    //TODO: editors should be plugins extensions points. So it would be easier to build additional plugins to provide new editor for new simulated hardware.
    private Collection<HardwareEditorDialog> getAllEditors(Project project) {
        Set<Class<? extends HardwareEditorDialog>> propertiesClasses = this.reflections.getSubTypesOf(HardwareEditorDialog.class);
        HashSet<HardwareProperties> propertiesEditors = new HashSet();
        Iterator i = propertiesClasses.iterator();

        Collection<HardwareEditorDialog> editors = new HashSet<>();
        while(i.hasNext()) {
            Object hardwareConfigClass = i.next();
            if (hardwareConfigClass instanceof Class) try {
                Object object =  ((Class) hardwareConfigClass).getDeclaredConstructor(Project.class).newInstance(project);
                if (object instanceof HardwareEditorDialog) {
                    editors.add((HardwareEditorDialog) object);
                }
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return editors;
    }
}
