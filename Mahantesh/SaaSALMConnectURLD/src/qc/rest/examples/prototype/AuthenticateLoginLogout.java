/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.prototype;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import qc.rest.examples.infrastructure.Response;
import qc.rest.examples.prototype.RestConnector;
import sun.misc.BASE64Encoder;

public class AuthenticateLoginLogout {
    private RestConnector con = RestConnector.getInstance();

    public boolean login(String username, String passowrd) throws Exception {
        String authenticationPoint = this.isAuthenticated();
        if (authenticationPoint != null) {
            return this.login(authenticationPoint, username, passowrd);
        }
        return true;
    }

    public boolean login(String loginUrl, String username, String password) throws Exception {
        byte[] credBytes = (String.valueOf(username) + ":" + password).getBytes();
        String credEncodedString = "Basic " + new BASE64Encoder().encode(credBytes);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("Authorization", credEncodedString);
        Response response = this.con.httpGet(loginUrl, null, map);
        boolean ret = response.getStatusCode() == 200;
        return ret;
    }

    public boolean logout() throws Exception {
        Response response = this.con.httpGet(this.con.buildUrl("qcbin/authentication-point/logout"), null, null);
        if (response.getStatusCode() == 200) {
            return true;
        }
        return false;
    }

    public String isAuthenticated() throws Exception {
        String ret;
        String isAuthenticateUrl = this.con.buildingUrl("qcbin/rest/is-authenticated");
        Response response = this.con.httpGet(isAuthenticateUrl, null, null);
        int responseCode = response.getStatusCode();
        if (responseCode == 200) {
            ret = null;
        } else if (responseCode == 401) {
            Iterable<String> authenticationHeader = response.getResponseHeaders().get("WWW-Authenticate");
            String newUrl = authenticationHeader.iterator().next().split("=")[1];
            newUrl = newUrl.replace("\"", "");
            ret = newUrl = String.valueOf(newUrl) + "/authenticate";
        } else {
            throw response.getFailure();
        }
        return ret;
    }
}

