package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.repository.CardRepository;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcFullService;

@Service
public class CardService implements MvcFullService<Card> {

    @Autowired
    private CardRepository cardRepository;
    
    public List<Card> findAll() {
        return cardRepository.findAll();
    }
    
    @Override
    public List<Card> findById(Integer cardId) {
        return cardRepository.findByCardId(cardId);
    }
    
    public List<Card> findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }
    
    public List<Card> findByActive(Boolean active) {
        return cardRepository.findByActive(active);
    }

    @Override
    public Card save(Card entity) {
        return cardRepository.save(entity);
    }

    public void delete(Card entity) {
        cardRepository.delete(entity);
    }
    
}
