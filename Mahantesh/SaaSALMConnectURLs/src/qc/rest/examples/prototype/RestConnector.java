/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package qc.rest.examples.prototype;
import javax.net.ServerSocketFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import qc.rest.examples.infrastructure.Response;

public class RestConnector {
    private static Logger log = Logger.getLogger((String)"tracer");
    protected Map<String, String> cookies;
    protected String host;
    protected String port;
    protected String domain;
    protected String project;
    private static RestConnector instance = new RestConnector();

    public RestConnector init(Map<String, String> cookies, String host, String port, String domain, String project) {
        log.trace((Object)"Entering RestConnector");
        this.cookies = cookies;
        this.host = host;
        this.port = port;
        this.domain = domain;
        this.project = project;
        return this;
    }

    private RestConnector() {
    }

    public static RestConnector getInstance() {
        log.trace((Object)"Entering RestConnector instance");
        return instance;
    }

    public String buildEntityCollectionUrl(String entityType) {
        return this.buildUrl("qcbin/rest/domains/" + this.domain + "/projects/" + this.project + "/" + entityType + "s");
    }

    public String buildEntityUrl(String entityType, String entityId) {
        return this.buildUrl("qcbin/rest/domains/" + this.domain + "/projects/" + this.project + "/" + entityType + "s" + "/" + entityId);
    }

    public String buildEntityUrlTestInstance(String entityType) {
        return this.buildUrl("qcbin/rest/domains/" + this.domain + "/projects/" + this.project + "/" + entityType + "s");
    }

    public String buildUrl(String path) {
        return String.format("http://%1$s:%2$s/%3$s", this.host, this.port, path);
    }

