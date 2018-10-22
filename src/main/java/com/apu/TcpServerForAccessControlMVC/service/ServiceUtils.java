package com.apu.TcpServerForAccessControlMVC.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.ActivatableEntity;
import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlMVC.entity.VisualEntity;

@Component
public class ServiceUtils<S extends ActivatableEntity> {
    
    private MVCService<S> service;
    
    public ServiceUtils(MVCService<S> service) {
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
    
    public ModelAndView FillMvcEntity(String pageName, String actionValue, boolean activeValue) {
        ModelAndView modelAndView = new ModelAndView();
        VisualEntity entity = new VisualEntity();
        entity.setPageName(pageName);
        entity.setElementName("Card");     
        entity.setActionValue(actionValue);
        List<S> list = service.findByActive(activeValue);
        for(S c:list) {
            if(c instanceof Card) {
                entity.addElementToList(((Card)c).getCardId(), ((Card)c).getCardId() + " - " + ((Card)c).getCardNumber());
            }
        }
        modelAndView.addObject("entity", entity);
        modelAndView.setViewName("activate");
        return modelAndView;
    }
    
    public void setUserName(ModelAndView modelAndView, Principal principal) {
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
    }
    
}
