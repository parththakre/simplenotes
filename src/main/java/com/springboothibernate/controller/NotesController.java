package com.springboothibernate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboothibernate.dao.NotesDAO;
import com.springboothibernate.model.Notes;



@Controller
public class NotesController {
	
	@Autowired
	private NotesDAO notesDao;
	
	@RequestMapping(value="/notes",method=RequestMethod.GET)
	public String newRegistration(ModelMap model) {
		Notes notes = new Notes();
		model.addAttribute("notes",notes);
		return "addNote";
	}
	
	@RequestMapping(value="/viewNotesByUser")
	public ModelAndView getAllUserNotes(@ModelAttribute("email") String email,  ModelMap model) {
		List<Notes> list= notesDao.findByUserEmail(email);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("list",list);
		obj.put("emailId", email);
		return new ModelAndView("viewNotes", "obj", obj);
	}
	
	@RequestMapping(value="/deletenote/{id}",method=RequestMethod.GET)
	public ModelAndView deleteNote(@PathVariable int id) {
		Notes note = notesDao.findOne(id);
		notesDao.delete(note);
		return new ModelAndView("redirect:/viewNotesByUser","email",note.getEmailId());
	}
	
	@RequestMapping(value="/editnote/{id}")
	public ModelAndView getAllUserNotes(@PathVariable int id,ModelMap model) {
		Notes note = notesDao.findOne(id);
		return new ModelAndView("editNote", "note", note);
	}
	
	@RequestMapping(value="/saveNote/{emailId}",method=RequestMethod.POST)
	public ModelAndView saveRegistration(Notes note,@PathVariable String emailId, BindingResult result,ModelMap model,RedirectAttributes redirectAttributes) {
		model.addAttribute("emailId", emailId);
		if(result.hasErrors()) {
			System.out.println("has errors");
			new ModelAndView("redirect:/viewNotesByUser", "email", note.getEmailId());
		}
		note.setEmailId(emailId);
		notesDao.save(note);
		List<Notes> userNotes = notesDao.findByUserEmail(note.getEmailId());
		model.addAttribute("notes",userNotes);
		return new ModelAndView("redirect:/viewNotesByUser","email",note.getEmailId());
	}
	
	@RequestMapping(value="/notes/{id}")
	public ModelAndView addoneNote (@PathVariable String id,ModelMap model) {
		Notes notes = new Notes();
		model.addAttribute("notes",notes);
		model.addAttribute("note", notesDao.findByUserEmail(id));
		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("addnote", "emailId", id);
	}

	@RequestMapping(value="/editsavenote/{id}",method=RequestMethod.POST)
	public ModelAndView editsave(@ModelAttribute("note") Notes note, @PathVariable int id) {
		
		Notes notes=notesDao.findOne(id);
		
		notes.setContent(note.getContent());
		notesDao.save(notes);
		return new ModelAndView("redirect:/viewNotesByUser","email",notes.getEmailId());
	}
}