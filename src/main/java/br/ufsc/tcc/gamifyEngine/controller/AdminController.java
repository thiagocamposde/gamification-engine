package br.ufsc.tcc.gamifyEngine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
	 @RequestMapping(value="/home",method = RequestMethod.GET)
     public String adminHomePage(){
         return "index";
     }
}