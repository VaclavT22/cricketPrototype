package com.taska.java.crickets.cricketfarm.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateVerifier {

	public boolean isDateCorrect(String dateString) {
		try {
			LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			return true;
		} catch (Exception e){
			return false;
		}
	}
}
