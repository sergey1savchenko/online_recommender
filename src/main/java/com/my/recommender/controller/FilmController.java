package com.my.recommender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.my.recommender.service.FilmService;
import com.my.recommender.service.RatingService;
import com.my.recommender.service.RecommenderService;
import com.my.recommender.service.UserService;

@Controller
@SessionAttributes("id")
public class FilmController {
	
	@Autowired
	FilmService filmService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RatingService ratingService;
	
	@Autowired
	RecommenderService recommenderService;
	
	@GetMapping( "user/allFilms" )
	public String allFilms(Model model) {
		model.addAttribute("films", filmService.getAllFilmsWithAvgRating());
		return "allFilms";
	}
	
	@GetMapping( "user/myFilms" )
	public String myFilms(Model model, @SessionAttribute int id) {
		model.addAttribute("films", userService.getUserFilmsWithAvgRatingAndUserRating(id));
		return "myFilms";
	}
	
	@GetMapping( "user/topRecommended" )										// top 10 movies for current user
	public String topRecommended(Model model, @SessionAttribute int id) {
		model.addAttribute("films", filmService.getTopRecommended(id));
		return "topRecommended";
	}
	
	@GetMapping( "user/film/{filmId}" )
	public String allFilms(Model model, @PathVariable("filmId") int filmId, @SessionAttribute int id) {
		model.addAttribute("film", filmService.getFilmByIdWithAvgRating(filmId));
		model.addAttribute("prediction", recommenderService.getPrediction(id, filmId));
		model.addAttribute("real", ratingService.getRating(id, filmId));
		return "film";
	}
	
	
}
