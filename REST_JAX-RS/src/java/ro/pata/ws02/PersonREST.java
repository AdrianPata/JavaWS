/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/xml")
    public String getAgendaXML(){
        loadAgenda();
        agenda.setAccept("xml");
        return agenda.getAgenda();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json")
    public String getAgendaJSON(){
        loadAgenda();
        agenda.setAccept("json");
        return agenda.getAgenda();
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/xml/{id: \\d+}")
    public Response getPerson(@PathParam("id") int id){
        loadAgenda();
        agenda.setAccept("xml");
        String p=agenda.getPerson(id);
        if(p!=null){
            return Response.ok(agenda.getPerson(id),MediaType.APPLICATION_XML).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("<Error>Person ID not found.</Error>").type(MediaType.TEXT_XML).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/json/{id: \\d+}")
    public Response getPersonJSON(@PathParam("id") int id){
        loadAgenda();
        agenda.setAccept("json");
        String p=agenda.getPerson(id);
        if(p!=null){
            return Response.ok(agenda.getPerson(id),MediaType.APPLICATION_JSON).build();
        }else{
            return Response.status(Response.Status.NOT_FOUND).entity("Person ID not found.").type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/add")
    public Response addPerson(@FormParam("name") String name,@FormParam("phone") String phone){
        loadAgenda();
        if(name==null || phone==null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Post name and phone.").type(MediaType.TEXT_PLAIN).build();
        }
        
        agenda.addPerson(name, phone);
        
        return Response.ok("Person added.",MediaType.TEXT_PLAIN).build();
    }
    
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/update")
    public Response updatePerson(@FormParam("id") Integer id,@FormParam("name") String name, @FormParam("phone") String phone){
        loadAgenda();
        if(id==null || name==null || phone==null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Post id, name and phone.").type(MediaType.TEXT_PLAIN).build();
        }
        
        agenda.editPerson(id, name, phone);
        
        return Response.ok("Person updated.",MediaType.TEXT_PLAIN).build();
    }
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/delete")
    public Response deletePerson(@FormParam("id") Integer id){
        loadAgenda();
        if(id==null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Post id.").type(MediaType.TEXT_PLAIN).build();
        }
        
        agenda.deletePerson(id);
        
        return Response.ok("Person deleted.",MediaType.TEXT_PLAIN).build();
    }
}
