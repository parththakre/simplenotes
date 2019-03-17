package com.springboothibernate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboothibernate.dao.NotesDAO;
import com.springboothibernate.dao.UserDAO;
import com.springboothibernate.model.Notes;
import com.springboothibernate.model.User;



@Controller
public class UserFormController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private NotesDAO notesDAO;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(ModelMap model) {
		User student = new User();
		model.addAttribute("student",student);
		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String newUserRegistration(ModelMap model) {
		User student = new User();
		model.addAttribute("student",student);
		return "register";
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveRegistration(@Valid User student,BindingResult result,ModelMap model,RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			System.out.println("has errors");
			return "redirect:/register";
		}
		userDAO.save(student);
		return "redirect:/login";
	}
	
	@RequestMapping(value="/loginsubmit", method=RequestMethod.POST)
	public ModelAndView edit (@ModelAttribute("student") User p, ModelMap model) {
		
		User student=userDAO.findByEmailAndPassword(p.email, p.password);
		if(student == null) {
			return new ModelAndView("redirect:/register");
		}
		List<Notes> userNotes = notesDAO.findByUserEmail(p.email);
		model.addAttribute("notes",userNotes);
		model.addAttribute("emailId", p.getEmail());
		return new ModelAndView("redirect:/viewNotesByUser","email",p.email);
	}
}