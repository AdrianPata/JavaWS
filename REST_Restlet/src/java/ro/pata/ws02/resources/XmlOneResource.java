/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02.resources;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 *
 * @author A4YZTZZ
 */
public class XmlOneResource extends ServerResource {
    
    @Get
    public Representation getXml(){
        String id=(String)getRequest().getAttributes().get("id");
        String xml=Agenda.getAgenda("xml").getPerson(Integer.parseInt(id));
        
        StringRepresentation rez=new StringRepresentation(xml,MediaType.TEXT_XML);
        
        return rez;
    }
}
