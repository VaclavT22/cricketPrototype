package com.taska.java.crickets.cricketfarm.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

import org.springframework.stereotype.Component;

@Component
public class NumberVerifier {

	public boolean isAbleToParseFloat(String toFloat) {
		try {
			Float.parseFloat(toFloat);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isAbleToParseDecimal(String toDecimal) {
		try {
			DecimalFormat df = new DecimalFormat();
			df.parse(toDecimal);
			return true;
		} catch(ParseException e) {
			return false;
		}
	}
}
