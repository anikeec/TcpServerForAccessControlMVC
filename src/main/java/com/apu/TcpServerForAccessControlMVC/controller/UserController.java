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

import com.apu.TcpServerForAccessControlDB.entity.User;
import com.apu.TcpServerForAccessControlDB.repository.UserRepository;

@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/user/view")
    public ModelAndView index(Principal principal) {
        Map<String, Object> model = new HashMap<>();
        List<User> userList = userRepository.findAll();
        model.put("userList", userList);
        if(principal != null) {
            model.put("name", principal.getName());
        }
        return new ModelAndView("user/view", model);
    }
    
    @RequestMapping(value="/user/add", method = RequestMethod.GET)
    public ModelAndView add(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        List<User> userList = userRepository.findAll();
        modelAndView.addObject("userList", userList);
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        modelAndView.setViewName("user/add");        
        return modelAndView;
    }
    
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        List<User> userList = userRepository.findByEmail(user.getEmail());
        if ((userList != null)&&(userList.size() > 0)) {
            bindingResult
                    .rejectValue("email", "error.email",
                            "This user email is already registered in the system");
        }
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("user/add");
        } else {
            userRepository.save(user);
            modelAndView.addObject("successMessage", "User has been added successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("user/add");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/user/activate", method = RequestMethod.GET)
    public ModelAndView activateUser(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        List<User> userList = userRepository.findByActive(false);
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("user/activate"); 
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/user/activate", method = RequestMethod.POST)
    public ModelAndView activateUser(@Valid User user, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<User> userList = userRepository.findByUserId(user.getUserId());
        if((userList == null) || (userList.size() == 0)) {
            bindingResult
                .rejectValue("userId", "error.userId",
                        "This user id has't registered in the system yet");
        } else {
            user = userList.get(0);
            user.setActive(true);
            userRepository.save(user);
        }            
        
        userList = userRepository.findByActive(false);
        modelAndView.addObject("userList", userList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("user/activate");
        } else {            
            modelAndView.addObject("successMessage", "User has been activated successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("user/activate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/user/inactivate", method = RequestMethod.GET)
    public ModelAndView inactivateUser(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        List<User> userList = userRepository.findByActive(true);
        modelAndView.addObject("userList", userList);
        modelAndView.setViewName("user/inactivate");
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
    @RequestMapping(value = "/user/inactivate", method = RequestMethod.POST)
    public ModelAndView inactivateUser(@Valid User user, BindingResult bindingResult, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();        
        List<User> userList = userRepository.findByUserId(user.getUserId());
        if((userList == null) || (userList.size() == 0)) {
            bindingResult
                .rejectValue("userId", "error.userId",
                        "This user id has't registered in the system yet");
        } else {
            user = userList.get(0);
            user.setActive(false);
            userRepository.save(user);
        }            
        
        userList = userRepository.findByActive(true);
        modelAndView.addObject("userList", userList);
        if (bindingResult.hasErrors()) {            
            modelAndView.setViewName("user/inactivate");
        } else {            
            modelAndView.addObject("successMessage", "User has been inactivated successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("user/inactivate");
        }
        if(principal != null) {
            modelAndView.addObject("name", principal.getName());
        }
        return modelAndView;
    }
    
}
