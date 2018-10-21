/**
  * Copyright 2018 bejson.com 
  */
package com.example.model;
import java.util.List;

/**
 * Auto-generated: 2018-05-06 14:22:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class CookCategory {

    private String parentId;
    private String name;
    private List<CategrgoryList> list;
    public void setParentId(String parentId) {
         this.parentId = parentId;
     }
     public String getParentId() {
         return parentId;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCategrgoryList(List<CategrgoryList> list) {
         this.list = list;
     }
     public List<CategrgoryList> getList() {
         return list;
     }

}