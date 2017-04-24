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
        String isAuthenticateUrl = this.con.buildUrl("qcbin/rest/is-authenticated");
        Response response1 = this.con.httpGet(isAuthenticateUrl, null, null);
        System.out.println("response1"+response1);
        GetQCSession();
        
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

//--------------

public void GetQCSession(){
        String qcsessionurl = con.buildUrl("qcbin/rest/site-session");
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        try {
            Response resp = con.httpPost(qcsessionurl, null, requestHeaders);
            System.out.println(resp);
            con.updateCookies(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }


//-----

    public String isAuthenticated() throws Exception {
        String ret;
        String isAuthenticateUrl = this.con.buildUrl("qcbin/rest/is-authenticated");
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

