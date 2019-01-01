package com.omdb.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.jayway.jsonpath.JsonPath;
import com.omdb.exception.OMDBHttpException;
import com.omdb.exception.OMDBInvalidInputException;
import com.omdb.rest.model.ErrorType;
import com.omdb.rest.model.MovieRatingResponse;

import net.minidev.json.JSONArray;

public class OMDBRestClient {
	
    private static final String MOVIE_SOURCE = "Rotten Tomatoes";
	private static final String FALSE_RESPONSE = "False";
	private static String jsonPath="$.Ratings[?(@.Source == \"Rotten Tomatoes\")].Value";	
	private static final String API_KEY="ee7e581c";
	private static final String BASE_URL="http://www.omdbapi.com/?t=";
	
	public static MovieRatingResponse getMovieRating(String movie) {

		MovieRatingResponse response = new MovieRatingResponse();

		try {

			String result = call(movie);
			response = ResponseHandler.handleResponse(result);

		} catch (OMDBInvalidInputException e) {
			response.setType(ErrorType.INPUT);
			response.setErrorMessage(e.getMessage());
		} catch (OMDBHttpException e) {
			response.setType(ErrorType.HTTP);
			response.setErrorMessage(e.getMessage());
		} catch (IOException e) {
			response.setType(ErrorType.CONNECTIVITY);
			response.setErrorMessage("Not able to connect, please check connectivity");
		}
		return response;
	}
	
	private static String call(String movie) throws IOException {

		if (movie == null || movie.length() == 0) {
			throw new OMDBInvalidInputException("Invalid input");
		}

		HttpURLConnection conn = null;
		StringBuffer sb = new StringBuffer();

		try {

			movie = URLEncoder.encode(movie, "UTF-16");

			URL url = new URL(BASE_URL + movie + "&apikey="+API_KEY);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				String httpErrorMessage=getHttpErrorMessage(conn.getResponseCode());
				throw new OMDBHttpException(httpErrorMessage);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output = null;
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}

		} finally {
			conn.disconnect();
		}

		return sb.toString();
	}
	
	private static String getHttpErrorMessage(int responseCode) {
		String message=null;

		switch(responseCode) {
		
		    case  401 :
		    	message="Invalid API Key";
		    	break;
		    	
            case  408 :
		    	message="The server timed out waiting for the request.";
		    	break;
		}
		
		return message;
	}

	static class ResponseHandler{
		
		public static MovieRatingResponse handleResponse( String result) {
			
			MovieRatingResponse r=new MovieRatingResponse();
			String apiResponse = JsonPath.read(result, "$.Response");

			if (FALSE_RESPONSE.equalsIgnoreCase(apiResponse)) {
				String errorMessage = JsonPath.read(result, "$.Error");
				r.setType(ErrorType.FALSE_RESPONSE);
				r.setErrorMessage(errorMessage);

			} else {

				JSONArray arr = JsonPath.read(result, jsonPath);
				r.setSource(MOVIE_SOURCE);
				
				String title = JsonPath.read(result, "$.Title");
                r.setTitle(title);
				
				if(arr !=null && arr.size() > 0 && arr.get(0) !=null) {
				   r.setRating((String) arr.get(0));
				}else {
				   r.setType(ErrorType.FALSE_RESPONSE);
				   r.setErrorMessage("Movie Review not available");
				}
			}
			
			return r;
		}
	}
	

}
