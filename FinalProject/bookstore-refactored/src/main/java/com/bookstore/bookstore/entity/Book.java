package com.bookstore.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String author;
	private double price;

	// Required for JPA
	public Book() {
		super();
	}

	// Private constructor for Builder
	private Book(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.author = builder.author;
		this.price = builder.price;
	}

	// Static method to get a new Builder instance
	public static Builder builder() {
		return new Builder();
	}

	// Getters and Setters (kept for JPA and existing code compatibility)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// Static nested Builder class
	public static class Builder {
		private int id;
		private String name;
		private String author;
		private double price;

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder author(String author) {
			this.author = author;
			return this;
		}

		public Builder price(double price) {
			this.price = price;
			return this;
		}

		public Book build() {
			return new Book(this);
		}
	}
}
