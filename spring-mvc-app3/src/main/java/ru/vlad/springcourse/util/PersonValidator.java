package ru.vlad.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.vlad.springcourse.dao.PersonDAO;
import ru.vlad.springcourse.models.Person;
@Component
public class PersonValidator implements Validator {
	
private final PersonDAO personDAO;
@Autowired
	public PersonValidator(PersonDAO personDAO) {
	super();
	this.personDAO = personDAO;
}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Person person = (Person)target;
		if( !person.getEmail().contains("@"))
		{
			errors.rejectValue("email", "", "This email is not valid.");
		}
		if(person.getName()==""|| person.getName().length()<2)
		{
			errors.rejectValue("name", "", "Name not valid");
		}
		if(person.getAge()<0)
		{
			errors.rejectValue("age", "", "Age not valid");
		}
		if(!person.getAddress().matches("[A-Z]\\w+, [A-Z]\\w+, \\d{6}"))
		{
			errors.rejectValue("address", "", "The should be in this form: Country, City, post code(6 digits)");
		}

}
}