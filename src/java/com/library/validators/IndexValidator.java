/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.validators;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Владислав
 */
@FacesValidator("com.library.validators.IndexValidator")
public class IndexValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value.toString().length()<5){
           ResourceBundle bundle = ResourceBundle.getBundle("com.library.properties.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
           FacesMessage facesMessage = new FacesMessage(bundle.getString("min_mength"));
           facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ValidatorException(facesMessage);
        }
        if((value.toString().toLowerCase().charAt(0)<'a'||value.toString().toLowerCase().charAt(0)>'z')&&(value.toString().toLowerCase().charAt(0)<'а'||value.toString().toLowerCase().charAt(0)>'я')){
           ResourceBundle bundle = ResourceBundle.getBundle("com.library.properties.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
           FacesMessage facesMessage = new FacesMessage(bundle.getString("start_letter"));
           facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ValidatorException(facesMessage);
        }
        if(value.toString().toLowerCase().equals("username")||value.toString().toLowerCase().equals("login")){
           ResourceBundle bundle = ResourceBundle.getBundle("com.library.properties.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
           FacesMessage facesMessage = new FacesMessage(bundle.getString("login_busy"));
           facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
           throw new ValidatorException(facesMessage);
        }
    }
    
}
