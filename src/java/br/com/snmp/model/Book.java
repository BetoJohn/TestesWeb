/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.snmp.model;

/**
 *
 * @author carlos.macedo
 */
import java.io.Serializable;
 
public class Book implements Serializable {
     
    private String title;
    private String author;
    private String publisher;
    private Integer pages;
 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getAuthor() {
        return author;
    }
 
    public void setAuthor(String author) {
        this.author = author;
    }
 
    public String getPublisher() {
        return publisher;
    }
 
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
 
    public Integer getPages() {
        return pages;
    }
 
    public void setPages(Integer pages) {
        this.pages = pages;
    }
 
    public boolean equals(Object obj) {
        if(!(obj instanceof Book))
            return false;
         
        Book book = (Book) obj;
         
        return (book.getTitle() != null && book.getTitle().equals(title)) && (book.getAuthor() != null && book.getAuthor().equals(author));
    }
 
    public int hashCode() {
        int hash = 1;
        if(title != null)
            hash = hash * 31 + title.hashCode();
         
        if(author != null)
            hash = hash * 29 + author.hashCode();
 
        return hash;
    }
}
