package com.apu.TcpServerForAccessControlMVC.restcontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlMVC.PageWrapper;
import com.apu.TcpServerForAccessControlMVC.service.CardService;
import com.apu.TcpServerForAccessControlMVC.service.utils.ActivatableServiceUtils;
import com.apu.TcpServerForAccessControlRestAPI.CardRest;

@RestController
public class RestCardController {
    
    private static int PAGE_SIZE = 10;
    
    @Autowired
    private CardService cardService;    

    @Autowired
    @Qualifier("cardServiceUtils")
    private ActivatableServiceUtils<Card> utils;
    
    @RequestMapping("/card/api/view")
    public List<CardRest> index(Principal principal,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer pageSize) {
        
        Pageable pageable = null;
        if((page!=null)&&(pageSize!=null)) {
            pageable = PageRequest.of(page-1, pageSize);
        } else {
            pageable = PageRequest.of(0, PAGE_SIZE);
        }        

        Page<Card> accessMessagePage = cardService.findAll(pageable);
        PageWrapper<Card> pageWrapper = new PageWrapper<>(accessMessagePage, "");
        
        List<Card> cardList = pageWrapper.getContent();
        List<CardRest> cardRestList = new ArrayList<>();
        for(Card c:cardList) {
            cardRestList.add(new CardRest(c.getCardId(), c.getCardNumber(), c.getActive(), c.getUserId().getUserId()));
        }
        return cardRestList;
    }
    
}
