package com.company.model;

import com.company.view.Bridge;
import com.company.view.RequestPanel;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * HttpConnection with the following commands ::
 * "-M" , "--method" , "-S" , "--save" , "-O" , "--output" , "-H" , "--header" , "-d" , "--data" , "--help" , "-h" ,
 * "-i" , "-j" , "--json" , "--upload".
 *
 * can send request such as ::
 * GET, POST, DELETE, PUT, PATCH
 *
 * and also save request on a file
 *
 * use "jurl" in terminal to use HttpConnection
 *
 * some test websites ::
 * host : http://apapi.haditabatabaei.ir/tests/post/formdata
 * host : http://postman-echo.com - /post /get
 * host : https://webhook.site/86b18525-f014-4f8a-afc3-9ee66ebaa66e
 * host : https://httpbin.org - /get /post /delete /put
 */
public class HttpConnection extends Thread{

    public JPanel jPanel;

    public RequestPanel.Right right;

    public RequestPanel.Request request;

    private final String args;

    public boolean transferred = false;

    private static String URL_ADDRESS;

    // The User-Agent request header is a characteristic
    // string that lets servers and network peers identify
    // the application, operating system, vendor, and/or version
    // of the requesting user agent.
    private static final String USER_AGENT = "Mozilla/5.0";

    // Headers
    private static final HashMap<String , String> HEADERS = new HashMap<>();

    private static String PARAMS = "";

    private static Boolean OUTPUT = false;

    private static String fileName = "";

    private static boolean showHeaders = false;

    private static Boolean save = false;

    //wait until the connection is made and request is send
    //and HttpRequest receive the data
    private HttpRequest responseConn = null;

    public HttpConnection(String args) {
        this.args = args;
    }

    public HttpConnection() {
        Scanner sc = new Scanner(System.in);
        args = sc.nextLine();
    }

