/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.library.Database;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.Genre;
import com.library.entity.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Владислав
 */
public class DataHelper {
    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper = null;

    public int getTotalBooksNumber() {
        return totalBooksNumber;
    }
    private Transaction transaction = null;
    private int totalBooksNumber = 0;
    
    private DataHelper(){
        sessionFactory = HibernateUtil.getSessionFactory();       
    } 
    
    public static DataHelper getInstance(){
        
        return dataHelper==null?dataHelper = new DataHelper():dataHelper;
    }
    
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
    
    public List<Book> getAllBooks(int firstNote, int notesNumber){
        getSession().beginTransaction();
        totalBooksNumber = getSession().createCriteria(Book.class).list().size();
        List<Book> books = getSession().createCriteria(Book.class).addOrder(Order.asc("name")).setFirstResult(firstNote).setMaxResults(notesNumber).list();
        getSession().getTransaction().commit();
        return books;
    }
    
    public List<Genre> getAllGenres(){
        getSession().beginTransaction();
        List<Genre> genres = getSession().createCriteria(Genre.class).list();
        getSession().getTransaction().commit();
        return genres;
      
    }
    
    public List<Author> getAllAuthors(){
        getSession().beginTransaction();
        List<Author> genres = getSession().createCriteria(Author.class).list();
        getSession().getTransaction().commit();
        return genres;

    }
    
    public List<Book> getBooksByGenre(Long genreId, int firstNote, int notesNumber){
        getSession().beginTransaction();
        totalBooksNumber = getSession().createCriteria(Book.class).add(Restrictions.eq("genre.id", genreId)).list().size();
        List<Book> books = getSession().createCriteria(Book.class).add(Restrictions.eq("genre.id", genreId)).addOrder(Order.asc("name")).setFirstResult(firstNote).setMaxResults(notesNumber).list();
        getSession().getTransaction().commit();
        return books;

    }
    
    public List<Book> getBooksByLetter(Character letter, int firstNote, int notesNumber){
        return getBookList("name", letter.toString(), MatchMode.START, firstNote, notesNumber);
    }
    
    public List<Book> getBookByName(String name, int firstNote, int notesNumber){
        return getBookList("name", name, MatchMode.ANYWHERE, firstNote, notesNumber);
    }
    
    public List<Book> getBookByAuthor(String authorName, int firstNote, int notesNumber){
        
        return getBookList("author", authorName, MatchMode.ANYWHERE, firstNote, notesNumber);
        
    }
    
    public byte[] getContent(Long id){
        return (byte[]) getFieldValue("content", id);
    }
    
    public byte[] getImage(Long id){
        return (byte[]) getFieldValue("image", id);
    }
    
    private Object getFieldValue(String field, Long id){
        getSession().beginTransaction();
        Object bloob = getSession().createCriteria(Book.class).setProjection(Projections.property(field)).add(Restrictions.eq("id", id)).uniqueResult();
        getSession().getTransaction().commit();
        return bloob;

    }
    
    private List<Book> getBookList(String field, String value, MatchMode matchMode, int firstNote,int notesNumber){
        getSession().beginTransaction();
        totalBooksNumber = getSession().createCriteria(Book.class).add(Restrictions.ilike(field, value, matchMode)).list().size();
        List<Book> books = getSession().createCriteria(Book.class).add(Restrictions.ilike(field, value, matchMode)).addOrder(Order.asc("name")).setFirstResult(firstNote).setMaxResults(notesNumber).list();
        getSession().getTransaction().commit();
        return books;

    }
       
}
