/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xml_bind;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import xml_bind_xjc.Persoana;
import xml_bind_xjc.Persoane;

/**
 *
 * @author 10051644
 */
public class XML_Bind {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XML_Bind xbind=new XML_Bind();
        xbind.JAXBTest();
        xbind.XStreamTest();
        xbind.XStreamFineGrainedTest();
        
        //Clasele xml_bind_xjc au fost generate cu utilitarul xjc din fisierul test2.xsd
        //xjc -p xml_bind_xjc test2.xsd
        xbind.XJCTest();
    }
    
    public void XJCTest(){
        try {
            final String fileName = "xsd/test2.xsd"; // downloaded XML Schema
            final String schemaUri = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            
            SchemaFactory factory = SchemaFactory.newInstance(schemaUri);
            Schema schema = factory.newSchema(new StreamSource(fileName));
            
            JAXBContext ctx = JAXBContext.newInstance(Persoane.class);
            Unmarshaller um = ctx.createUnmarshaller();
            um.setSchema(schema);
            Persoane pers=(Persoane)um.unmarshal(new FileInputStream("xsd/test2.xml"));
            for(Persoana p:pers.getPersoana()){
                System.out.println(p.getNume()+" ["+p.getVarsta()+"] din "+p.getOras());
            }
        } catch (SAXException ex) {
            Logger.getLogger(XML_Bind.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(XML_Bind.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(XML_Bind.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void XStreamFineGrainedTest(){
        PersonProps person = new PersonProps();
        person.setName("Bruno");
        
        XStream xstream = new XStream(new DomDriver());
        xstream.registerConverter(new PersonPropsConverter());
        xstream.alias("person", PersonProps.class);
        
        String xml = xstream.toXML(person);
        System.out.println(xml);
        PersonProps clone = (PersonProps) xstream.fromXML(xml);
        System.out.println(clone.getName()); // Bruno
    }
    
    public void XStreamTest(){
        //XML
        PersonNoProps bd = new PersonNoProps("Bjoern Daehlie", 49, "Male");
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("skier", PersonNoProps.class); // for readability
        
        String xml = xstream.toXML(bd);
        System.out.println(xml);
        PersonNoProps bdClone = (PersonNoProps) xstream.fromXML(xml);
        System.out.println(xstream.toXML(bdClone));
        
        //JSON
        bd = new PersonNoProps("Bjoern Daehlie", 49, "Male");
        xstream = new XStream(new JsonHierarchicalStreamDriver());
        xstream.alias("skier", PersonNoProps.class); // for readability
        String json = xstream.toXML(bd); // it's really toJson now
        System.out.println(json);
    }
    
    public void JAXBTest(){
        try {
            JAXBContext ctx = JAXBContext.newInstance(Skier.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            
            Skier skier = createSkier();
            m.marshal(skier, System.out);
            
            ByteArrayOutputStream os=new ByteArrayOutputStream();
            m.marshal(skier, os);
            os.close();
            
            ByteArrayInputStream is=new ByteArrayInputStream(os.toByteArray());
            Unmarshaller u = ctx.createUnmarshaller();
            Skier bdClone = (Skier) u.unmarshal(is);
            System.out.println();
	    m.marshal(bdClone, System.out);
        } catch (JAXBException | IOException ex) {
            Logger.getLogger(XML_Bind.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Skier createSkier() {
	Person bd = new Person("Bjoern Daehlie", 49, "Male");
        List<String> list = new ArrayList<String>();
	list.add("12 Olympic Medals");
        list.add("9 World Championships");                
        list.add("Winningest Winter Olympian");                       
	list.add("Greatest Nordic Skier");
        return new Skier(bd, "Norway", list);
    }
    
}
