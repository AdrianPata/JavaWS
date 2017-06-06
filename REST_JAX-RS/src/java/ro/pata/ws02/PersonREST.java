/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Adrian
 */
@Path("/")
public class PersonREST {
    @Context ServletContext ctx;
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/xml")
    public String getTest(){
        return "<msg>Test ok</msg>";
    }
}
