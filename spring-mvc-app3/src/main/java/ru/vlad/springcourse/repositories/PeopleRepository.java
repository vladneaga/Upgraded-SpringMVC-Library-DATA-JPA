package ru.vlad.springcourse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.vlad.springcourse.models.Person;

public interface PeopleRepository extends JpaRepository<Person, Integer>{

	List<Person> findByIdGreaterThan(int id);
	

	
}
