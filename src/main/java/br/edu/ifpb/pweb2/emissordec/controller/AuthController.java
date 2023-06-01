package br.edu.ifpb.pweb2.emissordec.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.pweb2.emissordec.model.Estudante;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getForm(ModelAndView modelAndView) {
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/auth");
        return mav;
    }


}


