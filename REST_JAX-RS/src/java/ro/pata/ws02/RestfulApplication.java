/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Adrian
 */
@ApplicationPath("resA")
public class RestfulApplication extends Application {
    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> set=new HashSet<>();
        set.add(PersonREST.class);
        return set;
    }
}
