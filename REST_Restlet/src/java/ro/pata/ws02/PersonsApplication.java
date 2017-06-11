/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import ro.pata.ws02.resources.XmlAllResource;

/**
 *
 * @author adi
 */
public class PersonsApplication extends Application{
    @Override
    public synchronized Restlet createInboundRoot(){
        Router router=new Router(getContext());
        router.attach("/xml",XmlAllResource.class);
        return router;
    }
}
