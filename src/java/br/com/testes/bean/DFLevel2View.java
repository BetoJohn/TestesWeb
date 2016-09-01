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
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
 
@ManagedBean(name = "dfLevel2View")
public class DFLevel2View {
     
    public void openLevel3() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("level3", options, null);
    }
     
    public void onReturnFromLevel3(SelectEvent event) {
        //pass back to level 1
        RequestContext.getCurrentInstance().closeDialog(event.getObject());
    }
}
