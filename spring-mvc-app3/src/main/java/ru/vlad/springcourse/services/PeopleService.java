package ru.vlad.springcourse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vlad.springcourse.models.Person;
import ru.vlad.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class PeopleService {
	
	private final PeopleRepository peopleReopsitory;
	
	
	@Autowired
	public PeopleService(PeopleRepository peopleReopsitory) {
		super();
		this.peopleReopsitory = peopleReopsitory;
	}

 
public List<Person> findAll() {
	return this.peopleReopsitory.findByIdGreaterThan(1);
	
}

public Person findOne(int id) {
    Optional<Person> foundPerson = this.peopleReopsitory.findById(id);
   
    		 return foundPerson.orElse(null);
}
	@Transactional
	public void save(Person person) {
		this.peopleReopsitory.save(person);
	}
	
	@Transactional
	public void update(int id, Person updatedPerson) {
		updatedPerson.setId(id);
		this.peopleReopsitory.save(updatedPerson);
	}
	
	@Transactional
	public void delete(int id) {
		this.peopleReopsitory.deleteById(id);
	}
	

}
