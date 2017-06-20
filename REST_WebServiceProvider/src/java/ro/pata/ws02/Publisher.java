/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.pata.ws02;

import javax.xml.ws.Endpoint;

/**
 *
 * @author A4YZTZZ
 */
public class Publisher {
    public static void main(String[] args){
        Endpoint.publish("http://localhost:8888/", new PersonsProvider());
    }
}
