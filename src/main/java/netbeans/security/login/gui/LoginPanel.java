/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License
 */

package netbeans.security.login.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.JXLoginPane;
import org.openide.DialogDescriptor;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;

class LoginPanel extends JXLoginPane {
    private JButton okButton;
    private JButton cancelButton;

    public LoginPanel() {
        this.okButton = createOkButton();
        this.cancelButton = createCancelButton();
        setSaveMode(SaveMode.NONE);

        addPropertyChangeListener("status", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                JXLoginPane.Status status = (JXLoginPane.Status)evt.getNewValue();
                switch (status)
                {
                    case NOT_STARTED:
                        break;
                    case IN_PROGRESS:
                        cancelButton.setEnabled(false);
                        break;
                    case CANCELLED:
                        cancelButton.setEnabled(true);
                        pack();
                        break;
                    case FAILED:
                        cancelButton.setEnabled(true);
                        pack();
                        break;
                    case SUCCEEDED:
                        // dispose dialog to allow main window to be opened...
                        SwingUtilities.getWindowAncestor(LoginPanel.this).dispose();
                }
            }
        });
    }

    private JButton createOkButton() {
        return new JButton(getActionMap().get(LOGIN_ACTION_COMMAND));
    }

    private JButton createCancelButton() {
        JButton button = new JButton("Cancel");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(LoginPanel.this).dispose();
                exit();
            }
        });

        return button;
    }

    public DialogDescriptor createDialogDescriptor() {
        DialogDescriptor dd = new DialogDescriptor(this,
                "Login",
                true,
                new Object[]{ okButton, cancelButton },
                okButton,
                DialogDescriptor.DEFAULT_ALIGN,
                null,
                null);

        // no option should close dialog by default...
        dd.setClosingOptions(new Object[]{});

        dd.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if("value".equalsIgnoreCase(evt.getPropertyName())) {
                    // Escape pressed or dialog closed...
                    if(NotifyDescriptor.CLOSED_OPTION.equals(evt.getNewValue())) {
                        exit();
                    }
                }
            }
        });

        return dd;
    }

    public void exit() {
       LifecycleManager.getDefault().exit();
    }

    private void pack() {
        revalidate();
        SwingUtilities.getWindowAncestor(this).pack();
    }

}
