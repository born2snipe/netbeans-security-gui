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

class JXLoginServiceDecorator extends org.jdesktop.swingx.auth.LoginService {
    private LoginService loginService;
    private UserRoleService userRoleService;
    private String currentUsername;

    @Override
    public boolean authenticate(String username, char[] password, String server) throws Exception {
        currentUsername = username;
        return loginService.login(username, new String(password));
    }

    @Override
    public String[] getUserRoles() {
        if (userRoleService != null) {
            return userRoleService.rolesUser(currentUsername).toArray(new String[0]);
        }
        return new String[0];
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

}
