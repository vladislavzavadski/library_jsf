package com.library.entity;
// Generated 11.03.2016 18:42:23 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Genre generated by hbm2java
 */
public class Genre  implements java.io.Serializable, Comparable {


     private Long id;
     private String name;
     private Set books = new HashSet(0);

    public Genre() {
    }

	
    public Genre(String name) {
        this.name = name;
    }
    public Genre(String name, Set books) {
       this.name = name;
       this.books = books;
    }
   
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Set getBooks() {
        return this.books;
    }
    
    public void setBooks(Set books) {
        this.books = books;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Genre)o).getName());
    }




}


