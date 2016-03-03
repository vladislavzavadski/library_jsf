/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Владислав
 */
@ManagedBean(eager = true)
@SessionScoped
public class LocaleChanger implements Serializable {
    private Locale locale;

    public LocaleChanger() {
        locale = new Locale("ru");
    }

    public Locale getLocale() {
        return locale;
    }
    
    public void setLocale(String locale){
        this.locale = new Locale(locale);
    }
}
