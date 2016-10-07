/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.validator;

import br.com.testes.bean.DeviceBeanTeste;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author carlos.macedo
 */
@FacesValidator("portValidator")
public class PortValidator implements Validator {

    /**
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object obj = component.getAttributes().get("portaInicial");

        if (obj != null) {
            int portIni = (int) obj;
            int portFinal = (int) value;

            //Caso não queira que seja igual adicionar na condição  || portFinal == portIni
            if (portFinal < portIni) {
                ((UIInput) component).setValid(false);

                FacesMessage message = new FacesMessage(
                        "A Porta Final deve ser maior que a Inicial!");
                context.addMessage(component.getClientId(context), message);

            }

        }

    }

}
