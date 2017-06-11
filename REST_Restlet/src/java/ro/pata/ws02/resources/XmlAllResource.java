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
 * @author adi
 */
public class XmlAllResource extends ServerResource {
    @Get
    public Representation toXml(){
        StringRepresentation ret=new StringRepresentation("<msg>Salut</msg>",MediaType.TEXT_XML);
        
        return ret;
    }
}
