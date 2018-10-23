package com.apu.TcpServerForAccessControlMVC.service.utils;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.interfaces.ActivatableEntity;
import com.apu.TcpServerForAccessControlDB.interfaces.VisualizableEntity;
import com.apu.TcpServerForAccessControlMVC.entity.VisualEntity;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcFullService;

public class ActivatableServiceUtils<S extends ActivatableEntity> extends PageableServiceUtils<S> {
    
    private MvcFullService<S> service;
    
    public ActivatableServiceUtils(MvcFullService<S> service) {
        super();
        this.service = service;
    }

    public String saveEntity(VisualEntity visualEntity, boolean activeValue) {
        String errorMessage = null;
        List<S> list = service.findById(visualEntity.getEntityId());
        if((list == null) || (list.size() == 0)) {
            errorMessage = "Error. Entity with this id has't registered in the system yet";
        } else {
            S entity = list.get(0);
            entity.setActive(activeValue);
            service.save(entity);
        }
        
        return errorMessage;
    }   

    public ModelAndView fillMvcEntity(String elementName, String pageName, String actionValue, boolean activeValue) {
        ModelAndView modelAndView = new ModelAndView();
        VisualEntity entity = new VisualEntity();
        entity.setPageName(pageName);
        entity.setElementName(elementName);     
        entity.setActionValue(actionValue);
        List<S> list = service.findByActive(activeValue);
        for(S c:list) {
            if(c instanceof VisualizableEntity) {
                entity.addElementToList(((VisualizableEntity)c).getId(), ((VisualizableEntity)c).getDescription());
            }
        }
        modelAndView.addObject("entity", entity);
        modelAndView.setViewName("activate");
        return modelAndView;
    }
    
}
