package com.company.view;

import com.company.model.HttpConnection;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * 4 part JSplitScreen gui
 * each part(menuBar , left , center , right) have it unique function
 */
public class RequestPanel extends JSplitPane {
    private final JSplitPane splitPane;
    private final Right guiRightPanel = new Right();
    private final Center guiCenterPanel = new Center();
    private final Left guiLeftPanel = new Left();

    private static class Left{
        private JPanel list;
        public ArrayList<Request> requestList;
    }

    private static class Center{
        private JComboBox<String> box;
        private JTextField selectedUrl;
        private JTextArea requestName;
        private HashMap<String , String> headerQuery;
        private HashMap<String , String> bodyQuery;
        private JTabbedPane tb;
        private JPanel body;
        private JPanel header;
        private JPanel scrollList = new JPanel();
        private final JPanel bodyScrollList = new JPanel();

    }

    public static class Right{
        private JLabel status;
        private JLabel time;
        private JLabel contentLen;
        private JTextArea responseMsg;
        private JPanel responseHeader;
        private JPanel scrollList;
        private Map<String, List<String>> headersQuery;

        public JLabel getStatus() {
            return status;
        }

        public JTextArea getResponseMsg() {
            return responseMsg;
        }

        public JLabel getTime() {
            return time;
        }

        public JLabel getContentLen() {
            return contentLen;
        }

        public JPanel getScrollList() {
            return scrollList;
        }

        public void setHeadersQuery(Map<String, List<String>> headersQuery) {
            this.headersQuery = headersQuery;
        }
    }

