/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Adrian
 */
@Path("/")
public class PersonREST {
    @Context ServletContext ctx;
    private static Persons agenda;
    
    private void loadAgenda(){
        if(agenda==null){
            System.out.println("******** INIT Agenda ****************");
            agenda=new Persons(ctx);
            agenda.setAccept("xml");
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/xml")
    public String getTest(){
        loadAgenda();
        return agenda.getAgenda();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/xml/{id: \\d+}")
    public Response getPerson(@PathParam("id") int id){
        //return Response.status(Response.Status.FORBIDDEN).type(MediaType.TEXT_PLAIN).build();
        return Response.ok("Salut "+id,MediaType.TEXT_PLAIN).build();
    }
}
