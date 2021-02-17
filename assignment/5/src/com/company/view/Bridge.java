package com.company.view;

import com.company.model.HttpRequest;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Bridge Thread makes a connection between gui and HttpConnection class.
 */
public class Bridge extends Thread {

    private final long startTime;

    private RequestPanel.Right right;

    private final HttpRequest httpRequest;

    private final RequestPanel.Request request;

    public Bridge(HttpRequest httpRequest, RequestPanel.Request request, Long startTime) {
        this.httpRequest = httpRequest;
        this.request = request;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        update();
    }

    public void update() {
        // insert data into request obj
        request.setStatusCode(httpRequest.getStatusCode());
        request.setStatusMassage(httpRequest.getStatusMsg());

        if ( !httpRequest.getRESPONSE().equals("") ) request.setResponse(
                httpRequest.getRESPONSE().replace("\n", ""));
        else if (httpRequest.getStatusCode() != 200) request.setResponse(httpRequest.getStatusCode() + " :: " + httpRequest.getStatusMsg());
        else request.setResponse("no body for response.");

        request.setResponseHeaders(httpRequest.getRESPONSE_HEADERS());
        request.setContentLen(httpRequest.getContentLen());

        long time = Math.abs(startTime - System.nanoTime()) / 10000000;
        request.setTime((int) time);

        //insert data into right panel
        String status = request.getStatusCode() + " " + request.getStatusMassage();
        right.getStatus().setText(status);

        request.setResponseHeaders(httpRequest.getRESPONSE_HEADERS());
        allHeaders();

        right.getTime().setText(time + " ms");
        right.getContentLen().setText(request.getContentLen() + " bytes");
        if ( ((request.getResponse() == null || request.getResponse().equals("")) ) && httpRequest.getStatusCode() != 200) right.getResponseMsg().setText(
                httpRequest.getStatusCode() + " :: " + httpRequest.getStatusMsg());
        else if ( ((request.getResponse() == null || request.getResponse().equals("")) ) && httpRequest.getStatusCode() == 200) right.getResponseMsg().setText(
                "no body for respond.");
        else right.getResponseMsg().setText(createResponse(request.getResponse()));
        this.stop();
    }

    private void allHeaders() {
        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT));
        card.setPreferredSize(new Dimension(400, 40));
        card.setBackground(Color.gray);

        JLabel key = new JLabel("NAME");
        key.setPreferredSize(new Dimension(140, 40));
        key.setForeground(Color.decode("#fd6c35"));

        JLabel value = new JLabel("VALUE");
        value.setPreferredSize(new Dimension(140, 40));
        value.setForeground(Color.decode("#fd6c35"));

        card.add(key);
        card.add(value);

        right.getScrollList().add(card);

        for (Map.Entry<String , List<String>> i : request.getResponseHeaders().entrySet()) {
            setHeaders(i.getKey() , i.getValue());
        }

        right.setHeadersQuery(request.getResponseHeaders());
    }

    private void setHeaders(String HKey , List<String> HValue) {

        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT));
        card.setPreferredSize(new Dimension(400, 40));
        card.setBackground(Color.gray);
        JLabel key = new JLabel(HKey);
        key.setPreferredSize(new Dimension(145, 40));
        JLabel value = new JLabel(HValue.toString());
        value.setPreferredSize(new Dimension(155, 40));

        card.add(key);
        card.add(value);

        right.getScrollList().add(card);
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

    public void setRight(RequestPanel.Right right) {
        this.right = right;
    }
}
