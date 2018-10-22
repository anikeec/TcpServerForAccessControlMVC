package com.apu.TcpServerForAccessControlMVC.entity;

import java.util.ArrayList;
import java.util.List;

public class VisualEntity {
    private String pageName;
    private String elementName;
    private String actionValue;
    private List<Pair> elementsList = new ArrayList<>();
    private Integer entityId;    

    public String getPageName() {
        return pageName;
    }
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
    public String getElementName() {
        return elementName;
    }
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }
    public String getActionValue() {
        return actionValue;
    }
    public void setActionValue(String actionValue) {
        this.actionValue = actionValue;
    }
    public List<Pair> getElementsList() {
        return elementsList;
    }
    public void addElementToList(Integer id, String value) {
        elementsList.add(new Pair(id, value));
    }    
    public Integer getEntityId() {
        return entityId;
    }
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
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
