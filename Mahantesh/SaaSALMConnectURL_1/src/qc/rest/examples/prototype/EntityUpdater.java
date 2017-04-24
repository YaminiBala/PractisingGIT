/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.prototype;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import qc.rest.examples.infrastructure.Constants;
import qc.rest.examples.infrastructure.Entity;
import qc.rest.examples.infrastructure.EntityMarshallingUtils;
import qc.rest.examples.infrastructure.Response;
import qc.rest.examples.prototype.AuthenticateLoginLogout;
import qc.rest.examples.prototype.RestConnector;

public class EntityUpdater {
    String domain;
    String project;
    RestConnector con;
    AuthenticateLoginLogout auth;
    public boolean VERSIONED = false;

    public EntityUpdater(String almDomain, String almProject) {
        this.domain = almDomain.trim();
        this.project = almProject.trim();
        this.con = RestConnector.getInstance().init(new HashMap<String, String>(), Constants.HOST, Constants.PORT, this.domain, this.project);
        this.auth = new AuthenticateLoginLogout();
    }

    public void loginCall() throws Exception {
        boolean isLoggedIn = this.auth.login(Constants.USERNAME, Constants.PASSWORD);
        if (!isLoggedIn) {
            System.out.println("ALM Error: Unable to Login.");
        }
    }

    public void logoutCall() throws Exception {
        this.auth.logout();
    }

    private void doUpdate(String testCaseId, String field, String value) throws Exception {
       /* String newEntityToUpdateUrl = this.con.buildEntityUrl("test-instance", testCaseId);
        String string = this.lock(newEntityToUpdateUrl);
        String entityUpdateXml = this.generateUpdateXml(field, value);
        System.out.println("entityUpdateXml::"+entityUpdateXml);
        Entity e = (Entity)EntityMarshallingUtils.marshal(Entity.class, entityUpdateXml);
        System.out.println("Yamini1");
        String updateResponseEntityXml = this.update(newEntityToUpdateUrl, EntityMarshallingUtils.unmarshal(Entity.class, e)).toString();
        System.out.println("Yamini");
        boolean bl = this.unlock(newEntityToUpdateUrl);*/
     String newEntityToUpdateUrl = con.buildEntityUrl("test-instance",testCaseId);
     // System.out.println("newEntityToUpdateUrl"+newEntityToUpdateUrl);
			
    if (VERSIONED) {
       // System.out.println("Yamini");
            checkout(newEntityToUpdateUrl, "REST Test Checkout", -1);
    }
    else {
            lock(newEntityToUpdateUrl);
          //  String L=lock(newEntityToUpdateUrl);
          //  System.out.println("L"+L);
    }
    
    //Create update string
    String entityUpdateXml = generateUpdateXml(field, value);
   // System.out.println("entityUpdateXml"+entityUpdateXml);
    //Create entity. (We could have instantiated the entity and used methods to set the new values.)
    Entity e = EntityMarshallingUtils.marshal(Entity.class, entityUpdateXml);

    update(newEntityToUpdateUrl,EntityMarshallingUtils.unmarshal(Entity.class, e)).toString();
            
    //checkin
    if (VERSIONED) {
            checkin(newEntityToUpdateUrl);
    }
    else {
            unlock(newEntityToUpdateUrl);
    }
    }

    private void doAttachment(String instanceId, byte[] filedata, String filename) throws Exception {
        String newEntityToUpdateUrl = this.con.buildEntityUrl("run", instanceId);
        String string = this.lock(newEntityToUpdateUrl);
        Response updateResponseEntityXml = this.attachWithOctetStream(instanceId, filedata, filename);
        System.out.println("updateResponseEntityXml:" + updateResponseEntityXml);
        boolean bl = this.unlock(newEntityToUpdateUrl);
    }

    private void doDuration(String instanceId, byte[] filedata) throws Exception {
        String newEntityToUpdateUrl = this.con.buildEntityUrl("runs", instanceId);
        String string = this.lock(newEntityToUpdateUrl);
        Response updateResponseEntityXml = this.updateDuration(instanceId, filedata);
        System.out.println("updateResponseEntityXml:" + updateResponseEntityXml);
        boolean bl = this.unlock(newEntityToUpdateUrl);
    }

    public String checkout(String entityUrl, String comment, int version) throws Exception {
        String commentXmlBit = comment != null && !comment.isEmpty() ? "<Comment>" + comment + "</Comment>" : "";
        String versionXmlBit = version >= 0 ? "<Version>" + version + "</Version>" : "";
        String xmlData = String.valueOf(commentXmlBit) + versionXmlBit;
        String xml = xmlData.isEmpty() ? "" : "<CheckOutParameters>" + xmlData + "</CheckOutParameters>";
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        Response response = this.con.httpPost(String.valueOf(entityUrl) + "/versions/check-out", xml.getBytes(), requestHeaders);
        return response.toString();
    }

