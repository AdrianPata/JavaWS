/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02.resources;

import javax.servlet.ServletContext;
import ro.pata.ws02.Persons;

/**
 *
 * @author A4YZTZZ
 */
public class Agenda {
    private static Persons agenda;
    
    static public void loadAgenda(ServletContext c){
        agenda=new Persons(c);        
    }
    
    static public Persons getAgenda(String accept){
        agenda.setAccept(accept);
        return agenda;
    }
}