    @Override
    public void run() {
        try {
            getCommand(args);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void consoleGetCommand() throws IOException, InterruptedException {
        while (true) {
            System.out.print("$ ");
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            String[] commandArray = command.split(" ", -1);
            analyseCommands(commandArray);
        }
    }

    public void getCommand(String command) throws IOException, InterruptedException {
        String[] commandArray = command.split(" ", -1);
        analyseCommands(commandArray);
    }

    //TODO 2
    // console commands - extra
    // --json / -j
    // --upload
    private void analyseCommands(String[] array) throws IOException {
        // check if it is jURL command
        if (array[0].equals("exit")) System.exit(0);

        if (!array[0].equals("jurl")) {
            System.out.println(array[0] + " is not recognized as an internal or external command,\n" +
                    "operable program or batch file.");
            return;
        }

        // first input after jURL is url address
        if (array[1].equals("--help") || array[1].equals("-h")) { dashHelp(); return; }
        URL_ADDRESS = array[1];

        String[] commands = new String[]{"-M" , "--method" , "-S" , "--save" , "-O" , "--output" ,
                    "-H" , "--header" , "-d" , "--data" , "--help" , "-h" , "-i" , "-j" , "--json" , "--upload"};
        String[] methods = new String[]{"GET", "POST", "DELETE", "PUT", "PATCH"};
        // Default method
        String toDoMethod = "get";
        // all of possible methods
        ArrayList<String> possibleMethods = new ArrayList<>();
        Collections.addAll(possibleMethods , methods);
        // all of -<command> \ --<command>
        ArrayList<String> dashCommand = new ArrayList<>();
        Collections.addAll(dashCommand , commands);
        // Commands arrayList
        List<String> args = new ArrayList<>();
        Collections.addAll(args , array);
        args = args.subList(2 , args.size());

        System.out.println("jURL command\nURL_ADDRESS : " + URL_ADDRESS);
        // do the commands
        for (int i = 0 ; i < args.size() ; i++) {
            // check if it is a command or not
            if (args.get(i).indexOf('-') == 0 && !dashCommand.contains(args.get(i))) { // is command
                System.out.println(args.get(i) + " is not recognized as an jURL command");
                return;
            }

            // --method || -M
            // send a request of GET, POST, DELETE, PATCH, PUT
            if ( (args.get(i).equals("-M") || args.get(i).equals("--method")) && i < args.size() - 1) {
                System.out.println("method : [" + args.get(i + 1) + "]");
                if (possibleMethods.contains(args.get(i + 1).toUpperCase())) {
                    toDoMethod = args.get(i+1).toLowerCase();
                    i++;
                } else {
                    System.out.println("unsupported request");
                }
            }

            // -D || --data
            // message body
            if (args.get(i).equals("-d") || args.get(i).equals("--data")) {
                if (i < args.size() - 1) {
                    String prms = args.get(i+1);
                    if (prms.charAt(0) == '\"' && prms.charAt(prms.length() - 1) == '\"') {
                        prms = args.get(i + 1).substring(1, args.get(i + 1).length() - 1);
                    } else {
                        prms = args.get(i+1);
                    }

                    System.out.println("POST_PARAMS : " + prms);
                    PARAMS = prms;
                    i++;
                } else {
                    System.out.println("invalid data");
                }
            }

            // -H || --headers
            // headers
            if (args.get(i).equals("-H") || args.get(i).equals("--headers")) {
                if (i < args.size() - 1) {
                    String h = args.get(i+1);
                    if (h.charAt(0) == '\"' && h.charAt(h.length() - 1) == '\"') {
                        h = args.get(i+1).substring(1 , args.get(i+1).length() - 1);
                    } else {
                        h = args.get(i+1);
                    }

                    System.out.println("HEADERS : " + h);
                    divideHeaders(h);
                } else {
                    System.out.println("invalid headers");
                }
            }

            // -O || --output
            // save response body in a file
            if (args.get(i).equals("-O") || args.get(i).equals("--output")) {
                if (i < args.size() - 1 && args.get(i+1).indexOf('-') != 0) fileName = args.get(i+1);
                if (!fileName.equals("")) System.out.println("filename = " + fileName);
                OUTPUT = true;
            }

            // -help
            if (args.get(i).equals("--help") || args.get(i).equals("-h")) {
                dashHelp();
            }

            // -i
            // show headers
            if (args.get(i).equals("-i")) {
                showHeaders = true;
            }

            if (args.get(i).equals("--save")) {
                save = true;
            }
        }

        // do the method
        switch (toDoMethod) {
            case "get" -> sendGET();
            case "post" -> sendPOST();
            case "delete" -> sendDELETE();
            case "put" -> sendPUT();
            case "patch" -> sendPATCH();
        }
    }

    private static void printHeaders(HttpURLConnection httpCon) {
        Map<String, List<String>> hdrs = httpCon.getHeaderFields();
        Set<String> hdrKeys = hdrs.keySet();

        for (String k : hdrKeys)
            System.out.println("Key: " + k + "  Value: " + hdrs.get(k));
        showHeaders = false;
    }

    private static void dashHelp() {
        System.out.println("--method / -M \t choice one of following methods to send a request " +
                "GET, POST, DELETE, PUT, PATCH\n"
                + "--headers / -H \t \"header key -> header value\"\n"
                + "-help \t shows all of jURL commands\n"
                + "-i \t show headers in response or not\n"
                + "--output / -O <args> \t save response body in a file <args> fileName. if args = null then " +
                "filename " +
                "= output_[currentDate]\n"
                + "--save / -S \t save current request in a file\n"
                + "--data / -d \t data types \n" +
                "\"Content-Type=application/x-www-form-urlencoded\" \t " +
                "\"Content-Type=multipart/form-data\"\n"
                + "-j / --json \t message body as a json object\n"
                + "--upload <args>\t gets a absolute path and upload the file into that address\n" +
                "exit \t force exit the program");
    }

    private static void divideHeaders(String headers) {
        String[] prototype = headers.split(";" , -1);
        for (String i : prototype) {
            String[] map = i.split(":" , -1);
            HEADERS.put(map[0] , map[1]);
        }
    }

    private static void addHeadersToRequest(HttpURLConnection con) {
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true);
        con.setDoInput(true);
        for(Map.Entry<String, String> entry : HEADERS.entrySet()) {
            con.setRequestProperty(entry.getKey() , entry.getValue());
        }
    }

    private static void dashSave(HttpURLConnection conn, String response) {
        if (save) {
            try {
                new HttpRequest(conn, response).writeToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            save = false;
        }
    }

    private static void saveOUTPUT(String msgBody) {
        // concert body to array of characters
        char[] body = new char[msgBody.length()];

        // Copy character by character into array
        for (int i = 0; i < msgBody.length(); i++) {
            body[i] = msgBody.charAt(i);
        }

        try {
            String directory;
            if (!fileName.equals("")) directory = ".\\request\\" + fileName + ".txt";
            else {
                String date = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss'.tsv'").format(new Date());
                directory = ".\\request\\output_[" + date + "].txt";
            }
            System.out.println("mkdir " + directory);
            FileWriter out = new FileWriter(directory);
            for (int i = 0 ; i < body.length ; i++) {
                out.write(body[i]);
                if (i % 120 == 0 && i != 0)
                    out.write('\n');
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileName = "";
    }

    private static void bufferOutFormData(HashMap<String, String> body, String boundary,
                                    BufferedOutputStream bufferedOutputStream) throws IOException {
        if (body == null) return;
        for (String key : body.keySet()) {
            bufferedOutputStream.write(("--" + boundary + "\r\n").getBytes());
            if (key.contains("file")) {
                bufferedOutputStream.write(("Content-Disposition: form-data; filename=\"" + (new File(body.get(key))).getName() + "\"\r\nContent-Type: Auto\r\n\r\n").getBytes());
                try {
                    BufferedInputStream tempBufferedInputStream = new BufferedInputStream(new FileInputStream(new File(body.get(key))));
                    byte[] filesBytes = tempBufferedInputStream.readAllBytes();
                    bufferedOutputStream.write(filesBytes);
                    bufferedOutputStream.write("\r\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bufferedOutputStream.write(("Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n").getBytes());
                bufferedOutputStream.write((body.get(key) + "\r\n").getBytes());
            }
        }
        bufferedOutputStream.write(("--" + boundary + "--\r\n").getBytes());
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }

    private static HashMap<String , String> breakPARAMS() {
        if (PARAMS.equals("")) return null;
        HashMap<String , String> body = new HashMap<>();
        String[] split = PARAMS.split("&", -1);
        for (String s : split) {
            String[] params = s.split("=" , -1);
            body.put(params[0] , params[1]);
        }
        System.out.println(body);
        return body;
    }

    private void sendGET() throws IOException {
        long start = System.nanoTime();
        URL obj = new URL(URL_ADDRESS);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        // insert headers
        addHeadersToRequest(con);
        String boundary = System.currentTimeMillis() + "";
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();
        String responseMsg = con.getResponseMessage();
        System.out.println("GET Response Code :: " + responseCode + " " + responseMsg);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            if (response.toString().equals("")) System.out.println("no body for response\n");
            else System.out.println(response.toString());
            if (OUTPUT) {
                saveOUTPUT(response.toString());
                OUTPUT = false;
            }
        } else {
            System.out.println("GET request not worked");
        }

        if (showHeaders) {
            printHeaders(con);
        }

        // save to file
        dashSave(con , response.toString());
        responseConn = new HttpRequest(con , response.toString());
        transferred = true;
        Bridge br = new Bridge(responseConn , request , start);
        br.setRight(right);
        br.start();
    }

    private void sendPOST() throws IOException {
        long start = System.nanoTime();
        URL obj = new URL(URL_ADDRESS);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // insert headers
        addHeadersToRequest(con);
        String boundary = System.currentTimeMillis() + "";
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(con.getOutputStream());
        HashMap<String , String> body = breakPARAMS();
        bufferOutFormData(body , boundary , bufferedOutputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(con.getInputStream());
        System.out.println(new String(bufferedInputStream.readAllBytes()));

//        // For POST only - START
//        con.setDoOutput(true);
//        con.setDoInput(true);
//        OutputStream os = con.getOutputStream();
//        os.write(PARAMS.getBytes());
//        os.flush();
//        os.close();
//        // For POST only - END

        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();
        String responseMsg = con.getResponseMessage();
        System.out.println("POST Response Code :: " + responseCode + " " + responseMsg);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            if (response.toString().equals("")) System.out.println("no body for response\n");
            else System.out.println(response.toString());
            // -O
            if (OUTPUT) {
                saveOUTPUT(response.toString());
                OUTPUT = false;
            }
        } else {
            System.out.println("POST request not worked");
        }

        if (showHeaders) {
            printHeaders(con);
        }

        dashSave(con , response.toString());
        responseConn = new HttpRequest(con , response.toString());
        transferred = true;
        Bridge br = new Bridge(responseConn , request , start);
        br.setRight(right);
        br.start();
    }

    private void sendDELETE() throws IOException {
        long start = System.nanoTime();
        URL obj = new URL(URL_ADDRESS);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");
        con.setDoOutput(true);
        con.setDoInput(true);

        // insert headers
        addHeadersToRequest(con);
        // body
        String boundary = System.currentTimeMillis() + "";
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        if (con.getOutputStream() != null) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(con.getOutputStream());
            HashMap<String, String> body = breakPARAMS();
            bufferOutFormData(body, boundary, bufferedOutputStream);
        } else {
            System.out.println("no body for response");
        }

        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();
        String responseMsg = con.getResponseMessage();
        System.out.println("DELETE Response Code :: " + responseCode + " " + responseMsg);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            if (response.toString().equals("")) System.out.println("no body for response\n");
            else System.out.println(response.toString());
            if (OUTPUT) {
                saveOUTPUT(response.toString());
                OUTPUT = false;
            }
        } else {
            System.out.println("DELETE request not worked");
        }

        if (showHeaders) {
            printHeaders(con);
        }

        dashSave(con , response.toString());
        responseConn = new HttpRequest(con , response.toString());
        transferred = true;
        Bridge br = new Bridge(responseConn , request , start);
        br.setRight(right);
        br.start();
    }

    private void sendPUT() throws IOException{
        long start = System.nanoTime();
        URL obj = new URL(URL_ADDRESS);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("PUT");

        // insert headers
        addHeadersToRequest(con);
        String boundary = System.currentTimeMillis() + "";
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        if (con.getOutputStream() != null) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(con.getOutputStream());
            HashMap<String, String> body = breakPARAMS();
            bufferOutFormData(body, boundary, bufferedOutputStream);
        } else {
            System.out.println("no body for response");
        }

        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();
        String responseMsg = con.getResponseMessage();
        System.out.println("PUT Response Code :: " + responseCode + " " + responseMsg);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            if (response.toString().equals("")) System.out.println("no body for response\n");
            else System.out.println(response.toString());
            if (OUTPUT) {
                saveOUTPUT(response.toString());
                OUTPUT = false;
            }
        } else {
            System.out.println("PUT request not worked");
        }

        if (showHeaders) {
            printHeaders(con);
        }

        dashSave(con , response.toString());
        responseConn = new HttpRequest(con , response.toString());
        transferred = true;
        Bridge br = new Bridge(responseConn , request , start);
        br.setRight(right);
        br.start();
    }

    private void sendPATCH() throws IOException{
        long start = System.nanoTime();
        URL obj = new URL(URL_ADDRESS);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        con.setRequestMethod("POST");

        // insert headers
        addHeadersToRequest(con);
        String boundary = System.currentTimeMillis() + "";
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        if (con.getOutputStream() != null) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(con.getOutputStream());
            HashMap<String, String> body = breakPARAMS();
            bufferOutFormData(body, boundary, bufferedOutputStream);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(con.getInputStream());
            System.out.println(new String(bufferedInputStream.readAllBytes()));
        } else {
            System.out.println("no body for response");
        }

        int responseCode = con.getResponseCode();
        StringBuilder response = new StringBuilder();
        String responseMsg = con.getResponseMessage();
        System.out.println("PATCH Response Code :: " + responseCode + " " + responseMsg);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            if (response.toString().equals("")) System.out.println("no body for response\n");
            else System.out.println(response.toString());
            if (OUTPUT) {
                saveOUTPUT(response.toString());
                OUTPUT = false;
            }
        } else {
            System.out.println("PATCH request not worked");
        }

        if (showHeaders) {
            printHeaders(con);
        }

        dashSave(con , response.toString());
        responseConn = new HttpRequest(con , response.toString());
        transferred = true;
        Bridge br = new Bridge(responseConn , request , start);
        br.setRight(right);
        br.start();
    }

    public void setRight(RequestPanel.Right right) {
        this.right = right;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }
}