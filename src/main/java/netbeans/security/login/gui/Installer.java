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

import netbeans.security.login.LoginService;
import netbeans.security.login.UserRoleService;
import org.openide.DialogDisplayer;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;

public class Installer extends ModuleInstall {
    @Override
    public void restored() {
        JXLoginServiceDecorator decorator = new JXLoginServiceDecorator();
        decorator.setLoginService(Lookup.getDefault().lookup(LoginService.class));
        decorator.setUserRoleService(Lookup.getDefault().lookup(UserRoleService.class));

        LoginPanel form = new LoginPanel();
        form.setLoginService(decorator);

        DialogDisplayer.getDefault().notifyLater(form.createDialogDescriptor());
    }
}
