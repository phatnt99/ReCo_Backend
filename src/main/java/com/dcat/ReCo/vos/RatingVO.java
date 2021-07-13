package com.dcat.ReCo.vos;

import java.io.Serializable;

public class RatingVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;

	private int movieId;

	private float rating;

	private int like;

	public static RatingVO parseRating(String str) {

		String[] fields = str.split("\t");

		int userId = Integer.parseInt(fields[0]);

		int movieId = Integer.parseInt(fields[1]);

		float rating = Float.parseFloat(fields[2]);

		if (rating > 3) return new RatingVO(userId, movieId, rating, 1);

		return new RatingVO(userId, movieId, rating, 0);

	}

	public RatingVO() {
		super();
	}

	public RatingVO(int userId, int movieId, float rating, int like) {
		super();
		this.userId  = userId;
		this.movieId = movieId;
		this.rating  = rating;
		this.like    = like;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

}