    public void updateTestCase(String testCaseId, String field, String value) {
        try {
            this.doUpdate(testCaseId.trim(), field.trim(), value.trim());
        }
        catch (Exception e) {
            System.out.println("Exception in Update - Unlock Enitty.....");
            String newEntityToUpdateUrl = this.con.buildEntityUrl("test-instance", testCaseId);
            try {
                boolean unlock = this.unlock(newEntityToUpdateUrl);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void CreateTCAttachment(String instanceId, byte[] filedata, String filename) {
        try {
            this.doAttachment(instanceId.trim(), filedata, filename.trim());
        }
        catch (Exception e) {
            System.out.println("Exception in Update - Unlock Enitty.....");
            String newEntityToUpdateUrl = this.con.buildEntityUrl("run", instanceId);
            try {
                boolean unlock = this.unlock(newEntityToUpdateUrl);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void RunDuration(String instanceId, byte[] filedata) {
        try {
            this.doDuration(instanceId.trim(), filedata);
        }
        catch (Exception e) {
            System.out.println("Exception in Update - Unlock Enitty.....");
            String newEntityToUpdateUrl = this.con.buildEntityUrl("runs", instanceId);
            try {
                boolean unlock = this.unlock(newEntityToUpdateUrl);
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public boolean checkin(String entityUrl) throws Exception {
        Response response = this.con.httpPost(String.valueOf(entityUrl) + "/versions/check-in", null, null);
        boolean ret = response.getStatusCode() == 200;
        return ret;
    }

    public String lock(String entityUrl) throws Exception {
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Accept", "application/xml");
        return this.con.httpPost(String.valueOf(entityUrl) + "/lock", null, requestHeaders).toString();
    }

    public boolean unlock(String entityUrl) throws Exception {
        if (this.con.httpDelete(String.valueOf(entityUrl) + "/lock", null).getStatusCode() == 200) {
            return true;
        }
        return false;
    }

    private String generateUpdateXml(String field, String value) {
        return "<Entity Type=\"test-instance\"><Fields><Field Name=\"" + field + "\"><Value>" + value + "</Value></Field>" + "</Fields></Entity>";
    }

    private Response update(String entityUrl, String updatedEntityXml) throws Exception {
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        Response put = this.con.httpPut(entityUrl, updatedEntityXml.getBytes(), requestHeaders);
        return put;
    }

    public String updateAttachmentData(String instanceId, byte[] bytes, String attachmentFileName) throws Exception {
        String entityUrl = "http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/runs/" + instanceId;
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/octet-stream");
        requestHeaders.put("Accept", "application/xml");
        byte[] ret = this.con.httpPut(String.valueOf(entityUrl) + "/attachments/" + attachmentFileName, bytes, requestHeaders).getResponseData();
        return new String(ret);
    }

    public Response getInstanceId(String cycleid, String testid) throws Exception {
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        Response XML1 = this.con.httpGet("http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/test-instances?query={cycle-id[" + cycleid + "];test-id[" + testid + "]}&fields=id", null, requestHeaders);
        System.out.println("XML is ::" + XML1);
        return XML1;
    }

    public Response getInstanceIdFromRun(String cycleid, String testid) throws Exception {
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        Response XML1 = this.con.httpGet("http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/runs?query={cycle-id[" + cycleid + "];test-id[" + testid + "]}", null, requestHeaders);
        System.out.println("XML is ::" + XML1);
        return XML1;
    }

    public Response attachWithOctetStream(String Instanceid, byte[] fileData, String filename) throws Exception {
        String entityUrl = "http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/runs/" + Instanceid;
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Slug", filename);
        requestHeaders.put("Content-Type", "application/octet-stream");
        requestHeaders.put("Accept", "application/xml");
        Response response = this.con.httpPost(String.valueOf(entityUrl) + "/attachments", fileData, requestHeaders);
        return response;
    }

    public Response updateDuration(String Instanceid, byte[] fileData) throws Exception {
        String entityUrl = "http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/runs/" + Instanceid;
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/octet-stream");
        requestHeaders.put("Accept", "application/xml");
        Response response = this.con.httpPost(String.valueOf(entityUrl) + "/duration", fileData, requestHeaders);
        return response;
    }

    public Response attachWithOctetStream1(String Instanceid, byte[] fileData, String filename) throws Exception {
        String entityUrl = "http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/test-instances/" + Instanceid;
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/xml");
        requestHeaders.put("Accept", "application/xml");
        Response response = this.con.httpPost(String.valueOf(entityUrl) + "/attachments", fileData, requestHeaders);
        String res = response.getResponseHeaders().get("Location").iterator().next();
        return response;
    }

    public Response attachWithMultipart(String InstanceId, byte[] fileData, String contentType, String filename, String description) throws Exception {
        String entityUrl = "http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/test-instances/" + InstanceId;
        String boundary = "exampleboundary";
        String fieldTemplate = "--%1$s\r\nContent-Disposition: form-data; name=\"%2$s\" \r\n\r\n%3$s\r\n";
        String fileDataPrefixTemplate = "--%1$s\r\nContent-Disposition: form-data; name=\"%2$s\"; filename=\"%3$s\"\r\nContent-Type: %4$s\r\n\r\n";
        String filenameData = String.format(fieldTemplate, boundary, "filename", filename);
        String descriptionData = String.format(fieldTemplate, boundary, "description", description);
        String fileDataSuffix = "\r\n--" + boundary + "--";
        String fileDataPrefix = String.format(fileDataPrefixTemplate, boundary, "file", filename, contentType);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bytes.write(filenameData.getBytes());
        bytes.write(descriptionData.getBytes());
        bytes.write(fileDataPrefix.getBytes());
        bytes.write(fileData);
        bytes.write(fileDataSuffix.getBytes());
        bytes.close();
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "multipart/form-data; boundary=" + boundary);
        Response response = this.con.httpPost(String.valueOf(entityUrl) + "/attachments", bytes.toByteArray(), requestHeaders);
        return response;
    }
}

