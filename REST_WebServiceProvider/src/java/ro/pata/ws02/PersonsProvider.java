/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingType;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;

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
        
        switch (httpVerb) {
            case "GET":
                return doGet(mctx);
            case "POST":
                return doPost(request);
            default:
                throw new HTTPException(405); //bad verb
        }
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
    
    private Source doPost(Source request){
        InputSource in=toInputSource(request);
        String name=findElement("//name/text()",in);
        if(name==null) throw new HTTPException(400);
        return toSource("<msg>Name: "+name+"</msg>");
    }
    
    private StreamSource toSource(String str){
        return new StreamSource(new StringReader(str));
    }
    
    private InputSource toInputSource(Source source) {
        InputSource input = null;
        try {
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(bos);
            trans.transform(source, result);
            input = new InputSource(new ByteArrayInputStream(bos.toByteArray()));
        }
        catch(TransformerException e) { throw new HTTPException(500); } // internal server error
        return input;
    }    
    
    private String findElement(String expression, InputSource source) {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String retval = null;
        try {
            retval = (String) xpath.evaluate(expression, source, XPathConstants.STRING);
        }
        catch(XPathExpressionException e) { throw new HTTPException(400); } // bad request
        return retval;
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
