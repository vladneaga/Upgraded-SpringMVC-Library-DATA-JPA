package ru.vlad.springcourse.util;

import java.time.LocalDate;
import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import ru.vlad.springcourse.dao.BookDAO;
import ru.vlad.springcourse.models.Book;

@Component
public class BookValidator implements Validator {
	
//private final BookDAO bookDAO;
@Autowired
	public BookValidator(BookDAO bookDAO) {
	super();
//	this.bookDAO=bookDAO;
	
}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Book.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Book book = (Book)target;
		
		if(book.getYear()<1600)
		{
			errors.rejectValue("year", "", "The book was written before 1600, we accept only books after 1600.");
		}
		if(book.getYear()>LocalDate.now().getYear())
		{
			errors.rejectValue("year", "", "The book hasn't been writeen yet! Hello to the future:" + book.getYear());
		}

}
}