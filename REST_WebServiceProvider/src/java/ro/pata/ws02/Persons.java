/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author adi
 */

@XmlRootElement(name="Persons")
@XmlAccessorType(XmlAccessType.NONE)
public class Persons {
    private AtomicInteger idIdx;
    @XmlElementWrapper(name="agenda")
    @XmlElement(name="person")
    private final CopyOnWriteArrayList<Person> agenda;    
    @XmlElement(name="type")
    private String accept;
    private ServletContext sctx;
    
    public Persons(){
        this.idIdx = new AtomicInteger(0);
        this.agenda = new CopyOnWriteArrayList<>();        
    }
    
    public Persons(ServletContext ctx){
        this.idIdx = new AtomicInteger(0);
        this.agenda = new CopyOnWriteArrayList<>();        
        this.sctx=ctx;        
        loadAgenda();
    }

    public void setAccept(String c){
        this.accept=c;
    }
    
    public String getAgenda(){
        if(agenda.isEmpty())loadAgenda();
        
        if(!this.accept.toLowerCase().contains("json")){
            return getXMLAgenda();
        } else {
            return getJSONAgenda();
        }
    }
    
    public String getPerson(int id){
        if(agenda.isEmpty())loadAgenda();
        
        if(!this.accept.toLowerCase().contains("json")){
            return getXMLPerson(id);
        } else {
            return getJSONPerson(id);
        }
    }
    
    private String getXMLAgenda(){
        String xml="N/A";

        try {
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            JAXBContext jaxbCtx=JAXBContext.newInstance(Persons.class);
            Marshaller mrsh=jaxbCtx.createMarshaller();
            mrsh.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            mrsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mrsh.marshal(this, os);
            xml=os.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(Persons.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return xml;
    }
    
    private String getJSONAgenda(){
        String rez="N/A";
        try {
            JSONObject json=XML.toJSONObject(getXMLAgenda());
            rez=json.toString(3);
        } catch (JSONException ex) {
            Logger.getLogger(Persons.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rez;
    }
    
    private String getXMLPerson(int id){
        Person p=new Person(id);
        String rez=null;
        int pos;
        if((pos=agenda.indexOf(p))>=0){
            p=agenda.get(pos);
            try{
                ByteArrayOutputStream os=new ByteArrayOutputStream();
                JAXBContext jaxbCtx=JAXBContext.newInstance(Person.class);
                Marshaller mrsh=jaxbCtx.createMarshaller();
                mrsh.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                mrsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                mrsh.marshal(p, os);
                rez=os.toString();
            } catch (JAXBException ex) {
                Logger.getLogger(Persons.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rez;
    }
    
    private String getJSONPerson(int id){
        String rez=null;
        try {
            String xml=getXMLPerson(id);
            if(xml!=null){
                JSONObject json=XML.toJSONObject(xml);
                rez=json.toString(3);
            }
        } catch (JSONException ex) {
            Logger.getLogger(Persons.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rez;
    }
    
    private void loadAgenda(){
        InputStream is=sctx.getResourceAsStream("/WEB-INF/data/agenda.csv");
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        try{
            String l;
            Person prs;
            while((l=br.readLine())!=null){
                String[] agendaPrs=l.split(",");
                prs=new Person(idIdx.getAndIncrement(), agendaPrs[0], agendaPrs[1]);
                agenda.add(prs);
            }
        }catch (IOException ex){
            Logger.getLogger(Persons.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addPerson(String name,String phone){
        if(agenda.isEmpty())loadAgenda();
        Person prs=new Person(idIdx.getAndIncrement(),name,phone);
        agenda.add(prs);
    }
    
    public void editPerson(int id,String name,String phone){
        if(agenda.isEmpty())loadAgenda();
        Person p=new Person(id);
        int pos;
        if((pos=agenda.indexOf(p))>=0){
            p=agenda.get(pos);
            p.setName(name);
            p.setPhone(phone);
        }
    }
    
    public void deletePerson(int id){
        if(agenda.isEmpty())loadAgenda();
        Person p=new Person(id);
        int pos;
        if((pos=agenda.indexOf(p))>=0){
            agenda.remove(pos);
        }
    }
}
