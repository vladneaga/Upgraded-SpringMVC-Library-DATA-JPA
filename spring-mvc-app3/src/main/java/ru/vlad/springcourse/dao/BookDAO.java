package ru.vlad.springcourse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.vlad.springcourse.models.Book;

import ru.vlad.springcourse.models.Person;

@Component
public class BookDAO {
	
	private final JdbcTemplate jdbcTemplate;
@Autowired
	public BookDAO(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	public List<Book> index()
	{
		return this.jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
	}
public Book show(int id) {
    
	return this.jdbcTemplate.query("SELECT * FROM book WHERE id=?", new Object[] {id}, new BeanPropertyRowMapper<>(Book.class)).
			stream().findAny().orElse(null);
}

public void save(Book book) {
  this.jdbcTemplate.update("INSERT INTO book(title, author, year, personId) VALUES(?, ?, ?,?)", 
		   book.getTitle(), book.getAuthor(), book.getYear(), book.getPerson_id());
	
	
}

public void update(int id, Book updatedBook) {
	
 this.jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?", updatedBook.getTitle(),
		 updatedBook.getAuthor(), updatedBook.getYear(), id);
}

public void delete(int id) {
	
  this.jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
}

public Optional<Person> getBookOwner(int id)
{
	return this.jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.personId = person.id WHERE book.id=?", 
			new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
}

public void release(int id)
{
	this.jdbcTemplate.update("UPDATE book SET personId=1 WHERE id=?", id);
}

public void assign(int id, Person selectedPerson)
{
	this.jdbcTemplate.update("UPDATE book SET personId=? WHERE id=?",  selectedPerson.getId(), id);
}




	
	

}
