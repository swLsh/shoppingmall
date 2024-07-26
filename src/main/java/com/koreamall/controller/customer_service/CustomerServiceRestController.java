package com.koreamall.controller.customer_service;


import com.koreamall.service.customer_service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer_service")
public class CustomerServiceRestController {
    @Autowired private CustomerService customerService;

    @GetMapping("/one_on_one/opened/{no}")
    public ResponseEntity<Boolean> get_one_on_one_isOpened(
            @PathVariable("no") Integer oneOnOneNo,
            HttpSession session
    ){
        if(customerService.get_post_isOpened(oneOnOneNo)){
            // 공개 상태
            session.setAttribute("isPasswordCorrect", true);
            return ResponseEntity.ok(true);
        }
        // 비공개상태
        session.setAttribute("isPasswordCorrect", false);
        return ResponseEntity.ok(false);
    }

    @GetMapping("/one_on_one/password/{no}")
    public ResponseEntity<Boolean> get_one_on_one_password_is_correct(
            @PathVariable("no") Integer oneOnOneNo,
            @RequestParam("password") String password,
            HttpSession session
    ){
        if(customerService.get_post_password_isCorrect(oneOnOneNo, password)){
            session.setAttribute("isPasswordCorrect", true);
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.ok().body(false);
    }




}
