package com.apu.TcpServerForAccessControlMVC.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.repository.CardRepository;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;
import com.apu.TcpServerForAccessControlMVC.service.CardService;

@Controller
public class CardController {
    
    @Autowired
    private CardService cardService;
    
    @Autowired
    private SystemUserRepository userRepository;
    
    @GetMapping("/card/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<Card> cardList = cardService.findAll();
//        for(Card card:cardList) {
//            card.setUserId(userRepository.findByUserId(card.getUserId().getUserId()).get(0));
//        }
        model.put("cardList", cardList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("card/view", model);
    }
    
    @RequestMapping(value="/card/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Card card = new Card();
        modelAndView.addObject("card", card);
        List<SystemUser> userList = userRepository.findAll();
        modelAndView.addObject("userList", userList);
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
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
        List<SystemUser> userList = userRepository.findAll();
        modelAndView.addObject("userList", userList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("card/add");
        } else {
            cardService.save(card);
            modelAndView.addObject("successMessage", "Card has been added successfully");
            modelAndView.addObject("card", new Card());
            modelAndView.setViewName("card/add");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/card/activate", method = RequestMethod.GET)
    public ModelAndView activateCard(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Card card = new Card();
        modelAndView.addObject("card", card);
        List<Card> cardList = cardService.findByActive(false);
        modelAndView.addObject("cardList", cardList);
        modelAndView.setViewName("card/activate"); 
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/card/activate", method = RequestMethod.POST)
    public ModelAndView activateCard(@Valid Card card, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<Card> cardList = cardService.findByCardId(card.getCardId());
        if((cardList == null) || (cardList.size() == 0)) {
            bindingResult
                .rejectValue("cardId", "error.cardId",
                        "This card id has't registered in the system yet");
        } else {
            card = cardList.get(0);
            card.setActive(true);
            cardService.save(card);
        }            
        
        cardList = cardService.findByActive(false);
        modelAndView.addObject("cardList", cardList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("card/activate");
        } else {            
            modelAndView.addObject("successMessage", "Card has been activated successfully");
            modelAndView.addObject("card", new Card());
            modelAndView.setViewName("card/activate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/card/inactivate", method = RequestMethod.GET)
    public ModelAndView inactivateCard(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        Card card = new Card();
        modelAndView.addObject("card", card);
        List<Card> cardList = cardService.findByActive(true);
        modelAndView.addObject("cardList", cardList);
        modelAndView.setViewName("card/inactivate");
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/card/inactivate", method = RequestMethod.POST)
    public ModelAndView inactivateCard(@Valid Card card, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<Card> cardList = cardService.findByCardId(card.getCardId());
        if((cardList == null) || (cardList.size() == 0)) {
            bindingResult
                .rejectValue("cardId", "error.cardId",
                        "This card id has't registered in the system yet");
        } else {
            card = cardList.get(0);
            card.setActive(false);
            cardService.save(card);
        }            
        
        cardList = cardService.findByActive(true);
        modelAndView.addObject("cardList", cardList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("card/inactivate");
        } else {            
            modelAndView.addObject("successMessage", "Card has been inactivated successfully");
            modelAndView.addObject("card", new Card());
            modelAndView.setViewName("card/inactivate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
}
