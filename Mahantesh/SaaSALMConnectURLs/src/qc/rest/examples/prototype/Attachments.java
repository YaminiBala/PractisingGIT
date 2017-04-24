/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.prototype;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import qc.rest.examples.infrastructure.Constants;
import qc.rest.examples.infrastructure.Response;
import qc.rest.examples.prototype.RestConnector;

public class Attachments {
    RestConnector con;

    public byte[] convertTobytes(String attachPath) {
        File file = new File(attachPath);
        byte[] b = new byte[(int)file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            int i = 0;
            while (i < b.length) {
                System.out.print(b[i]);
                ++i;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        }
        catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return b;
    }

    public String attachWithOctetStream(String Instanceid, byte[] fileData, String filename) throws Exception {
        String entityUrl = "http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/runs/" + Instanceid;
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Slug", filename);
        requestHeaders.put("Content-Type", "application/octet-stream");
        requestHeaders.put("Accept", "application/xml");
        Response response = this.con.httpPost(String.valueOf(entityUrl) + "/attachments" + filename, fileData, requestHeaders);
        return response.getResponseHeaders().get("Location").iterator().next();
    }

    public String updateAttachmentData(String Instanceid, byte[] bytes, String attachmentFileName) throws Exception {
        String entityUrl = "http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin/rest/domains/" + Constants.DOMAIN + "/projects/" + Constants.PROJECT + "/runs/" + Instanceid;
        HashMap<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "application/octet-stream");
        requestHeaders.put("Accept", "application/xml");
        byte[] ret = this.con.httpPut(String.valueOf(entityUrl) + "/attachments/" + attachmentFileName, bytes, requestHeaders).getResponseData();
        return new String(ret);
    }
}

