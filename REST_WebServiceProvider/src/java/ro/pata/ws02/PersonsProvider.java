/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import java.io.StringReader;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;

/**
 *
 * @author adi
 */
@WebServiceProvider
@ServiceMode(javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(HTTPBinding.HTTP_BINDING)
public class PersonsProvider implements Provider<Source>{
    @Resource
    protected WebServiceContext wctx;
    private static Persons agenda;
    
    public PersonsProvider(){
        
    }
    
    @Override
    public Source invoke(Source request){
        if(wctx==null) throw new RuntimeException("Injection failed on WebServiceContext.");        
        ServletContext servletContext = (ServletContext) wctx.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        MessageContext mctx=wctx.getMessageContext();
        String httpVerb=(String)mctx.get(MessageContext.HTTP_REQUEST_METHOD);
        httpVerb=httpVerb.trim().toUpperCase();
        
        if(agenda==null){
            agenda=new Persons(servletContext);
            agenda.setAccept("xml");
        }
        
        if(httpVerb.equals("GET")) return doGet(mctx);
        else throw new HTTPException(405); //bad verb
    }
    
    private Source doGet(MessageContext mctx){
        String qs=(String)mctx.get(MessageContext.QUERY_STRING);
        
        if(qs==null) {
            return toSource(agenda.getAgenda());
        } else {
            int id=getId(qs);
            return toSource(agenda.getPerson(id));
        }       
    }
    
    private StreamSource toSource(String str){
        return new StreamSource(new StringReader(str));
    }
    
    private int getId(String qs){
        int badId = -1; // bad ID
        String[ ] parts = qs.split("=");
        if (!parts[0].toLowerCase().trim().equals("id")) return badId;
        int goodId; // for now
        try {
            goodId = Integer.parseInt(parts[1].trim());
        }
        catch(NumberFormatException e) { return badId; }
        return goodId;        
    }
}
