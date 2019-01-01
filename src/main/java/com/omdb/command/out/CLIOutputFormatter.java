package com.omdb.command.out;

import com.omdb.rest.model.ErrorType;
import com.omdb.rest.model.MovieRatingResponse;

public class CLIOutputFormatter {

	public static void format(MovieRatingResponse rating) {

		if (rating.getType() == null) {

			System.out.println("Source:" + rating.getSource());
			System.out.println("Title:"+rating.getTitle());
			System.out.println("Ratings"+rating.getRating());
		} else if(rating.getType() == ErrorType.HTTP){
			System.out.println(rating.getErrorMessage());
		}else if(rating.getType() == ErrorType.FALSE_RESPONSE){
			System.out.println(rating.getErrorMessage()+"!!!");
		}else if(rating.getType() == ErrorType.INPUT){
			System.out.println(rating.getErrorMessage()+", Please enter valid movie title");
		}else if(rating.getType() == ErrorType.CONNECTIVITY){
			System.out.println("ERROR"+rating.getErrorMessage());
		}

	}

}
