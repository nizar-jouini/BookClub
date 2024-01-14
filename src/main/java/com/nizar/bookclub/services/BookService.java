package com.nizar.bookclub.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nizar.bookclub.models.Book;
import com.nizar.bookclub.repositories.BookRepository;

@Service
public class BookService {

//	C R U D

	@Autowired
	private BookRepository bookRepo;

//	READ ALL
	public List<Book> allbooks() {		
		return bookRepo.findAll();
	}

//	CREATE
	public Book createbook(Book b) {
		return bookRepo.save(b);
	}

//	READ ONE
	public Book findbook(Long id) {
		Optional<Book> maybebook = bookRepo.findById(id);
		if (maybebook.isPresent()) {
			return maybebook.get();
		} else {
			return null;
		}
	}

//	UPDATE A Book
	public Book updatebook(Book b) {
		return bookRepo.save(b);
	}

//	DELETE A Book
	public void deletebook(Long id) {
		bookRepo.deleteById(id);
	}
	
}
