/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author 10051644
 */
public class FetchXML {
    public void setJson(String json) { }
    public String getJson() {
        JSONObject json = null;
        try {
            String xml = "";
            URL url = new URL("http://localhost:8080/REST_JAX-RS/resA/xml");
            URLConnection conn = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) xml += line;
            in.close();
            json = XML.toJSONObject(xml.toLowerCase());
        } catch (IOException | JSONException ex) {
            Logger.getLogger(FetchXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return json!=null?json.toString():"null";
    }
}
