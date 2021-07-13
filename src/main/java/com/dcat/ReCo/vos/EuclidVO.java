package com.dcat.ReCo.vos;

public class EuclidVO {
	private int    movieId1;
	private int    movieId2;
	private String movieTitle1;
	private String movieTitle2;
	private double distance;

	public int getMovieId1() {
		return movieId1;
	}

	public void setMovieId1(int movieId1) {
		this.movieId1 = movieId1;
	}

	public int getMovieId2() {
		return movieId2;
	}

	public void setMovieId2(int movieId2) {
		this.movieId2 = movieId2;
	}

	public String getMovieTitle1() {
		return movieTitle1;
	}

	public void setMovieTitle1(String movieTitle1) {
		this.movieTitle1 = movieTitle1;
	}

	public String getMovieTitle2() {
		return movieTitle2;
	}

	public void setMovieTitle2(String movieTitle2) {
		this.movieTitle2 = movieTitle2;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
