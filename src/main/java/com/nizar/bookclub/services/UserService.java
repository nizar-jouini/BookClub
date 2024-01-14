package com.nizar.bookclub.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nizar.bookclub.models.LoginUser;
import com.nizar.bookclub.models.User;
import com.nizar.bookclub.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	// TO-DO: Write register and login methods!
	public User register(User newUser, BindingResult result) {

		// Reject if email is taken
		Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());

		if (potentialUser.isPresent()) {

			result.rejectValue("email", "registerErrors", "Email is Taken");
		}

		// Does the entered password match the confirmation password?
		if (!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("password", "registerErrors", "Password doesn't match");
		}

		if (result.hasErrors()) {
			return null;
		} else {
			// Hash and Set Password
			String hashedPW = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(hashedPW);
			// Save the User to database
			return userRepo.save(newUser);
		}

	}

	public User login(LoginUser newLoginObject, BindingResult result) {

		// Check if user is in DB
		Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());

		if (!potentialUser.isPresent()) {

			result.rejectValue("email", "loginErrors", "Email is not found");
		} else {

			User user = potentialUser.get();
			// Check Password
			if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
				result.rejectValue("password", "loginErrors", "Invalid Password");
			}
			if (result.hasErrors()) {
				return null;
			} else {
				return user;
			}
		}

		return null;
	}

	public User findById(Long id) {
		Optional<User> potentialUser = userRepo.findById(id);
		if (potentialUser.isPresent()) {
			return potentialUser.get();
		}
		return null;
	}

}
