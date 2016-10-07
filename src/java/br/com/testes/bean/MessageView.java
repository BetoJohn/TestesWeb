/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testes.bean;

/**
 *
 * @author carlos.macedo
 */

import br.com.snmp.service.MessageService;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class MessageView {
    private MessageService msg;
    public void save() {
        msg = new MessageService();
        msg.addMessage("Success", "Salvo", "Data saved");
    }
     
    public void update() {
        msg = new MessageService();
        msg.addMessage("Fatal", "Deletado","Data updated");
    }
     
    public void delete() {
        msg = new MessageService();
        msg.addMessage("Error", "Conflito","Data deleted");
    }
     
   
}
