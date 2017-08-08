/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap_random_console;

import javax.xml.ws.Endpoint;
import rand.RandService;

/**
 *
 * @author 10051644
 */
public class SOAP_Random_Console {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final String url = "http://localhost:8888/rs";
        System.out.println("Publishing RandService at endpoint " + url);
        Endpoint.publish(url, new RandService());
    }
    
}
