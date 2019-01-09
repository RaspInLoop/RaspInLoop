package org.raspinloop.fmi.plugin.editor.hardware;

import com.intellij.ide.util.gotoByName.ChooseByNameBase;
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider;
import com.intellij.ide.util.gotoByName.ChooseByNameModel;
import com.intellij.ide.util.gotoByName.ChooseByNamePopupComponent;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;


public class HardwareSelectionPanel extends ChooseByNameBase implements Disposable {
    private JPanel myPanel;

    public HardwareSelectionPanel(Project project, ChooseByNameModel model, ChooseByNameItemProvider provider, String initialText) {
        super(project, model, provider, initialText);
    }

    @Override
    protected void initUI(ChooseByNamePopupComponent.Callback callback, ModalityState modalityState, boolean allowMultipleSelection) {
        super.initUI(callback, modalityState, allowMultipleSelection);

        myTextFieldPanel.setBorder(null);

        myPanel = new JPanel(new GridBagLayout());

        myPanel.add(myTextFieldPanel, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        myPanel.add(myListScrollPane, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public JComponent getPreferredFocusedComponent() {
        return myTextField;
    }

    @Override
    protected void showList() {
    }

    @Override
    protected void hideList() {
    }

    @Override
    protected void close(boolean isOk) {
    }

    @Override
    protected boolean isShowListForEmptyPattern() {
        return true;
    }

    @Override
    protected boolean isCloseByFocusLost() {
        return false;
    }

    @Override
    protected boolean isCheckboxVisible() {
        return false;
    }

    public JPanel getPanel() {
        return myPanel;
    }

    @Override
    public void dispose() {
        setDisposed(true);
        cancelListUpdater();
    }
}