    public Map<String, String> getCookies() {
        return this.cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProject() {
        return this.project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Response httpPut(String url, byte[] data, Map<String, String> headers) throws Exception {
        return this.doHttp("PUT", url, null, data, headers, this.cookies);
    }

    public Response httpPost(String url, byte[] data, Map<String, String> headers) throws Exception {
        return this.doHttp("POST", url, null, data, headers, this.cookies);
    }

    public Response httpDelete(String url, Map<String, String> headers) throws Exception {
        return this.doHttp("DELETE", url, null, null, headers, this.cookies);
    }

    public Response httpGet(String url, String queryString, Map<String, String> headers) throws Exception {
        return this.doHttp("GET", url, queryString, null, headers, this.cookies);
    }
    public Response checkhttpGet(String url, String queryString, Map<String, String> headers) throws Exception {
        return this.checkdoHttp("GET", url, queryString, null, headers, this.cookies);
    }

    private Response doHttp(String type, String url, String queryString, byte[] data, Map<String, String> headers, Map<String, String> cookies) throws Exception {
        if (queryString != null && !queryString.isEmpty()) {
            queryString = queryString.replaceAll("\\s", "%20");
            url = String.valueOf(url) + "?" + queryString;
        }
        System.out.println("URL " + url);
        HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();
     //  InputStream inputStream= con.getInputStream();
        System.out.println("con:"+con);
        con.setRequestMethod(type);
        
        String cookieString = this.getCookieString();
        System.out.println("cookieString"+cookieString);
        System.out.println("headers"+headers);
        System.out.println("data"+data);
        this.prepareHttpRequest(con, headers, data, cookieString);
        
        con.connect();
       // con.getInputStream();
        Response ret = this.retrieveHtmlResponse(con);
       
        this.updateCookies(ret);
        return ret;
    }
    
    private Response checkdoHttp(String type, String url, String queryString, byte[] data, Map<String, String> headers, Map<String, String> cookies) throws Exception {
         if (queryString != null && !queryString.isEmpty()) {
            queryString = queryString.replaceAll("\\s", "%20");
            url = String.valueOf(url) + "?" + queryString;
        }
        String saveDir="C:\\ALMConfig";
        System.out.println("URL " + url);
        HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();
     //  InputStream inputStream= con.getInputStream();
        System.out.println("con:"+con);
        con.setRequestMethod(type);
        
        String cookieString = this.getCookieString();
        System.out.println("cookieString"+cookieString);
        System.out.println("headers"+headers);
        System.out.println("data"+data);
        this.prepareHttpRequest(con, headers, data, cookieString);
        
        con.connect();
       InputStream inputStream = con.getInputStream();
       System.out.println("inputStream"+inputStream);
        String saveFilePath = saveDir + File.separator + "Yamini.txt";
        OutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
          //  outputStream.close();
           // inputStream.close();
 
            System.out.println("File downloaded");
        Response ret = this.retrieveHtmlResponse(con);
       
        this.updateCookies(ret);
        return ret;
    }
    
    

    private void prepareHttpRequest(HttpURLConnection con, Map<String, String> headers, byte[] bytes, String cookieString) throws IOException {
        String contentType = null;
        if (cookieString != null && !cookieString.isEmpty()) {
            con.setRequestProperty("Cookie", cookieString);
        }
        if (headers != null) {
            contentType = headers.remove("Content-Type");
            for (Map.Entry<String, String> header : headers.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }
        }
        if (bytes != null && bytes.length > 0) {
            con.setDoOutput(true);
            if (contentType != null) {
                con.setRequestProperty("Content-Type", contentType);
            }
            OutputStream out = con.getOutputStream();
            System.out.println("print1"+out);
            out.write(bytes);
            System.out.println("print"+con.getHeaderFields());
            out.flush();
            out.close();
        }
    }

    private Response retrieveHtmlResponse(HttpURLConnection con) throws Exception {
        int read;
        System.out.println("Getting connection in"+con.getHeaderFields());
        InputStream inputStream;
        Response ret = new Response();
        ret.setStatusCode(con.getResponseCode());
        ret.setResponseHeaders(con.getHeaderFields());
        try {
            inputStream = con.getInputStream();
        }
        catch (Exception e) {
            inputStream = con.getErrorStream();
            ret.setFailure(e);
        }
        ByteArrayOutputStream container = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while ((read = inputStream.read(buf, 0, 1024)) > 0) {
            container.write(buf, 0, read);
        }
        ret.setResponseData(container.toByteArray());
        return ret;
    }

    public void updateCookies(Response response) {
        Iterable<String> newCookies = response.getResponseHeaders().get("Set-Cookie");
        if (newCookies != null) {
            for (String cookie : newCookies) {
                int equalIndex = cookie.indexOf(61);
                int semicolonIndex = cookie.indexOf(59);
                String cookieKey = cookie.substring(0, equalIndex);
                String cookieValue = cookie.substring(equalIndex + 1, semicolonIndex);
                this.cookies.put(cookieKey, cookieValue);
            }
        }
    }

    public String getCookieString() {
        StringBuilder sb = new StringBuilder();
        if (!this.cookies.isEmpty()) {
            Set<Map.Entry<String, String>> cookieEntries = this.cookies.entrySet();
            for (Map.Entry<String, String> entry : cookieEntries) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
            }
        }
        String ret = sb.toString();
        return ret;
    }
    
    public void downloadFile(String fileURL, String saveDir)
            throws IOException {
        String url = "http://10.25.130.162:8080/qcbin/rest/domains/CET/projects/ENTERPRISE_SERVICES/runs/1627811/attachments?Log_2016-04-26_12hrs41min20sec.txt)";
        System.out.println("Download url"+url);
      //  HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
         HttpURLConnection httpConn = (HttpURLConnection)new URL(url).openConnection();
         httpConn.setRequestMethod("GET");
        
        String cookieString = this.getCookieString();
        int responseCode = httpConn.getResponseCode();
   System.out.println("responseCode"+responseCode);
        // always check HTTP response code first
       if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
             
            // opens an output stream to save into file
            OutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded");
              
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
      httpConn.disconnect();
    }
}



