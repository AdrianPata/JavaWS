/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest_clientamazon;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author 10051644
 */
public class REST_ClientAmazon {

    private static final String endpoint = "ecs.amazonaws.com";
    private static final String itemId = "0545010225"; // Harry Potter
    
    public static void main(String[] args) {
        new REST_ClientAmazon().lookupStuff("AKIAI5MXAAOFHIOIO3YQ", "W5Wu0XoGzRSG3Vjzk5+LxF5lxQ38jUldXYBV0czz");
    }
    
    private void lookupStuff(String accessKeyId, String secretKey) {
        RequestHelper helper = new RequestHelper(endpoint, accessKeyId, secretKey);
        String requestUrl = null;
        String title = null;
        
        Map<String, String> params = new HashMap<>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2017-07-26");
        params.put("Operation", "ItemLookup");
        params.put("ItemId", itemId);
        params.put("ResponseGroup", "Small");
        params.put("AssociateTag", "kalin"); // any string should do
        
        requestUrl = helper.sign(params);
        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<note>\n" +
                "  <to>Tove</to>\n" +
                "  <from>Jani</from>\n" +
                "  <heading>Reminder</heading>\n" +
                "  <body>Don't forget me this weekend!</body>\n" +
                "</note>";
        System.out.println(getBody(xml));
    }
    
    private String requestAmazon(String stringUrl) {
        String response = null;
        try{
            URL url = new URL(stringUrl);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String chunk = null;
            while ((chunk = in.readLine()) != null) response += chunk;
            in.close();
        }catch(Exception e) { throw new RuntimeException("Arrrg! " + e); }
        return response;
    }
    
    private String getBody(String xml){
        String body=null;
        try{
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            fact.setNamespaceAware(true);
            DocumentBuilder builder = fact.newDocumentBuilder();
            Document doc = builder.parse(bais);
            NodeList nl=doc.getElementsByTagName("body");
            Node n=nl.item(0);
            body=n.getChildNodes().item(0).getNodeValue();
            /*
            NodeList results = doc.getElementsByTagName("note");
            for(int i=0;i<results.getLength();i++){
                Node n=results.item(i);
                body+=n.getNodeValue();
            }
            */
        }catch(Exception e) { throw new RuntimeException("Xml bad!", e); }
        return body;
    }
}
