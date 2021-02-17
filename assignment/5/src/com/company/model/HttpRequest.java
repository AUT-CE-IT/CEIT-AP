package com.company.model;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * HttpRequest obj holds response information and waits
 * for bridge to create connection between response and
 * gui.
 */
public class HttpRequest {

    //TODO
    // content-length .getContentLength method for file size as bytes not working?
    private static String URL;
    private static String METHOD;
    private static String HEADERS;
    private static int STATUS_CODE;
    private static String STATUS_MSG;
    // method response
    private static String RESPONSE;
    private Map<String, List<String>> RESPONSE_HEADERS;
    private final int contentLen;

    public HttpRequest(HttpURLConnection conn , String response) throws IOException {
        URL = conn.getURL().toString();
        METHOD = conn.getRequestMethod();
        STATUS_CODE = conn.getResponseCode();
        STATUS_MSG = conn.getResponseMessage();
        RESPONSE = createResponse(response);
        HEADERS = getHeaders(conn);
        RESPONSE_HEADERS = conn.getHeaderFields();
        contentLen = conn.getContentLength();
    }

    @Override
    public String toString() {
        String str = "``````````````````````````````````````````````````````````````````````````\n\n" +
                "URL_ADDRESS :: " + URL + "\n\nMETHOD :: [" + METHOD + "]\n\n" +
                "STATUS CODE :: " + STATUS_CODE + " " + STATUS_MSG + " content-length :: " + contentLen
                + "\n\n";

        if (STATUS_CODE == HttpURLConnection.HTTP_OK) str = str.concat(
                "RESPONSE ::\n" + RESPONSE + "\n\n" +
                        "HEADERS :: \n" + HEADERS + "\n" +
                        "``````````````````````````````````````````````````````````````````````````\n"
        );
        return str;
    }

    private static String createResponse(String msgBody) {
        // concert body to array of characters
        char[] body = new char[msgBody.length()];

        // Copy character by character into array
        for (int i = 0; i < msgBody.length(); i++) {
            body[i] = msgBody.charAt(i);
        }

        StringBuilder str = new StringBuilder();

        for (int  i = 0 ; i < body.length ; i++) {
            str.append(body[i]);
            if (i % 50 == 0 && i != 0) str.append('\n');
        }
        return str.toString();
    }

    private static String getHeaders(HttpURLConnection httpCon) {
        StringBuilder str = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : httpCon.getHeaderFields().entrySet()) {
            String add = entry.getKey() + "::" + entry.getValue() + "\n";
            str.append(add);
        }
        str.append("\n");
        return str.toString();
    }

    public void writeToFile() throws IOException {
        String mkdir = ".\\--save\\RESPONDS.txt";
        try (PrintWriter out = new PrintWriter(new FileWriter(mkdir, true))) {
            String data = this.toString();
            out.println(data);
        }
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }

    public String getRESPONSE() {
        return RESPONSE;
    }

    public String getStatusMsg() {
        return STATUS_MSG;
    }

    public Map<String, List<String>> getRESPONSE_HEADERS() {
        return RESPONSE_HEADERS;
    }

    public int getContentLen() {
        return contentLen;
    }
}
