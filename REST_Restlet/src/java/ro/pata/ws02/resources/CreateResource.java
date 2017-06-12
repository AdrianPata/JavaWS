/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02.resources;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

/**
 *
 * @author A4YZTZZ
 */
public class CreateResource extends ServerResource {
    @Post
    public Representation create(Representation data){
        Status status;
        String msg;
        Form form=new Form(data);
        String name=form.getFirstValue("name");
        String phone=form.getFirstValue("phone");
        if(name==null || phone==null){
            msg="<msg>Please provide name and phone.</msg>";
            status=Status.CLIENT_ERROR_BAD_REQUEST;
        } else {
            Agenda.getAgenda("xml").addPerson(name, phone);
            msg="<msg>Ok</msg>";
            status=Status.SUCCESS_OK;
        }
        setStatus(status);
        StringRepresentation rez=new StringRepresentation(msg,MediaType.TEXT_XML);
        return rez;
    }
}
