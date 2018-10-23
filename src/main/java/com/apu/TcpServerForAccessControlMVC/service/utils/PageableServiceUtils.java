package com.apu.TcpServerForAccessControlMVC.service.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import com.apu.TcpServerForAccessControlDB.interfaces.AccessControlEntity;
import com.apu.TcpServerForAccessControlMVC.PageWrapper;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcReadService;

public class PageableServiceUtils<S extends AccessControlEntity> extends ServiceUtils {

    private static int PAGE_SIZE = 10;
    
    private MvcReadService<S> service;
    
    public PageableServiceUtils( ) {   
        super();
    }
    
    public PageableServiceUtils(MvcReadService<S> service) {
        this();
        this.service = service;
    }
    
    public void setPages(ModelAndView modelAndView, Integer page, Integer pageSize) {
        Pageable pageable = null;
        if((page!=null)&&(pageSize!=null)) {
            pageable = PageRequest.of(page-1, pageSize);
        } else {
            pageable = PageRequest.of(0, PAGE_SIZE);
        }        

        Page<S> accessMessagePage = service.findAll(pageable);
        PageWrapper<S> pageWrapper = new PageWrapper<>(accessMessagePage, "");
        modelAndView.addObject("page", pageWrapper);
    }
    
}
