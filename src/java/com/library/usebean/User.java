/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.usebean;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author Владислав
 */
@ManagedBean
@SessionScoped
public class User {

    /**
     * Creates a new instance of User
     */
    private String name;
    private String password;

    public String getPassword() {
        return password;
    }  

    public void setPassword(String password) {
        this.password = password;
    }
    
    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void login(){
        HttpServletRequest httpServletRequest = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            if(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()!=null){
               httpServletRequest.logout();
            }
            httpServletRequest.login(name, password);
             HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(((HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse()));
            try {
                wrappedResponse.sendRedirect(httpServletRequest.getContextPath()+"/pages/books.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        } catch (ServletException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            ResourceBundle bundle = ResourceBundle.getBundle("com.library.properties.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesMessage facesMessage = new FacesMessage(bundle.getString("invalid_authorisation"));           
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage("login_form", facesMessage);
    
        }
    }
    public String logout(){
        try {
            ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).logout();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "exit";
        } catch (ServletException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            return "exit";
        }
    }
    
    public boolean isUserAdmin(){
        return FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMIN");
    }
}
