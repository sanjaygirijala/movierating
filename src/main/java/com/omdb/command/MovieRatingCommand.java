package com.omdb.command;
import com.omdb.command.out.CLIOutputFormatter;
import com.omdb.rest.client.OMDBRestClient;
import com.omdb.rest.model.MovieRatingResponse;

public class MovieRatingCommand implements Command{
	private String[] cmdParams;
	
	public MovieRatingCommand(String[] cmdParams){
		this.cmdParams=cmdParams;
	}

	public void execute() {

		MovieRatingResponse rating=OMDBRestClient.getMovieRating(cmdParams[0]);
		CLIOutputFormatter.format(rating);
		
	}

	

}
