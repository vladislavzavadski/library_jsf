/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.controller;

import com.library.enums.SearchType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Владислав
 */
@ManagedBean(name = "searchTypeController")
@RequestScoped
public class SearchTypeController implements Serializable {
    
    private Map<String, SearchType> searchList = new HashMap<>();

    public void setSearchList(Map<String, SearchType> searchList) {
        this.searchList = searchList;
    }

    public SearchTypeController() {
        ResourceBundle bundle = ResourceBundle.getBundle("com.library.properties.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("search_author"), SearchType.Author);
        searchList.put(bundle.getString("search_name"), SearchType.Name);
    }

    public  Map<String, SearchType> getSearchList() {
        return searchList;
    }
    
}
