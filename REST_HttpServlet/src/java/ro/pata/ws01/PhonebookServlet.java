/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws01;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author adi
 */
public class PhonebookServlet extends HttpServlet {
    Persons agenda;
    
    @Override
    public void init(){
        agenda=new Persons(this.getServletContext());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml;charset=UTF-8");
        agenda.setAccept(request.getHeader("accept"));
        String ids=request.getParameter("id");
        
        try (PrintWriter out = response.getWriter()) {
            if(ids==null){
                out.println(agenda.getAgenda());
            } else {
                out.println(agenda.getPerson(Integer.parseInt(ids)));
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name=request.getParameter("name");
        String phone=request.getParameter("phone");
                
        try(PrintWriter out=response.getWriter()){
            if(name!=null && phone!=null){
                out.println("Primit: "+name);
                out.println("Param: "+phone);
                agenda.addPerson(name, phone);
                out.println("Added.");
            }else{
                out.println("Error: please provide name and phone.");
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream is=request.getInputStream();
        String data=IOUtils.toString(is, "UTF-8");
        String[] params=data.split("&");
        
        try(PrintWriter out=response.getWriter()){
            if(params.length==3){
                out.println(data);
                agenda.editPerson(Integer.parseInt(params[0]), params[1], params[2]);
                out.println("Ok");
            }else{
                out.println("Error: please provide id, name and phone.");
            }
        }
    }
    
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id=request.getParameter("id");
        try(PrintWriter out=response.getWriter()){
            if(id!=null){
                out.println("Delete id: "+id);
                agenda.deletePerson(Integer.parseInt(id));
                out.println("Ok.");
            }else {
                out.println("Error: please provide id, name and phone.");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
