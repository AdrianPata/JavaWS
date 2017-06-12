/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import javax.servlet.ServletContext;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import ro.pata.ws02.resources.Agenda;
import ro.pata.ws02.resources.CreateResource;
import ro.pata.ws02.resources.XmlAllResource;
import ro.pata.ws02.resources.XmlOneResource;

/**
 *
 * @author adi
 */
public class PersonsApplication extends Application{
    @Override
    public synchronized Restlet createInboundRoot(){
        ServletContext sc = (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext"); 
        Agenda.loadAgenda(sc);
        
        Router router=new Router(getContext());
        router.attach("/xml",XmlAllResource.class);
        router.attach("/xml/{id}",XmlOneResource.class);
        router.attach("/create",CreateResource.class);
        return router;
    }
}
