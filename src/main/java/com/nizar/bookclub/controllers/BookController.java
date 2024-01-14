package com.nizar.bookclub.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.nizar.bookclub.models.Book;
import com.nizar.bookclub.models.User;
import com.nizar.bookclub.services.BookService;
import com.nizar.bookclub.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class BookController {

	@Autowired
	private BookService bookServ;

	@Autowired
	private UserService userServ;

//	----------- Display Routes ---------------
//	Display all books
	@GetMapping("/books")
	public String books(Model model, @ModelAttribute("newBook") Book newBook, HttpSession session) {

		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {
			User user = userServ.findById(userId);
			model.addAttribute("user", user);

			List<Book> all_books = bookServ.allbooks();
			// Sort the list by Created at using a custom comparator
			Collections.sort(all_books, Comparator.comparing(Book::getCreatedAt).reversed());
			model.addAttribute("books", all_books);

			return "books/home.jsp";
		}
	}

	// Add to Favorites
	@GetMapping("/books/favorites/{id}")
	public String addToFavorites(@PathVariable("id") Long id, HttpSession session) {

		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {
			User user = userServ.findById(userId);
			Book thisBook = bookServ.findbook(id);
			thisBook.getUsersWhoFavor().add(user);
			// save changes in DB
			bookServ.updatebook(thisBook);
		}

		return "redirect:/books";
	}

	// Add to List of Favorites
	@GetMapping("/books/favorites/add/{id}")
	public String addToListOfFavorites(@PathVariable("id") Long id, HttpSession session) {

		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {
			User user = userServ.findById(userId);
			Book thisBook = bookServ.findbook(id);
			thisBook.getUsersWhoFavor().add(user);
			// save changes in DB
			bookServ.updatebook(thisBook);
		}

		return "redirect:/books/" + id;
	}

	// Remove from List of Favorites
	@GetMapping("/books/favorites/remove/{id}")
	public String removeFromListOfFavorites(@PathVariable("id") Long id, HttpSession session) {

		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {
			User user = userServ.findById(userId);
			Book thisBook = bookServ.findbook(id);
			thisBook.getUsersWhoFavor().remove(user);
			// save changes in DB
			bookServ.updatebook(thisBook);
		}

		return "redirect:/books/" + id;
	}

//	Display a book details
	@GetMapping("/books/{id}")
	public String oneBook(@PathVariable("id") Long id, Model model, @ModelAttribute("updatedBook") Book updatedBook,
			HttpSession session) {

		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {
			Book oneBook = bookServ.findbook(id);
			User user = userServ.findById(userId);
			model.addAttribute("user", user);
			model.addAttribute("oneBook", oneBook);

			if (!oneBook.getUser().equals(user)) {

				return "books/showBookForViewer.jsp";
			}
		}

		return "books/showBookForCreator.jsp";
	}

//	----------- Action Routes ---------------
//	Create Book
	@PostMapping("/books/new")
	public String createBook(@Valid @ModelAttribute("newBook") Book newBook, BindingResult result, Model model,
			HttpSession session) {
		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {

			if (result.hasErrors()) {
				User user = userServ.findById(userId);
				model.addAttribute("user", user);

				List<Book> all_books = bookServ.allbooks();
				// Sort the list by Created at using a custom comparator
				Collections.sort(all_books, Comparator.comparing(Book::getCreatedAt).reversed());
				model.addAttribute("books", all_books);
				return "books/home.jsp";
			} else {
				Long UserID = (Long) session.getAttribute("user_id");

				User loggedinUser = userServ.findById(UserID);
				newBook.setUser(loggedinUser);
				loggedinUser.getBooksFavorited().add(newBook);

				try {
					// save the book in DB
					bookServ.createbook(newBook);
				} catch (Exception e) {
					// Handle uniqueness violation exception
					result.rejectValue("title", "error.title", "This Title is Used, Choose another");
					return "books/home.jsp";
				}

				return "redirect:/books";
			}
		}
	}

//	Update Book
	@PutMapping("/books/{id}/edit")
	public String updateThisBook(@Valid @ModelAttribute("updatedBook") Book updatedBook, BindingResult result,
			@PathVariable("id") Long id, HttpSession session) {

		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {

			if (result.hasErrors()) {
				return "books/showBookForCreator.jsp";
			} else {
				
				User user = userServ.findById(userId);
				Book thisBook = bookServ.findbook(id);
				updatedBook.setUser(user);
				updatedBook.setUsersWhoFavor(thisBook.getUsersWhoFavor());
				bookServ.updatebook(updatedBook);

				return "redirect:/books";
			}
		}

	}

//	Delete Book
	@DeleteMapping("/books/{id}/delete")
	public String delete(@PathVariable("id") Long id, HttpSession session) {

		// grab the user id from session
		Long userId = (Long) session.getAttribute("user_id");
		// ROUTE GUARD
		if (userId == null) {
			return "redirect:/";
		} else {
			
			bookServ.deletebook(id);
		}

		return "redirect:/books";
	}

}
