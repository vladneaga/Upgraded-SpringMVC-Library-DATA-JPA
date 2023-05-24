package ru.vlad.springcourse.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.vlad.springcourse.dao.BookDAO;
import ru.vlad.springcourse.dao.PersonDAO;
import ru.vlad.springcourse.models.Book;
import ru.vlad.springcourse.models.Person;
import ru.vlad.springcourse.services.BooksService;
import ru.vlad.springcourse.services.PeopleService;
import ru.vlad.springcourse.util.BookValidator;
import ru.vlad.springcourse.util.PersonValidator;

@Controller
@RequestMapping("/books")
public class BooksController {
	private final PeopleService peopleService;
	private final BooksService booksService;
	BookValidator bookValidator;
	private final BookDAO bookDao;
	
	@Autowired
	public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator, BookDAO bookDao) {
		super();
		this.booksService = booksService;
		this.peopleService = peopleService;
		this.bookValidator=bookValidator;
		this.bookDao = bookDao;
	}
	
	@GetMapping()
	public String index(Model model)
	{
		model.addAttribute("books", booksService.findAll());
		int itemsPerPage=this.booksService.findAll().size(); // For page division
		int page=-1;  //For page division
		model.addAttribute("itemsPerPage", itemsPerPage); //For page division
		model.addAttribute("page", page); // For page division
		
		String searchKey="";
		model.addAttribute("searchKey", searchKey);
		return "books/index";
	}
	
	@PostMapping("/search")
	public String search(Model model, @RequestParam("searchKey") String searchKey)
	{
		if(searchKey.equals("")) return index(model); //return user to the the view with all pages if the search key is empty
		model.addAttribute("searchRes", this.booksService.getBooksByNameSearchKey(searchKey));
		model.addAttribute("searchKey", searchKey);
		return "books/indexSearch";
	}
	//We receive the number of items per page the user wants to receive, 
	//to be able to divide the number of books to the number selected by user.
	//After that we show him the first page of (itemsPerPage) books and allow the user to switch the pages.
	@PostMapping()
	public String indexPostPage(Model model, @RequestParam("itemsPerPage")int itemsPerPage)
	{
		if(itemsPerPage==-1) return "redirect:/books";
		//List of number of pages to be represented 
		List<Integer> pages = new ArrayList<>();
		for(int i = 0; i <= this.booksService.findAll().size()/itemsPerPage; i++) {
			pages.add(i);
		}
		String searchKey = "";
		model.addAttribute("searchKey", searchKey); //for search 
		//System.out.println(itemsPerPage); 
		model.addAttribute("pages", pages); //number of pages to be represented in a line
		model.addAttribute("books", booksService.findBooks(PageRequest.of(0, itemsPerPage, Sort.by("year"))).getContent());
		model.addAttribute("itemsPerPage", itemsPerPage);
		return "books/indexPage";
	}
	
	@GetMapping("/results/{page}/{itemsPerPage}")
	public String indexPageResults(@PathVariable("page") int page, @PathVariable("itemsPerPage") int itemsPerPage, Model model)
	{
		 if (booksService.findBooks(PageRequest.of(page, itemsPerPage)).getContent() == null) 
			 return "redirect:/people";
		
		 
		 List<Integer> pages = new ArrayList<>();
			for(int i = 0; i <= this.booksService.findAll().size()/itemsPerPage; i++) {
				pages.add(i);
			}
		 model.addAttribute("pages", pages);
		 
		model.addAttribute("books", booksService.findBooks(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent());
		return "books/indexPage";
	}
	
//*Not included*	
//custom possibility for user to divide select the number of books in a view and the number of respective page in the URL
	@GetMapping("/{page}/{itemsPerPage}")
	public String indexPage(@PathVariable("page") int page, @PathVariable("itemsPerPage") int itemsPerPage, Model model)
	{
		 if (booksService.findBooks(PageRequest.of(page, itemsPerPage)).getContent() == null) 
			 return "redirect:/people";
		
		model.addAttribute("books", booksService.findBooks(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent());
		return "books/index";
	}
	
	
	
	
	@GetMapping("/{id}")
	public String show(@PathVariable("id")int id, Model model, @ModelAttribute("person")Person person)
	{
		if( booksService.findOne(id)==null) return "redirect:/books"; //making sure that an inexistent book won't be accesed
		model.addAttribute("book", booksService.findOne(id));
		//Optional<Person> bookOwner = booksService.getBookOwner(id);
		Person bookOwner = booksService.getBookOwner(id);
		if(bookOwner!=null && bookOwner.getId()!=1) // ID 1 in the people table is an instrument to help determinate books with no owners.
		{
			model.addAttribute("owner", bookOwner);
		} else
			model.addAttribute("people", peopleService.findAll());
		return "books/show";
	}
	
	@GetMapping("/new")
	public String newBook(@ModelAttribute("book")Book book, Model model)
	{
		model.addAttribute("people",peopleService.findAll());
		model.addAttribute("nullPerson", peopleService.findOne(1));
		return "/books/new";
	}
	@PostMapping("/new")
	public String create(@ModelAttribute("book")Book book, BindingResult bindingResult) 
	{
		bookValidator.validate(book, bindingResult);
		if(bindingResult.hasErrors()) return "books/new";
		book.setOwner(this.peopleService.findOne(book.getPersonid()));
	 this.booksService.save(book);
	return "redirect:/books";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(Model model, @ModelAttribute("book")Book book,
			BindingResult bindingResult, @PathVariable("id")int id)
	{
		
		if( booksService.findOne(id)==null) return "redirect:/books"; //making sure that an inexistent book won't be accesed
		model.addAttribute("book", booksService.findOne(id));
		return "/books/edit";
	}
	
	@PatchMapping("/{id}")
	public String editBook(@PathVariable("id")int id, 
			@ModelAttribute("book")Book book, BindingResult bindingResult)
	{
		this.bookValidator.validate(book, bindingResult);
		if(bindingResult.hasErrors()) return "books/edit";
	booksService.update(id,book);
	return "redirect:/books";
	}
	
	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable("id")int id)
	{
		booksService.delete(id);
		return "redirect:/books";
	}
	@PatchMapping("/{id}/release")
	public String release(@PathVariable("id")int id)
	{
		booksService.release(id);
		return "redirect:/books";
	}
	
	@PatchMapping("/{id}/assign")
	public String assign(@PathVariable("id")int id, @ModelAttribute("person")Person selectedPerson, @ModelAttribute("book")Book book)
	{
	booksService.assign(id,selectedPerson);	
	return "redirect:/people/"+selectedPerson.getId();
	}
	
	
	

}
