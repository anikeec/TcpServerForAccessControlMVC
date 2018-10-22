package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlMVC.entity.VisualEntity;
import com.apu.TcpServerForAccessControlMVC.service.CardService;
import com.apu.TcpServerForAccessControlMVC.service.ServiceUtils;
import com.apu.TcpServerForAccessControlMVC.service.UserService;

@Controller
public class CardController {
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private UserService userService;    

    private final ServiceUtils<Card> utils;

    @Autowired
    public CardController(ServiceUtils<Card> serviceUtils) {
        this.utils = serviceUtils;
    }
    
    @GetMapping("/card/view")
    public ModelAndView index(Principal principal) {
        List<Card> cardList = cardService.findAll();
//        for(Card card:cardList) {
//            card.setUserId(userService.findByUserId(card.getUserId().getUserId()).get(0));
//        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cardList", cardList);
        utils.setUserName(modelAndView, principal);
        modelAndView.setViewName("card/view");      
        return modelAndView;
    }
    
    @RequestMapping(value="/card/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Card card = new Card();
        modelAndView.addObject("card", card);
        List<SystemUser> userList = userService.findAll();
        modelAndView.addObject("userList", userList);
        utils.setUserName(modelAndView, principal);
        modelAndView.setViewName("card/add");        
        return modelAndView;
    }
    
    @RequestMapping(value = "/card/add", method = RequestMethod.POST)
    public ModelAndView createNewCard(@Valid Card card, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        List<Card> cardList = cardService.findByCardNumber(card.getCardNumber());
        if ((cardList != null)&&(cardList.size() > 0)) {
            bindingResult
                    .rejectValue("cardNumber", "error.cardNumber",
                            "This card number is already registered in the system");
        }
        List<SystemUser> userList = userService.findAll();
        modelAndView.addObject("userList", userList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("card/add");
        } else {
            cardService.save(card);
            modelAndView.addObject("successMessage", "Card has been added successfully");
            modelAndView.addObject("card", new Card());
            modelAndView.setViewName("card/add");
        }
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value="/card/activate", method = RequestMethod.GET)
    public ModelAndView activateCard(Principal principal) {
        ModelAndView modelAndView = utils.FillMvcEntity("Activate card", "/card/activate", false);  
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value = "/card/activate", method = RequestMethod.POST)
    public ModelAndView activateCard(@Valid VisualEntity entity, BindingResult bindingResult, Principal principal) {
        Assert.notNull(entity.getEntityId(), "CardId has not be null.");     
        String errorMessage = utils.saveEntity(entity, true);
        ModelAndView modelAndView = utils.FillMvcEntity("Activate card", "/card/activate", false);
        if (errorMessage != null) {
            modelAndView.addObject("successMessage", errorMessage);
        } else {            
            modelAndView.addObject("successMessage", "Card has been activated successfully");
        }
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value="/card/inactivate", method = RequestMethod.GET)
    public ModelAndView inactivateCard(Principal principal) {
        ModelAndView modelAndView = utils.FillMvcEntity("Inactivate card", "/card/inactivate", true);
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
    @RequestMapping(value = "/card/inactivate", method = RequestMethod.POST)
    public ModelAndView inactivateCard(@Valid VisualEntity entity, BindingResult bindingResult, Principal principal) {
        Assert.notNull(entity.getEntityId(), "CardId has not be null.");        
        String errorMessage = utils.saveEntity(entity, false);         
        ModelAndView modelAndView = utils.FillMvcEntity("Inactivate card", "/card/inactivate", true);
        if (errorMessage != null) {
            modelAndView.addObject("successMessage", errorMessage);
        } else {            
            modelAndView.addObject("successMessage", "Card has been inactivated successfully");
        }
        utils.setUserName(modelAndView, principal);
        return modelAndView;
    }
    
}
