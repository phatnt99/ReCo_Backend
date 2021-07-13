package com.dcat.ReCo.vos;

public class MovieVO {
	private int    movieId;
	private String movieTitle;
	private int    action;
	private int    adventure;
	private int    animation;
	private int    children;
	private int    comedy;
	private int    crime;
	private int    documentary;
	private int    drama;
	private int    fantasy;
	private int    filmNoir;
	private int    horror;
	private int    musical;
	private int    mystery;
	private int    romance;
	private int    sciFi;
	private int    thriller;
	private int    war;
	private int    western;
	
	public static MovieVO parse(String row) {
		String[] strs = row.split("\\|");
		MovieVO mvo = new MovieVO();
		mvo.setMovieId(Integer.parseInt(strs[0]));
		mvo.setMovieTitle(strs[1]);
		mvo.setAction(Integer.parseInt(strs[5]));
		mvo.setAdventure(Integer.parseInt(strs[6]));
		mvo.setAnimation(Integer.parseInt(strs[7]));
		mvo.setChildren(Integer.parseInt(strs[8]));
		mvo.setComedy(Integer.parseInt(strs[9]));
		mvo.setCrime(Integer.parseInt(strs[10]));
		mvo.setDocumentary(Integer.parseInt(strs[11]));
		mvo.setDrama(Integer.parseInt(strs[12]));
		mvo.setFantasy(Integer.parseInt(strs[13]));
		mvo.setFilmNoir(Integer.parseInt(strs[14]));
		mvo.setHorror(Integer.parseInt(strs[15]));
		mvo.setMusical(Integer.parseInt(strs[16]));
		mvo.setMystery(Integer.parseInt(strs[17]));
		mvo.setRomance(Integer.parseInt(strs[18]));
		mvo.setSciFi(Integer.parseInt(strs[19]));
		mvo.setThriller(Integer.parseInt(strs[20]));
		mvo.setWar(Integer.parseInt(strs[21]));
		mvo.setWestern(Integer.parseInt(strs[22]));		
		
		return mvo;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getAdventure() {
		return adventure;
	}

	public void setAdventure(int adventure) {
		this.adventure = adventure;
	}

	public int getAnimation() {
		return animation;
	}

	public void setAnimation(int animation) {
		this.animation = animation;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public int getComedy() {
		return comedy;
	}

	public void setComedy(int comedy) {
		this.comedy = comedy;
	}

	public int getCrime() {
		return crime;
	}

	public void setCrime(int crime) {
		this.crime = crime;
	}

	public int getDocumentary() {
		return documentary;
	}

	public void setDocumentary(int documentary) {
		this.documentary = documentary;
	}

	public int getDrama() {
		return drama;
	}

	public void setDrama(int drama) {
		this.drama = drama;
	}

	public int getFantasy() {
		return fantasy;
	}

	public void setFantasy(int fantasy) {
		this.fantasy = fantasy;
	}

	public int getFilmNoir() {
		return filmNoir;
	}

	public void setFilmNoir(int filmNoir) {
		this.filmNoir = filmNoir;
	}

	public int getHorror() {
		return horror;
	}

	public void setHorror(int horror) {
		this.horror = horror;
	}

	public int getMusical() {
		return musical;
	}

	public void setMusical(int musical) {
		this.musical = musical;
	}

	public int getMystery() {
		return mystery;
	}

	public void setMystery(int mystery) {
		this.mystery = mystery;
	}

	public int getRomance() {
		return romance;
	}

	public void setRomance(int romance) {
		this.romance = romance;
	}

	public int getSciFi() {
		return sciFi;
	}

	public void setSciFi(int sciFi) {
		this.sciFi = sciFi;
	}

	public int getThriller() {
		return thriller;
	}

	public void setThriller(int thriller) {
		this.thriller = thriller;
	}

	public int getWar() {
		return war;
	}

	public void setWar(int war) {
		this.war = war;
	}

	public int getWestern() {
		return western;
	}

	public void setWestern(int western) {
		this.western = western;
	}

}
