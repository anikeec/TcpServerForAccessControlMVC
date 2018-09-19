package com.apu.TcpServerForAccessControlMVC.controller;

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
import com.apu.TcpServerForAccessControlDB.entity.User;
import com.apu.TcpServerForAccessControlDB.repository.CardRepository;
import com.apu.TcpServerForAccessControlDB.repository.UserRepository;

@Controller
public class CardController {
    
    @Autowired
    private CardRepository cardRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/card/view")
    public ModelAndView index() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Friend");
        List<Card> cardList = cardRepository.findAll();
        model.put("cardList", cardList);
        return new ModelAndView("card/view", model);
    }
    
    @RequestMapping(value="/card/add", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        Card card = new Card();
        modelAndView.addObject("card", card);
        List<User> userList = userRepository.findAll();
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("card/add");        
        return modelAndView;
    }
    
    @RequestMapping(value = "/card/add", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Card card, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        List<Card> cardList = cardRepository.findByCardNumber(card.getCardNumber());
        if ((cardList != null)&&(cardList.size() > 0)) {
            bindingResult
                    .rejectValue("cardNumber", "error.cardNumber",
                            "This card number is already registered in the system");
        }
        List<User> userList = userRepository.findAll();
        modelAndView.addObject("userList", userList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("card/add");
        } else {
            cardRepository.save(card);
            modelAndView.addObject("successMessage", "Card has been added successfully");
            modelAndView.addObject("card", new Card());
            modelAndView.setViewName("card/add");
        }
        return modelAndView;
    }
}
