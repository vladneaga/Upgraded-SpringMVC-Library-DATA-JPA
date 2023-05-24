package ru.vlad.springcourse.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.vlad.springcourse.repositories.PeopleRepository;
import ru.vlad.springcourse.services.PeopleService;

@Entity
@Table(name = "book")
public class Book {
	 
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "author")
	private String author;
	@Column(name = "year")
	private int year;
	@ManyToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	Person owner;
	@Transient int personid;
	public Book() {}

	public Book(String title, String author, int year) {
		super();
		this.title = title;
		this.author = author;
		this.year = year;
		
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

public int getPerson_id() {
		return this.owner.getId();
	}

	public void setPersonId(int person_id) {
		this.owner.setId(person_id);
	}

	public Person getOwner() {
		return this.owner;
	}
	
	public void setOwner(Person person) {
		this.owner = person;
	}

	public int getPersonid() {
		return personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
	}
	

}
