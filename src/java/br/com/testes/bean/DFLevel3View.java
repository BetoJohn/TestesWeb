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
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;
 
@ManagedBean(name = "dfLevel3View")
public class DFLevel3View {
     
    private String val;
 
    public String getVal() {
        return val;
    }
 
    public void setVal(String val) {
        this.val = val;
    }
 
    public void closeDialog() {
        //pass back to level 2
        RequestContext.getCurrentInstance().closeDialog(val);
    }
}