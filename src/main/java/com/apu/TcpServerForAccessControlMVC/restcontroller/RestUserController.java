package com.apu.TcpServerForAccessControlMVC.restcontroller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apu.TcpServerForAccessControlDB.entity.Card;
import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.entity.UserRole;
import com.apu.TcpServerForAccessControlMVC.PageWrapper;
import com.apu.TcpServerForAccessControlMVC.service.UserService;
import com.apu.TcpServerForAccessControlRestAPI.UserRest;

@RestController
@RequestMapping("/api/user")
public class RestUserController {

private static int PAGE_SIZE = 10;
    
    @Autowired
    private UserService userService;    
    
    @GetMapping("/view")
    public List<UserRest> index(Principal principal,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer pageSize) {
        
        Pageable pageable = null;
        if((page!=null)&&(pageSize!=null)) {
            pageable = PageRequest.of(page-1, pageSize);
        } else {
            pageable = PageRequest.of(0, PAGE_SIZE);
        }        

        Page<SystemUser> userPage = userService.findAll(pageable);
        PageWrapper<SystemUser> pageWrapper = new PageWrapper<>(userPage, "");
        
        List<SystemUser> userList = pageWrapper.getContent();
        List<UserRest> restList = new ArrayList<>();
        for(SystemUser u:userList) {
            
            List<String> cardCollection = new ArrayList<>();
            if(u.getCardCollection() != null) {
                for(Card card:u.getCardCollection()) {
                    cardCollection.add(card.getCardNumber());
                }
            }
            
            List<String> userRoleCollection = new ArrayList<>();
            if(u.getUserRoleCollection() != null) {
                for(UserRole userRole:u.getUserRoleCollection()) {
                    userRoleCollection.add(userRole.getDescription());
                }
            }
            
            restList.add(                   
                    new UserRest(
                            u.getUserId(),
                            u.getFirstName(),
                            u.getSecondName(),
                            u.getPhoneNumber(),
                            u.getEmail(),
                            u.getPassword(),
                            u.getActive(),
                            cardCollection,
                            userRoleCollection
                            )
                    );
        }
        return restList;
    }
    
}
