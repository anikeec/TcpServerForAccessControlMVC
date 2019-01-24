package com.apu.TcpServerForAccessControlMVC.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class VisualEntity {
    
    @Getter @Setter
    private String pageName;
    
    @Getter @Setter
    private String elementName;
    
    @Getter @Setter
    private String actionValue;
    
    private List<Pair> elementsList = new ArrayList<>();
    
    @Getter @Setter
    private Integer entityId;    
    
    public List<Pair> getElementsList() {
        return elementsList;
    }
    public void addElementToList(Integer id, String value) {
        elementsList.add(new Pair(id, value));
    }    

    class Pair {
        public Integer id;
        public String value;
        public Pair(Integer id, String value) {
            super();
            this.id = id;
            this.value = value;
        }        
    }
    
    
}
