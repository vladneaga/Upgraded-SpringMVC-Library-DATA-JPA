package ru.vlad.springcourse.repositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.vlad.springcourse.models.Book;
import ru.vlad.springcourse.models.Person;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
	
	
	
	List<Book> findByOwner(Person owner);
	 
	 
	  
	  @Query(value = "UPDATE book b SET b.personId=1 WHERE b.id=?1", nativeQuery = true)
	  public int release(int id);
	  
	  @Query(value = "UPDATE book b SET b.personId = ?1 WHERE b.id = ?2", nativeQuery = true)
	  public void assign(int personId, int id);
	  
	  List<Book> findByTitleContaining(String key);
	
	  
	  Page<Book> findAll(Pageable var1);
}
