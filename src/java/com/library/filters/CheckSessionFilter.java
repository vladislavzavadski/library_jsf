/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.filters;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Владислав
 */
@WebFilter(filterName= "CheckSessionFilter", 
urlPatterns= "/pages/*")
public class CheckSessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper((HttpServletRequest) request);
        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper((HttpServletResponse) response);
        HttpSession session = wrappedRequest.getSession(false);
        if(session==null||session.isNew()){
            wrappedResponse.sendRedirect(wrappedRequest.getContextPath()+"/index.xhtml");
            
        }
        else{
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
