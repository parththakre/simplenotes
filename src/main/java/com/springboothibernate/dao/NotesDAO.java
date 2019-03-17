package com.springboothibernate.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboothibernate.model.Notes;
import com.springboothibernate.repository.NotesRepository;

@Service
public class NotesDAO {
	@Autowired
	NotesRepository notesRepository;
	
	public Notes save(Notes std) {
		return notesRepository.save(std);
	}
	public List<Notes> findAll(){
		return notesRepository.findAll();
	}
	public Notes findOne(Integer id) {
		return notesRepository.findOne(id);
	}
	public void delete(Notes std) {
		notesRepository.delete(std);
	}
	public List<Notes> findByUserEmail(String emailId) {
		return notesRepository.findByEmailId(emailId);
	}
}
