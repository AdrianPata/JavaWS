/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest_jax.rs_client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author 10051644
 */
public class REST_JAXRS_Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new REST_JAXRS_Client().demo();
    }
    
    private void demo(){
        Client client = Client.create();
        client.setFollowRedirects(true); // in case the service redirects
        
        WebResource resource;
        String response;
        
        resource=client.resource("http://localhost:8080/REST_JAX-RS/resA/add");
        Form form = new Form(); // HTTP body, a simple hash
        form.add("name", "Coco Jambo Action Zone");
        form.add("phone", "3.1415");
        response = resource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).accept(MediaType.TEXT_PLAIN_TYPE).post(String.class, form);
        System.out.println(response);
        
        resource = client.resource("http://localhost:8080/REST_JAX-RS/resA/xml");
        response = resource.accept(MediaType.APPLICATION_XML_TYPE).get(String.class);
        System.out.println(response);
        
        resource=client.resource("http://localhost:8080/REST_JAX-RS/resA/delete");
        form = new Form();
        form.add("id", 4);
        response = resource.accept(MediaType.APPLICATION_XML_TYPE).accept(MediaType.TEXT_PLAIN_TYPE).delete(String.class,form);
        System.out.println(response);
        
    }
    
}
