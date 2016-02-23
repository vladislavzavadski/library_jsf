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
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Владислав
 */
@ManagedBean
@SessionScoped
public class SearchController implements Serializable {
    private SearchType searchType;
    private static Map<String, SearchType> searchList = new HashMap<>();
    public SearchController(){
        ResourceBundle bundle = ResourceBundle.getBundle("com.library.properties.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("search_author"), SearchType.Author);
        searchList.put(bundle.getString("search_name"), SearchType.Name);
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
    
}
