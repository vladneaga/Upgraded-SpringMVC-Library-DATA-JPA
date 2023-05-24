package ru.vlad.springcourse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.vlad.springcourse.models.Book;
import ru.vlad.springcourse.models.Person;
import ru.vlad.springcourse.repositories.BooksRepository;
import ru.vlad.springcourse.repositories.PeopleRepository;

@Service
@Transactional(readOnly = true)
public class BooksService {
	
	private final BooksRepository booksRepository;
	private final PeopleRepository peopleRepository;
	
	
	@Autowired
	public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
		super();
		this.booksRepository = booksRepository;
		this.peopleRepository = peopleRepository;
	}

 
public List<Book> findAll() {
	return this.booksRepository.findAll();
	
}

@Transactional
public Book findOne(int id) {
	return this.booksRepository.findById(id).orElse(null); //// Change null to 1
}

	@Transactional
	public void save(Book book) {
		this.booksRepository.save(book);
	}
	
	@Transactional
	public void update(int id, Book updatedBook) {
		updatedBook.setId(id);
		this.booksRepository.save(updatedBook);
	}
	
	@Transactional
	public void delete(int id) {
		this.booksRepository.deleteById(id);
	}
	
	public List<Book> findByOwner(Person owner) {
		return this.booksRepository.findByOwner(owner);
	}
	public List<Book> getBooksByNameSearchKey(String key) {
		return this.booksRepository.findByTitleContaining(key);
	}
	
	public List<Book> getBooksByPersonId(int personId) {
	    Person owner = this.peopleRepository.findById(personId).get();
	    return booksRepository.findByOwner(owner);
	}


	public Person getBookOwner(int bookId) {
        Optional<Book> optionalBook = booksRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return book.getOwner();
     
        }
        return null;
    }
		@Transactional
		public void release(int id) {
			Optional<Book> book = this.booksRepository.findById(id);
			book.get().setOwner(peopleRepository.findById(1).get());
		
	
			
		}
		@Transactional
		public void assign(int id, Person selectedPerson)
		{
			Optional<Book> book = this.booksRepository.findById(id);
			book.get().setOwner(selectedPerson);
			
			
		}


		public Page<Book> findBooks(Pageable of ) {
		return this.booksRepository.findAll(of);
			
		}


		
		
		
		
		
		
		
		
	
}