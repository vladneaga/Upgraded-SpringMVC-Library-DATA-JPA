package ru.vlad.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.vlad.springcourse.models.Book;
import ru.vlad.springcourse.models.Person;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;
   
    private final JdbcTemplate jdbcTemplate;
    
    
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
		
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * private static final String URL=
	 * "jdbc:mysql://localhost:3307/spring_mvc_app?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC";
	 * private static final String USERNAME="root"; private static final String
	 * PASSWORD = "root"; private static Connection connection = null;
	 * 
	 * 
	 * static {
	 * 
	 * try { Class.forName("com.mysql.cj.jdbc.Driver");
	 * }catch(ClassNotFoundException ex) { ex.printStackTrace();}
	 * 
	 * try { connection= DriverManager.getConnection(URL, USERNAME, PASSWORD); }
	 * catch (SQLException e) { e.printStackTrace();}
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
    
    

	/*
	 * { people = new ArrayList<>();
	 * 
	 * people.add(new Person(++PEOPLE_COUNT, "Tom", 24, "tom@mail.com"));
	 * people.add(new Person(++PEOPLE_COUNT, "Bob", 52, "bob@mail.com"));
	 * people.add(new Person(++PEOPLE_COUNT, "Mike", 66, "mike@mail.com"));
	 * people.add(new Person(++PEOPLE_COUNT, "Katy", 19, "katy@mail.com")); }
	 */
    public List<Person> index() {
    	String query="SELECT * FROM person WHERE id!=1";
    	return jdbcTemplate.query(query, new PersonMapper());
    }

    public Person show(int id) {
        
    	return this.jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[] {id}, new PersonMapper()).
    			stream().findAny().orElse(null);
    }

    public void save(Person person) {
      this.jdbcTemplate.update("INSERT INTO person(name, age, email, address) VALUES(?, ?, ?, ?)", 
   		   person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    	
    	
    }

    public void update(int id, Person updatedPerson) {
		
     this.jdbcTemplate.update("UPDATE person SET name=?, age=?, email=?, address=? WHERE id=?", updatedPerson.getName(),
    			updatedPerson.getAge(), updatedPerson.getEmail(), updatedPerson.getAddress(), id);
    }

    public void delete(int id) {
    	
    	//We set  the person id to 1 first, because after deleting the person its id will be null.
    	  this.jdbcTemplate.update("UPDATE book SET person_id=1 WHERE person_id=?", id);
      this.jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    
    }
    
    
    
	/*
	 * public Person show(String email) { return
	 * this.jdbcTemplate.query("SELECT * FROM person WHERE email=?", new Object[]
	 * {email}, new PersonMapper()).stream().findAny().orElse(null);
	 * 
	 * }
	 */
    
    
    public Optional<Person> getPersonByFullName(int id)
    {
    	return this.jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[] {id}, new PersonMapper()).stream().findAny();
    }
    public List<Book> getBooksByPersonId(int id)
    {
    	return this.jdbcTemplate.query("SELECT * FROM book WHERE person_id=?", new Object[] {id}, new BeanPropertyRowMapper<>(Book.class));
    }
    
    
    

    
    
}