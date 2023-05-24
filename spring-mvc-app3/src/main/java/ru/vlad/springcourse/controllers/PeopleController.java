package ru.vlad.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
//import jakarta.validation.Valid;
import ru.vlad.springcourse.dao.PersonDAO;
import ru.vlad.springcourse.models.Person;
import ru.vlad.springcourse.repositories.PeopleRepository;
import ru.vlad.springcourse.services.BooksService;
import ru.vlad.springcourse.services.PeopleService;
import ru.vlad.springcourse.util.PersonValidator;


@Controller
@RequestMapping("/people")
public class PeopleController {

   // private final PersonDAO personDAO;
    private final PersonValidator personValidator;
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public PeopleController(BooksService booksService, PersonValidator personValidator, PeopleService peopleService)  {
       // this.personDAO = personDAO;
        this.personValidator=personValidator;
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
    	if(id==1 || peopleService.findOne(id)==null) return "redirect:/people"; //making sure that an inexistent person or our the object with id=1 used to determinate null won't be accesed
        model.addAttribute("person", peopleService.findOne(id));
       // model.addAttribute("books", booksService.getBooksByPersonId(id));
        model.addAttribute("books", booksService.getBooksByPersonId(peopleService.findOne(id).getId())); //change NO1

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, 
    		BindingResult bindingResult) {
    	personValidator.validate(person, bindingResult);
    	if(bindingResult.hasErrors())
    		return "people/new";
    		
    		
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
    	if(id==1 || peopleService.findOne(id)==null) return "redirect:/people"; //making sure that an inexistent person or our the object with id=1 used to determinate null won't be accesed
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person")@Valid Person person,  
    		BindingResult bindingResult, @PathVariable("id") int id) {
    	personValidator.validate(person, bindingResult);
    	if(bindingResult.hasErrors()) return "people/edit";
    	
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}