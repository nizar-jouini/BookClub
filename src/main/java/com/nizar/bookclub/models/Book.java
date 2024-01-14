package com.nizar.bookclub.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="books")
public class Book {

	// MEMBER VARIABLES
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		@NotEmpty(message = "* Title is required!")
		private String title;
		
		@NotEmpty(message = "* Description is required!")
		@Size(min = 5, message = "* Description must be at least 5 characters")
		private String description;
		
		@Column(updatable = false)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date createdAt;

		@DateTimeFormat(pattern = "yyyy-MM-dd")
		private Date updatedAt;
		
		// M:1
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "user_id")
		private User user;
		
		// M:M
		@ManyToMany(fetch = FetchType.LAZY)
		@JoinTable(
				name = "favorite_books",
				joinColumns = @JoinColumn(name = "book_id"),
				inverseJoinColumns = @JoinColumn(name = "user_id")
			)
			private List<User> usersWhoFavor;
		
		// ZERO ARGS CONSTRUCTOR
		public Book() {
			
		}
		
		// ALL ARGS CONSTRUCTOR
		public Book(String title, String description) {
			
			this.title = title;
			this.description = description;
		}

		// GETTERS & SETTERS
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Date getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}

		public Date getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<User> getUsersWhoFavor() {
			return usersWhoFavor;
		}

		public void setUsersWhoFavor(List<User> usersWhoFavor) {
			this.usersWhoFavor = usersWhoFavor;
		}

		// Save the date before the object is created
		@PrePersist
		protected void onCreate() {
			this.createdAt = new Date();
		}

		// Save the date on every update
		@PreUpdate
		protected void onUpdate() {
			this.updatedAt = new Date();
		}
		
}


















