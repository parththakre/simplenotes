package com.springboothibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboothibernate.model.Notes;


public interface NotesRepository extends JpaRepository<Notes, Integer> {

	List<Notes> findByEmailId(String emailId);
}
