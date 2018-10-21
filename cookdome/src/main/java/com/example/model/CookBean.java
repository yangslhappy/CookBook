/**
  * Copyright 2018 bejson.com 
  */
package com.example.model;
import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-05-04 15:15:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CookBean implements Serializable{

    private String id;
    private String title;
    private String tags;
    private String imtro;
    private String ingredients;
    private String burden;
    private List<String> albums;
    private List<Steps> steps;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setTags(String tags) {
         this.tags = tags;
     }
     public String getTags() {
         return tags;
     }

    public void setImtro(String imtro) {
         this.imtro = imtro;
     }
     public String getImtro() {
         return imtro;
     }

    public void setIngredients(String ingredients) {
         this.ingredients = ingredients;
     }
     public String getIngredients() {
         return ingredients;
     }

    public void setBurden(String burden) {
         this.burden = burden;
     }
     public String getBurden() {
         return burden;
     }

    public void setAlbums(List<String> albums) {
         this.albums = albums;
     }
     public List<String> getAlbums() {
         return albums;
     }

    public void setSteps(List<Steps> steps) {
         this.steps = steps;
     }
     public List<Steps> getSteps() {
         return steps;
     }

}