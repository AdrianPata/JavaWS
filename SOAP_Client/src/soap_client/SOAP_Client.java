/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soap_client;

import client.RandService;
import client.RandServiceService;

/**
 *
 * @author 10051644
 */
public class SOAP_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandServiceService service = new RandServiceService();
        RandService port = service.getRandServicePort();
        System.out.println(port.next1());
    }
    
}