    public RequestPanel() {

        File data = new File("data.txt");
        data.setWritable(true , true);

        JPanel left = new JPanel();
        JPanel center = new JPanel();
        JPanel right = new JPanel();

        createLeftPanel(left , center , right);
        createCenterPanel(left , center , right);

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT , left , center);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT , sp , right);
    }

    private void createLeftPanel(JPanel leftPanel, JPanel centerPanel , JPanel rightPanel) {

        guiLeftPanel.requestList = new ArrayList<>();

        JPanel mainList = new JPanel();

        mainList.setLayout(new BorderLayout());
        JPanel postman = new JPanel();
        //contains a button named postman
        //no actions
        JLabel pst = new JLabel("POSTMAN" , SwingConstants.CENTER);
        pst.setBackground(Color.decode("#fd6c35"));
        pst.setForeground(Color.white);
        pst.setOpaque(true);
        pst.setPreferredSize(new Dimension(160 , 40));

        JMenu btn = new JMenu("NEW...");
        btn.setOpaque(true);
        btn.setBackground(Color.white);
        btn.setForeground(Color.decode("#fd6c35"));
        btn.setPreferredSize(new Dimension(100 , 40));
        btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                ((JMenu)mouseEvent.getSource()).doClick();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        //menu items
        JMenuItem req = new JMenuItem("Request"); JMenuItem folder = new JMenuItem("Folder");
        //change req to JMenu and add action listener
        //add post get... JMenuItems
        req.setIconTextGap(15); folder.setIconTextGap(15);
        btn.add(req); btn.add(folder);

        postman.add(pst); postman.add(btn);

        mainList.add(postman , BorderLayout.NORTH);

        guiLeftPanel.list = new JPanel();
        guiLeftPanel.requestList = new ArrayList<>();
        guiLeftPanel.list.setLayout(new GridLayout(100 , 1 , 0 , 1));
        req.addActionListener(e -> createRequest(leftPanel , centerPanel , rightPanel));

        //LOAD DATA
        try {
            loadRequest(leftPanel , centerPanel , rightPanel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        guiLeftPanel.list.setOpaque(true);
        JScrollPane jScrollPane = new JScrollPane(guiLeftPanel.list);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setOpaque(true);
        jScrollPane.setVisible(true);
        jScrollPane.setPreferredSize(new Dimension(260 , 650));

        mainList.add(jScrollPane , BorderLayout.CENTER);

        leftPanel.setLayout(new BorderLayout());
        leftPanel.setOpaque(true);
        leftPanel.add(mainList , BorderLayout.NORTH);
    }

    private void createCenterPanel(JPanel leftPanel , JPanel centerPanel , JPanel rightPanel) {

        JPanel mainList = new JPanel();
        mainList.setLayout(new BorderLayout());
        guiCenterPanel.scrollList = new JPanel();
        guiCenterPanel.headerQuery = new HashMap<>();
        guiCenterPanel.bodyQuery = new HashMap<>();

        JPanel north = new JPanel();

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.setPreferredSize(new Dimension(600 , 30));

        JButton deleteRequest = new JButton("DELETE");
        deleteRequest.setBackground(Color.white);
        deleteRequest.setForeground(Color.decode("#fd6c35"));
        deleteRequest.setPreferredSize(new Dimension(150 , 40));
        deleteRequest.addActionListener(actionEvent -> {
            deleteRequest(centerPanel , rightPanel , indexOfRequest(guiCenterPanel.requestName.getText()));
            leftPanel.updateUI();
            centerPanel.updateUI();
        });

        guiCenterPanel.requestName = new JTextArea("My request");
        guiCenterPanel.requestName.setEditable(false);
        guiCenterPanel.requestName.setForeground(Color.decode("#fd6c35"));
        guiCenterPanel.requestName.setPreferredSize(new Dimension(450 , 40));
        guiCenterPanel.requestName.setFont(new Font("Serif" , Font.BOLD , 24));
        top.add(guiCenterPanel.requestName , BorderLayout.WEST); top.add(deleteRequest , BorderLayout.CENTER);
        north.add(top);
        north.setBackground(Color.white);
        north.setPreferredSize(new Dimension(600 , 40));

        mainList.add(north , BorderLayout.NORTH);

        JPanel center = new JPanel();

        String[] methods = new String[]{"GET", "POST" , "DELETE" , "PUT" , "PATCH"};
        guiCenterPanel.box = new JComboBox<>(methods);
        guiCenterPanel.box.setBackground(Color.decode("#fd6c35"));
        guiCenterPanel.box.setForeground(Color.white);
        guiCenterPanel.box.setPreferredSize(new Dimension(100 , 40));
        //add comboBox actionListener
        //...

        guiCenterPanel.selectedUrl = new JTextField("URL : HTTP://EXAMPLE.COM");
        guiCenterPanel.selectedUrl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                guiCenterPanel.selectedUrl.setText("");
            }
        });
        guiCenterPanel.selectedUrl.setPreferredSize(new Dimension(350 , 40));
        guiCenterPanel.selectedUrl.setOpaque(true);

        JButton send = new JButton("SEND");
        send.addActionListener(actionEvent -> {
            createRightPanel(rightPanel);
            rightPanel.updateUI();
            try {
                getResponse(rightPanel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        JButton save = new JButton("SAVE");
        send.setBackground(Color.white);
        send.setForeground(Color.decode("#fd6c35"));
        send.setPreferredSize(new Dimension(70 , 40));
        save.setBackground(Color.white);
        save.setForeground(Color.decode("#fd6c35"));
        save.setPreferredSize(new Dimension(70 , 40));

        save.addActionListener(actionEvent -> save());

        center.add(guiCenterPanel.box); center.add(guiCenterPanel.selectedUrl); center.add(send); center.add(save);
        center.setOpaque(true);

        mainList.add(center , BorderLayout.CENTER);
        //body and header

        guiCenterPanel.tb = new JTabbedPane();
        guiCenterPanel.body = new JPanel();
        guiCenterPanel.body.setLayout(new BorderLayout());

        guiCenterPanel.header = new JPanel();
        guiCenterPanel.header.setLayout(new BorderLayout());

        guiCenterPanel.tb.add("BODY" , guiCenterPanel.body);
        guiCenterPanel.tb.add("HEADER" , guiCenterPanel.header);
        guiCenterPanel.tb.setOpaque(true);
        //header
        JPanel tbHeader = new JPanel();
        tbHeader.setLayout(new BorderLayout());
        guiCenterPanel.scrollList.setLayout(new GridLayout(100 , 1 , 0 , 1));
        //new key button
        JButton add = new JButton("ADD NEW KEY");
        add.setForeground(Color.decode("#fd6c35"));
        add.addActionListener(actionEvent -> addData(centerPanel , "header"));
        tbHeader.setOpaque(true);

        JScrollPane jScrollPane = new JScrollPane(guiCenterPanel.scrollList);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setOpaque(true);
        jScrollPane.setVisible(true);
        jScrollPane.setPreferredSize(new Dimension(260 , 500));

        tbHeader.add(jScrollPane);

        guiCenterPanel.header.add(jScrollPane , BorderLayout.NORTH);
        guiCenterPanel.header.add(add , BorderLayout.SOUTH);
        guiCenterPanel.header.add(tbHeader);

        //body
        JPanel tbBody = new JPanel();
        tbBody.setLayout(new BorderLayout());
        guiCenterPanel.bodyScrollList.setLayout(new GridLayout(100 , 1 , 0 , 1));
        //new key button
        JButton addBody = new JButton("ADD NEW KEY");
        addBody.setForeground(Color.decode("#fd6c35"));
        addBody.addActionListener(actionEvent -> addData(centerPanel , "body"));
        tbBody.setOpaque(true);

        JScrollPane jsp = new JScrollPane(guiCenterPanel.bodyScrollList);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setOpaque(true);
        jsp.setVisible(true);
        jsp.setPreferredSize(new Dimension(260 , 500));
        guiCenterPanel.body.add(jsp , BorderLayout.NORTH);
        guiCenterPanel.body.add(addBody , BorderLayout.SOUTH);
        guiCenterPanel.body.add(tbBody);

        guiCenterPanel.tb.setPreferredSize(new Dimension(600 , 550));

        mainList.add(guiCenterPanel.tb , BorderLayout.SOUTH);

        centerPanel.add(mainList);
    }

    private void createRightPanel(JPanel rightPanel) {
        resetRight(rightPanel);
        JPanel mainList = new JPanel();
        mainList.setLayout(new BorderLayout());

        JPanel north = new JPanel();

        north.setBackground(Color.white);
        guiRightPanel.status = new JLabel("", SwingConstants.CENTER);
        guiRightPanel.status.setForeground(Color.green);
        guiRightPanel.status.setOpaque(true);
        guiRightPanel.status.setPreferredSize(new Dimension(80 , 30));

        guiRightPanel.time = new JLabel("", SwingConstants.CENTER);
        guiRightPanel.time.setForeground(Color.decode("#fd6c35"));
        guiRightPanel.time.setOpaque(true);
        guiRightPanel.time.setPreferredSize(new Dimension(80 , 30));


        guiRightPanel.contentLen = new JLabel("", SwingConstants.CENTER);
        guiRightPanel.contentLen.setForeground(Color.decode("#fd6c35"));
        guiRightPanel.contentLen.setOpaque(true);
        guiRightPanel.contentLen.setPreferredSize(new Dimension(80 , 30));


        north.add(guiRightPanel.status); north.add(guiRightPanel.time); north.add(guiRightPanel.contentLen);

        north.setPreferredSize(new Dimension(300 , 40));

        mainList.add(north , BorderLayout.NORTH);
        //body and header

        JTabbedPane tb = new JTabbedPane();
        JPanel raw = new JPanel();
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        tb.add("RAW DATA" , raw);
        tb.add("HEADER" , header);
        tb.setPreferredSize(new Dimension(350 , 600));

        JPanel msgb = new JPanel(); //massage body
        guiRightPanel.responseMsg = new JTextArea();

        JScrollPane scroll = new JScrollPane(msgb);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setOpaque(true);
        scroll.setVisible(true);
        scroll.setPreferredSize(new Dimension(350 , 600));

        guiRightPanel.responseMsg.setPreferredSize(new Dimension(350 , 600));
        guiRightPanel.responseMsg.setEditable(false);
        msgb.add(guiRightPanel.responseMsg);
        String txt = "";
        guiRightPanel.responseMsg.setText(txt);
        raw.add(scroll);

        //header
        JPanel tbHeader = new JPanel();
        tbHeader.setLayout(new BorderLayout());
        guiRightPanel.scrollList = new JPanel();
        guiRightPanel.scrollList.setLayout(new GridLayout(100 , 1 , 0 , 1));

        JScrollPane jScrollPane = new JScrollPane(guiRightPanel.scrollList);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setOpaque(true);
        jScrollPane.setVisible(true);
        jScrollPane.setPreferredSize(new Dimension(350 , 600));

        tbHeader.add(jScrollPane);

        guiRightPanel.responseHeader = new JPanel();
        guiRightPanel.responseHeader.add(jScrollPane , BorderLayout.NORTH);
        guiRightPanel.responseHeader.add(tbHeader);

        header.add(guiRightPanel.responseHeader);
        mainList.add(tb , BorderLayout.SOUTH);

        //copy to clipboard
        JButton ccpy = new JButton("COPY TO CLIPBOARD");
        ccpy.setForeground(Color.decode("#fd6c35"));
        ccpy.setBackground(Color.white);
        ccpy.setPreferredSize(new Dimension(300 , 40));
        ccpy.addActionListener(actionEvent -> {
            String ctc = clipBoard();
            StringSelection stringSelection = new StringSelection(ctc);
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);
        });

        header.add(ccpy , BorderLayout.SOUTH);

        rightPanel.add(mainList);
    }

    /**
     * copy headers into clipboard
     * @return Request Headers
     */
    private String clipBoard() {
        String cb = "";
        for (Map.Entry<String , List<String>> i : guiRightPanel.headersQuery.entrySet())
            cb = cb.concat("Key :: " + i.getKey() + " - Value :: " + i.getValue() + "\n");
        return cb;
    }

    /**
     * reset and remove right panel after choosing another request
     * @param right right panel
     */
    private void resetRight(JPanel right) {
        right.removeAll();
        right.updateUI();
    }

    /**
     * find index of a given request
     * request names are uniq
     * if there is no request with given name return -1
     * @param requestName ```String``` name of request
     * @return index
     */
    private int indexOfRequest(String requestName) {
        for (Request i : guiLeftPanel.requestList) {
            if (i.name.equals(requestName)) return guiLeftPanel.requestList.indexOf(i);
        }

        return -1;
    }

    /**
     * add headers stored in query to its obj(request)
     * @param index request index
     */
    private void addDataToRequest(int index) {
        guiLeftPanel.requestList.get(index).headers.clear();
        guiLeftPanel.requestList.get(index).body.clear();

        for (Map.Entry<String , String> i : guiCenterPanel.headerQuery.entrySet()) {
            guiLeftPanel.requestList.get(index).headers.put(i.getKey() , i.getValue());
        }

        for (Map.Entry<String , String> i : guiCenterPanel.bodyQuery.entrySet()) {
            guiLeftPanel.requestList.get(index).body.put(i.getKey() , i.getValue());
        }
    }

    /**
     * find index of a given header key
     *
     * @param key header key
     * @return index of given key
     */
    private int findData(String key , String type) {
        int index = 0;
        if(type.equals("header")) {
            for (String i : guiCenterPanel.headerQuery.keySet()) {
                if (i.equals(key)) return ++index;
            }
        } else {
            for (String i : guiCenterPanel.bodyQuery.keySet()) {
                if (i.equals(key)) return ++index;
            }
        }
        return -1;
    }

    /**
     * takes center panel and add new key->value(map) to header tabbed pane
     * @param centerPanel centerPanel of application
     */
    private void addData(JPanel centerPanel , String type) {
        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT));
        card.setPreferredSize(new Dimension(400 , 40));
        card.setBackground(Color.gray);
        JLabel key = new JLabel("Key : ");
        key.setPreferredSize(new Dimension(80 , 40));
        JLabel value = new JLabel("Value : ");
        value.setPreferredSize(new Dimension(80 , 40));

        JFrame f = new JFrame();
        final String[] kt= {JOptionPane.showInputDialog(f,"Enter header" , "key")};
        final String[] vt = {JOptionPane.showInputDialog(f, "Enter value", "value")};
        if (findData(kt[0] , type) == -1) {
            addDataToFrame(centerPanel, type, card, key, value, kt, vt);
        } else
            showMessageDialog(null, "this header already exist");
    }

    private void addDataToFrame(JPanel centerPanel, String type, JPanel card, JLabel key, JLabel value, String[] kt, String[] vt) {
        JTextArea keyTxt = new JTextArea(kt[0]);
        keyTxt.setPreferredSize(new Dimension(130, 20));
        keyTxt.setBackground(Color.WHITE);
        keyTxt.setForeground(Color.decode("#fd6c35"));

        JTextArea valueTxt = new JTextArea(vt[0]);
        valueTxt.setPreferredSize(new Dimension(130, 20));
        valueTxt.setBackground(Color.WHITE);
        valueTxt.setForeground(Color.decode("#fd6c35"));

        valueTxt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                vt[0] = valueTxt.getText();
                if (type.equals("header")) guiCenterPanel.headerQuery.replace(kt[0], vt[0]);
                else guiCenterPanel.bodyQuery.replace(kt[0], vt[0]);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }
        });

        keyTxt.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if (type.equals("header")) guiCenterPanel.headerQuery.remove(kt[0]);
                else guiCenterPanel.bodyQuery.remove(kt[0]);
                kt[0] = keyTxt.getText();
                if (type.equals("header")) guiCenterPanel.headerQuery.put(kt[0], vt[0]);
                else guiCenterPanel.bodyQuery.put(kt[0], vt[0]);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }
        });

        if (type.equals("header")) guiCenterPanel.headerQuery.put(kt[0], vt[0]);
        else guiCenterPanel.bodyQuery.put(kt[0], vt[0]);

        JCheckBox isActive = new JCheckBox();
        isActive.setBackground(Color.gray);
        isActive.setForeground(Color.decode("#fd6c35"));
        JButton delete = new JButton("delete");
        delete.setBackground(Color.gray);
        delete.setForeground(Color.decode("#fd6c35"));
        delete.addActionListener(actionEvent1 -> {
            if (type.equals("header")) guiCenterPanel.scrollList.remove(card);
            else guiCenterPanel.bodyScrollList.remove(card);
            validate();
            repaint();
            centerPanel.updateUI();

            //delete from headerQuery
            //delete requestHeader
            //...
            if (type.equals("header")) guiCenterPanel.headerQuery.remove(kt[0], vt[0]);
            else guiCenterPanel.bodyQuery.remove(kt[0], vt[0]);
        });

        card.add(key);
        card.add(keyTxt);
        card.add(value);
        card.add(valueTxt);
        card.add(isActive);
        card.add(delete);

        if (type.equals("header")) guiCenterPanel.scrollList.add(card);
        else guiCenterPanel.bodyScrollList.add(card);
        validate();
        repaint();
        centerPanel.updateUI();
    }

    /**
     * @overload
     * overload of addData() method
     * load header Map from request to header and show key->values
     *
     * @param centerPanel centerPanel of application
     * @param HKey header key
     * @param HValue header value
     */
    private void loadHeaders(JPanel centerPanel , String HKey , String HValue , String type) {
        String[] k = {HKey};
        String[] v = {HValue};

        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT));
        card.setPreferredSize(new Dimension(400, 40));
        card.setBackground(Color.gray);
        JLabel key = new JLabel("Key : ");
        key.setPreferredSize(new Dimension(80, 40));
        JLabel value = new JLabel("Value : ");
        value.setPreferredSize(new Dimension(80, 40));

        addDataToFrame(centerPanel, type, card, key, value, k, v);
    }

    /**
     * refresh action listeners for request ```JButtons```
     * @param left leftPanel
     * @param center centerPanel
     * @param items request items
     */
    private void refresh(JPanel left , JPanel center , JPanel rightPanel , ArrayList<Request> items) {
        for (int i = 0 ; i < items.size() ; i++) {

            int finalI = i;
            left.getComponent(finalI).addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    guiCenterPanel.box.setSelectedItem(items.get(finalI).method);
                    guiCenterPanel.selectedUrl.setText(items.get(finalI).url);
                    guiCenterPanel.requestName.setText(items.get(finalI).name);
                    guiCenterPanel.scrollList.removeAll();
                    guiCenterPanel.headerQuery.clear();
                    guiCenterPanel.bodyScrollList.removeAll();
                    guiCenterPanel.bodyQuery.clear();

                    for (Map.Entry<String , String> i : items.get(finalI).headers.entrySet()) {
                        loadHeaders(center , i.getKey() , i.getValue() , "header");
                    }

                    for (Map.Entry<String , String> i : items.get(finalI).body.entrySet()) {
                        loadHeaders(center , i.getKey() , i.getValue() , "body");
                    }

                    if (items.get(finalI).statusCode != -1) {
                        createRightPanel(rightPanel);
                        guiRightPanel.status.setText(items.get(finalI).statusCode + " " + items.get(finalI).statusMassage);
                        guiRightPanel.time.setText(String.valueOf(items.get(finalI).time));
                        if (items.get(finalI).contentLen >= 0) guiRightPanel.contentLen.setText(String.valueOf(items.get(finalI).contentLen) + " bytes");
                        else guiRightPanel.contentLen.setText("error!");
                        guiRightPanel.responseMsg.setText(createResponse(items.get(finalI).response));
                        allHeaders(items.get(finalI));
                    }
                }
            });
        }
        //insertAllDataToFile();
        //I cant remember why the following function used to work
        //and now its not working
    }

    /**
     * insert all headers from response into headers tap
     * @param request request which headers are going to be shown
     */
    private void allHeaders(Request request) {
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

        guiRightPanel.getScrollList().add(card);

        for (Map.Entry<String , List<String>> i : request.getResponseHeaders().entrySet()) {
            setHeaders(i.getKey() , i.getValue());
        }

        guiRightPanel.setHeadersQuery(request.getResponseHeaders());
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

        guiRightPanel.getScrollList().add(card);
    }

    /**
     * takes the response String and change its form.
     * @param msgBody massage body
     * @return new massage body form
     */
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

    /**
     * save request data into request obj
     */
    private void save(){
        //if there is no more request
        if (guiLeftPanel.requestList.size() <= 0) {
            try {
                FileWriter fileWriter = new FileWriter("data.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int i;
        if (indexOfRequest(guiCenterPanel.requestName.getText()) < 0) return;
        i = indexOfRequest(guiCenterPanel.requestName.getText());
        guiLeftPanel.requestList.get(i).setUrl(guiCenterPanel.selectedUrl.getText());
        guiLeftPanel.requestList.get(i).setMethod(String.valueOf(guiCenterPanel.box.getSelectedItem()));
        guiLeftPanel.requestList.get(i).jButton.setText("[" + guiLeftPanel.requestList.get(i).method
                + "]     " + guiLeftPanel.requestList.get(i).name);
        addDataToRequest(i);
        insertAllDataToFile();
    }

    /**
     * save data of request such as
     * name, method, url, headers
     * into a .txt file
     */
    private void insertAllDataToFile(){
        try {
            FileWriter dataWriter = new FileWriter("data.txt");

            for (int i = 0 ; i < guiLeftPanel.requestList.size() ; i++) {
                dataWriter.write(guiLeftPanel.requestList.get(i).toString() + "\n");
            }

            dataWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * delete a given request from request list
     * @param centerPanel gui center panel
     * @param index index of request
     */
    private void deleteRequest(JPanel centerPanel , JPanel rightPanel , int index) {
        guiLeftPanel.requestList.remove(index);
        guiLeftPanel.list.removeAll();
        for (Request i : guiLeftPanel.requestList) {
            guiLeftPanel.list.add(i.getJButton());
        }
        resetCenter(centerPanel);
        refresh(guiLeftPanel.list , centerPanel , rightPanel , guiLeftPanel.requestList);
        resetRight(rightPanel);
    }

    /**
     * reset gui center panel after deleting a request
     * @param centerPanel guiCenterPanel
     */
    private void resetCenter(JPanel centerPanel) {
        guiCenterPanel.requestName.setText("");
        guiCenterPanel.selectedUrl.setText("");
        guiCenterPanel.headerQuery.clear();
        guiCenterPanel.scrollList.removeAll();
        guiCenterPanel.bodyQuery.clear();
        guiCenterPanel.bodyScrollList.removeAll();
        centerPanel.updateUI();
    }

    /**
     * create new request
     * @param leftPanel guiLeftPanel
     * @param centerPanel guiCenterPanel
     */
    private void createRequest(JPanel leftPanel , JPanel centerPanel , JPanel rightPanel) {
        //add new JButtons to list
        JFrame f = new JFrame();
        String name=JOptionPane.showInputDialog(f,"Enter Name" , "My request");
        String method=JOptionPane.showInputDialog(f,"Enter method" , "GET");
        Request m = new Request(method, name , rightPanel);

        if (indexOfRequest(name) ==  -1) {
            guiLeftPanel.list.add(m.getJButton());
            guiLeftPanel.requestList.add(m);
            refresh(guiLeftPanel.list , centerPanel , rightPanel , guiLeftPanel.requestList);
        } else
            showMessageDialog(null, "this request already exist");


        validate();
        repaint();
        leftPanel.updateUI();
    }

    /**
     * load a request from stored data in .txt file
     * @param leftPanel leftCenterPanel
     * @param centerPanel guiCenterPanel
     * @throws FileNotFoundException file not found error
     */
    private void loadRequest(JPanel leftPanel , JPanel centerPanel , JPanel rightPanel) throws FileNotFoundException {
        //load from data.txt
        // name
        // url
        // method
        // headers
        // body
        File fileReader = new File("data.txt");
        Scanner sc = new Scanner(fileReader);
        //insert data
        String name , method , url;
        HashMap<String , String> map = new HashMap<>();
        Request m;

        if (fileReader.length() == 0) return;
        while (sc.hasNextLine()) {
            name = sc.nextLine();
            url = sc.nextLine();
            method = sc.nextLine();
            m = new Request(method , name , rightPanel);
            if (!url.equals("null")) m.setUrl(url);
            else m.setUrl("");

            String str = sc.nextLine();
            if (!str.equals("null")) {
                String[] split = str.split(";" , -1);
                for (String s : split) {
                    String[] headers = s.split(":" , -1);
                    map.put(headers[0] , headers[1]);
                }
                m.headers = new HashMap<>(map);
            }

            map.clear();
            str = sc.nextLine();
            if (!str.equals("null")) {
                String[] split = str.split("&" , -1);
                for (String s : split) {
                    String[] body = s.split("=" , -1);
                    map.put(body[0] , body[1]);
                }
                m.body = new HashMap<>(map);
            }

            // status code
            // status massage
            // time
            // size
            // headers
            // response body
            m.statusCode = Integer.parseInt(sc.nextLine());
            m.statusMassage = sc.nextLine();
            m.time = Integer.parseInt(sc.nextLine());
            m.contentLen = Integer.parseInt(sc.nextLine());

            map.clear();
            HashMap<String , List<String>> responseMap = new HashMap<>();
            str = sc.nextLine();
            if (!str.equals("null")) {
                String[] split = str.split("&&&" , -1);
                for (String s : split) {
                    String[] body = s.split("===" , -1);
                    responseMap.put(body[0] , Collections.singletonList(body[1]));
                }
                m.responseHeaders = new HashMap<>(responseMap);
            }

            str = sc.nextLine();
            if (!str.equals("null")) {
                m.response = str;
            } else m.response = null;

            guiLeftPanel.list.add(m.getJButton());
            guiLeftPanel.requestList.add(m);
            refresh(guiLeftPanel.list , centerPanel , rightPanel , guiLeftPanel.requestList);

            validate();
            repaint();
            leftPanel.updateUI();
        }
        sc.close();
    }

    /**
     * send request as a Thread and wait for the answer.
     * @param right right panel
     */
    private void getResponse(JPanel right) {
        String url = guiCenterPanel.selectedUrl.getText();
        String method = Objects.requireNonNull(guiCenterPanel.box.getSelectedItem()).toString();
        String params = createPARAMS();
        String headers = createHEADERS();
        
        String args = null;
        if (!url.equals("")) {
            args = "jurl " + url + " -M " + method;
            if (params != null) args = args.concat(" -d " + params);
            if (headers != null) args = args.concat(" -H " + headers);
        }

        HttpConnection conn = new HttpConnection(args);
        conn.request = guiLeftPanel.requestList.get(indexOfRequest(guiCenterPanel.requestName.getText()));
        conn.start();
        conn.setRight(guiRightPanel);
        conn.setjPanel(right);
    }

    /**
     * create params from center panel/body and convert it to -d <args> command
     * @return PARAMS
     */
    private String createPARAMS() {
        String params = "";

        params = params.concat("\"");
        int counter = 0 , size = guiCenterPanel.bodyQuery.size() - 1;
        for (Map.Entry<String , String> i : guiCenterPanel.bodyQuery.entrySet()) {
            params = params.concat(i.getKey() + "=" + i.getValue());
            if (counter < size) params = params.concat("&");
            counter++;
        }
        params = params.concat("\"");
        if (params.equals("\"\"")) return null;
        return params;
    }

    /**
     * create params from center panel/header and convert it to -d <args> command
     * @return HEADERS
     */
    private String createHEADERS() {
        String header = "";

        header = header.concat("\"");
        int counter = 0 , size = guiCenterPanel.headerQuery.size() - 1;
        for (Map.Entry<String , String> i : guiCenterPanel.headerQuery.entrySet()) {
            header = header.concat(i.getKey() + ":" + i.getValue());
            if (counter < size) header = header.concat(";");
            counter++;
        }
        header = header.concat("\"");
        if (header.equals("\"\"")) return null;
        return header;
    }

    /**
     * obj of buttons for new Request and storing request
     */
    public class Request extends JButton{

        //request
        private final JButton jButton;
        private String url;
        private String method;
        private final String name;
        private HashMap<String , String> headers;
        private HashMap<String , String> body;

        //response
        private int statusCode = -1;
        private String statusMassage = null;
        private int time = -1;
        private int contentLen = -1;
        private String response = null;
        private Map<String, List<String>> responseHeaders = null;

        public Request(String method , String name , JPanel rightPanel) {

            headers = new HashMap<>();
            body = new HashMap<>();
            this.name = name;
            setMethod(method);
            setUrl("");
            jButton = new JButton("[" + method + "]     " + name);
            jButton.setBackground(Color.gray);
            jButton.setForeground(Color.decode("#fd6c35"));
            jButton.setMinimumSize(new Dimension(260 , 40));
            jButton.addActionListener(e -> resetRight(rightPanel));
        }

        public String toString(){
            String str;
            str = name + "\n";
            str = str.concat(url + "\n");
            str = str.concat(method + "\n");

            if (headers.size() != 0) {
                int index;
                index = 0;
                for (Map.Entry<String, String> map : headers.entrySet()) {
                    str = str.concat(map.getKey() + ":" + map.getValue());
                    if (index < headers.size() - 1) str = str.concat(";");
                    index++;
                }
            } else str = str.concat("null");

            str = str.concat("\n");
            if (body.size() != 0) {
                int index;
                index = 0;
                for (Map.Entry<String, String> map : body.entrySet()) {
                    str = str.concat(map.getKey() + "=" + map.getValue());
                    if (index < body.size() - 1) str = str.concat("&");
                    index++;
                }
            } else str = str.concat("null");
            str = str.concat("\n");

            //response
            str = str.concat(statusCode + "\n");
            str = str.concat(statusMassage + "\n");
            str = str.concat(time + "\n");
            str = str.concat(contentLen + "\n");

            if (responseHeaders != null) {
                int index = 0;
                for (Map.Entry<String, List<String>> i : responseHeaders.entrySet()) {
                    str = str.concat(i.getKey() + "===" + i.getValue());
                    if (index < responseHeaders.size() - 1) str = str.concat("&&&");
                    index++;

                }
                str = str.concat("\n");
            } else str = str.concat("null\n");

            if (!(response == null)) str = str.concat(response.replace("\n" , ""));
            else str = str.concat("null");

            return str;
        }

//        public String responseToString() {
//            String str = "";
//            str = str.concat(statusCode + "\n");
//            str = str.concat(statusMassage + "\n");
//            str = str.concat(time + "\n");
//            str = str.concat(contentLen + "\n");
//
//            if (statusCode == HttpURLConnection.HTTP_OK) str = str.concat(response + "\n");
//            else str = str.concat("null\n");
//
//            if (responseHeaders.size()!=0) str = str.concat(responseHeaders.toString() + "\n");
//            else str = str.concat("null\n");
//            return str;
//        }

        @Override
        public String getName() {
            return name;
        }

        public JButton getJButton() {
            return jButton;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusMassage() {
            return statusMassage;
        }

        public String getResponse() {
            return response;
        }

        public Map<String, List<String>> getResponseHeaders() {
            return responseHeaders;
        }

        public int getContentLen() {
            return contentLen;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public void setStatusMassage(String statusMassage) {
            this.statusMassage = statusMassage;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
            this.responseHeaders = responseHeaders;
        }

        public void setContentLen(int contentLen) {
            this.contentLen = contentLen;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }
}