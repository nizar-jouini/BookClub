package com.nizar.bookclub.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nizar.bookclub.models.LoginUser;
import com.nizar.bookclub.models.User;
import com.nizar.bookclub.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

	// Add once service is implemented:
	@Autowired
	private UserService userServ;

	// DISPALY ROUTE - forms
	@GetMapping("/")
	public String index() {
		return "redirect:/login";
	}

//	DISPLAY ROUTES
	@GetMapping("/login")
	public String login(Model model) {

		// Bind empty LoginUser object to the JSP
		// to capture the form input

		model.addAttribute("newLogin", new LoginUser());
		return "login.jsp";
	}
	
	@GetMapping("/register")
	public String newRegister(Model model) {

		// Bind empty User object to the JSP
		// to capture the form input

		model.addAttribute("newUser", new User());
		return "register.jsp";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

//		session.setAttribute("userId", null);

		session.invalidate();

		return "redirect:/login";
	}

//	ACTION ROUTES
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser,
			BindingResult result,
			Model model,
			HttpSession session) {

		// TO-DO Later -- call a register method in the service
		// to do some extra validations and create a new user!
		// 1. Execute the Service to Register first
		userServ.register(newUser, result);

		if (result.hasErrors()) {
			// Be sure to send in the empty LoginUser before
			// re-rendering the page.
//			model.addAttribute("newLogin", new LoginUser());
			return "register.jsp";
		}

		// No errors!
		// Store their ID from the DB in session,
		// in other words, log them in.
		session.setAttribute("user_id", newUser.getId());

		return "redirect:/books";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
			BindingResult result,
			Model model,
			HttpSession session) {

		// Add once service is implemented:
		User user = userServ.login(newLogin, result);

		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "login.jsp";
		}

		// No errors!
		// Store their ID from the DB in session,
		// in other words, log them in.
		session.setAttribute("user_id", user.getId());

		return "redirect:/books";
	}

}
