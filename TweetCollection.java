package CSCI3381Project3Package;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

public class TweetCollection {
	
	private HashMap<Long, Tweet> TweetCollection;
	
	//Parameterized constructor
	public TweetCollection(String fileName) {
		TweetCollection = new HashMap<Long, Tweet>();
		this.readIn(fileName);
	}
	
	//Read in from a file given by a parameter, each line becomes a new tweet and is added to the TweetCollection
	//Also counts the number of tweets read in and shows the updated number of tweets in TweetCollection
	public String readIn(String fileName) {
		int i = 0;
		BufferedReader lineReader = null;
		try {
			FileReader fr = new FileReader(fileName);
			lineReader = new BufferedReader(fr);
			String line = null;
			while ((line = lineReader.readLine())!=null) {
				String[] input = line.split(",");
				int polarity = Integer.parseInt(input[0]);
				long ID = Long.parseLong(input[1]);
				String user = input[2];
				String content = input[3];
				this.addTweet(new Tweet(polarity, ID, user, content));
				i++;
				}
		} catch (Exception e) {
			System.err.println("\n!---File Error, Trying Different Read Type---!\n");
			try {
				lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName.substring(1))));
				String line = null;
				while ((line = lineReader.readLine())!=null) {
					String[] input = line.split(",");
					int polarity = Integer.parseInt(input[0]);
					long ID = Long.parseLong(input[1]);
					String user = input[2];
					String content = input[3];
					this.addTweet(new Tweet(polarity, ID, user, content));
					i++;
					}
			} catch (Exception e2) {
				System.err.println("\n!---No Such File or Format Error---!\n");
				return ("\n!---No Such File or Format Error---!\n");
			} finally {
				if (lineReader != null)
					try {
						lineReader.close();
					} catch (IOException e2) {
						System.err.println("\\n!---Could Not Close Buffered Reader---!\\n");
					}
			}			
		} finally {
			if (lineReader != null)
				try {
					lineReader.close();
				} catch (IOException e) {
					System.err.println("\\n!---Could Not Close Buffered Reader---!\\n");
				}
		}
		return ("\n!---Read " + i + " Tweets From " +  fileName + ", TweetCollection Now Contains " + TweetCollection.size() + " Unique Tweets---!\n");

	}
	
	//Write out to a file given by a parameter, data written the exact same way as was read in so input and output files can be used interchangeably 
	//Also counts the number of tweets written out
	public String writeOut(String fileName) {
		long i = 0;
		try
		{
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter myOutfile = new BufferedWriter(fw);		
			Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
			while(twitterator.hasNext()){
				i++;
				HashMap.Entry<Long, Tweet> tweet = twitterator.next();
				myOutfile.write(tweet.getValue().getPolarity() + "," + tweet.getValue().getID() + "," + tweet.getValue().getUser() + "," + tweet.getValue().getContent() + "\n");
			}
			myOutfile.flush();
			myOutfile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("\n!---Did Not Save To " + fileName + "---!\n");
		}
		return ("\n!---Wrote " + i + " Tweets To " +  fileName + "---!\n");
	}
	
	//Add tweet to collection and return it
	public Tweet addTweet(Tweet tweet) {
		TweetCollection.put(tweet.getID(), tweet);
		return tweet;
	}
	
	//Remove tweet from collection and return it
	public Tweet removeTweet(Tweet tweet) {
		TweetCollection.remove(tweet.getID());
		return tweet;
	}
	
	//Search for and return a tweet given the ID
	public Tweet searchByID(long ID) {
		Tweet tweet = TweetCollection.get(ID);
		return tweet;
	}
	
	//toString method, returns 200 tweets from TweetCollection (if that many exist)
	public String toString() {
		String toReturn = "";
		Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
		if (TweetCollection.size() < 200) {
			while(twitterator.hasNext()){
				HashMap.Entry<Long, Tweet> tweet = twitterator.next();
				toReturn += (tweet.getValue() + "\n");
			}
		}
		else {
			int i = 0;
			while(twitterator.hasNext() && i < 200){
				HashMap.Entry<Long, Tweet> tweet = twitterator.next();
				toReturn += (tweet.getValue() + "\n");
				i++;
			}
		}
		return toReturn;
	}
	
	//Search for and return an ArrayList of tweets by a given user
	//Match input user string to each tweet's user string
	public ArrayList<Tweet> searchByUser(String User) {	
		ArrayList<Tweet> userTweets = new ArrayList<Tweet>();
		Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
		while(twitterator.hasNext()){
			HashMap.Entry<Long, Tweet> tweet = twitterator.next();
			if (tweet.getValue().getUser().equals(User)) {
				userTweets.add(tweet.getValue());
			}
		}
		return userTweets;
	}
	
	//Return an ArrayList of all IDs in the TweetCollection
	public ArrayList<Long> retriveAll(){
		ArrayList<Long> allIDs = new ArrayList<Long>();
		Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
		while(twitterator.hasNext()){
			HashMap.Entry<Long, Tweet> tweet = twitterator.next();
			allIDs.add(tweet.getKey());
		}
		return allIDs;
 	}
	
	public ArrayList<String> getallusers() {
		ArrayList<String> users = new ArrayList<String>();
		for (Entry<Long, Tweet> t : TweetCollection.entrySet()) {
			users.add(t.getValue().getUser());
		}
		return users;
	}
	
	//Return a random tweet (for testing purposes)
	public Tweet randomTweet() {
		Random rand = new Random();
		Object[] allTweets= TweetCollection.values().toArray();
		Tweet randTweet = (Tweet) allTweets[rand.nextInt(allTweets.length)];
		return randTweet;
	}
	
	//Generate a new long that's not used already when posting a new tweet so no existing tweets get overwritten
	//Recursively generate new Longs until an unused one is found
	public Long genID() {
		Random rand = new Random();
		long range = 2193602129L;
		long ID = (long)(rand.nextDouble()*range);
		while (TweetCollection.containsKey(ID)) {
			genID();
		}
		return ID;
	}
	
	/*
	 * All of the code pertaining to the prediction methodology and algorithm (effectively everything below this comment) was written from scratch
	 * 
	 * The idea behind this model is as follows:
	 * 		
	 * 		TRAINING DATA
	 * 		For each Tweet object in the TweetCollection, split the "content" string into words (separated by spacebar input)
	 * 		For each word, add the Tweet's "polarity" int to an array for that specific word
	 * 		Effectively, the training becomes a hashmap where each key, represented by a string, is each word found in the TweetCollection
	 * 		Each key's corresponding value is an integer array
	 * 		The size of the array is the number of occurrences of that word, while the value of each element in the array is the polarity of a Tweet it was found in
	 * 		Therefore, we can effectively compute an average polarity for each word by summing the contents of the array and dividing by the number of elements
	 * 		
	 * 		PREDICTION
	 * 		For any given Tweet to predict on, its "content" string is similarly split
	 * 		Each word in the tweet is given the previously computed average polarity
	 * 		If the word is not found in the array, it is given a polarity of two
	 * 		The sum of these polarities is then divided by the number of words to return an average polarity of the Tweet
	 * 
	 * 		MODEL ACCURACY
	 * 		Judging the accuracy of the model takes a single parameter, the previously created prediction data
	 * 		It then runs the prediction on every Tweet in the TweetCollection that called it
	 * 		After a prediction returns a value, it is compared to the actual polarity of the Tweet
	 * 		In this manner, the number of correct and incorrect guesses can be tallied
	 * 		The ratio of these two numbers * 100 gives the percentage accuracy of the model on the TweetCollection that called it using the given prediction data
	 * 		In addition, the number of guessed vs actual tweets is tallied for each polarity to provide further data for analysis
	 */
	
	//Create prediction data from the TweetCollection that called it
	//Return a HashMap with string as a key and an ArrayList of Integers as the value
	public HashMap<String, ArrayList<Integer>> createPredictionData(){
		HashMap<String, ArrayList<Integer>> allWords = new HashMap<String, ArrayList<Integer>>();		
		Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
		while(twitterator.hasNext()){
			HashMap.Entry<Long, Tweet> tweet = twitterator.next();
			String[] stufftobreak = tweet.getValue().getContent().split(" ");
			for (String thing : stufftobreak) {
				if (allWords.containsKey(thing)){
					allWords.get(thing).add(tweet.getValue().getPolarity());
				}
				else {
					ArrayList<Integer> newAListInt = new ArrayList<Integer>();
					newAListInt.add(tweet.getValue().getPolarity());
					allWords.put(thing, newAListInt);
				}
			}
		}
		return allWords;
	}
	
	//Predict the polarity of a given tweet using the given prediction data
	//Return the predicted polarity of the tweet (0,2 or 4)
	public int predict(Tweet predictionTweet, HashMap<String, ArrayList<Integer>> predictionData) {	
		String[] tweetBroken = predictionTweet.getContent().split(" ");
		double words = 0.0;
		double totalscore = 0.0;
		for (String thing2: tweetBroken) {
			words++;
			if (predictionData.containsKey(thing2)) {
				double totalpolarity = 0;
				for (int polarities : predictionData.get(thing2)) {
					totalpolarity += polarities;
				}
				double averagepolarity = totalpolarity / predictionData.get(thing2).size();
				totalscore += averagepolarity;
			}
			else totalscore += 2;
		}
		double averagescore = 0;
		averagescore = (words/totalscore);
		if (averagescore < 1.33) {
			return 0;
		}
		else if (averagescore > 2.66) {
			return 4;
		}
		else return 2;
	}
	
	//Given the prediction data, judge the overall accuracy of the prediction methodology by testing predict on every tweet and tallying correct/incorrect guesses
	//Return a string array containing the % accuracy, as well as the raw values and a breakdown by polarity
	public String[] judgeAccuracy(HashMap<String, ArrayList<Integer>> predictionData) {
		int negativeguess = 0;
		int neutralguess = 0;
		int positiveguess = 0;
		int negativereal = 0;
		int neutralreal = 0;
		int positivereal = 0;
		int correct = 0;
		int incorrect = 0;
		Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
		while(twitterator.hasNext()){
			HashMap.Entry<Long, Tweet> tweet = twitterator.next();
			int prediction = this.predict(tweet.getValue(), predictionData);
			if (prediction == 0) {
				negativeguess++;
			}
			else if (prediction == 4) {
				positiveguess++;
			}
			else neutralguess++;
			if (tweet.getValue().getPolarity() == 0) {
				negativereal++;
			}
			else if (tweet.getValue().getPolarity() == 4)
			{
				positivereal++;
			}
			else neutralreal++;
			if (prediction == tweet.getValue().getPolarity()) {
				correct++;
			}
			else if (prediction != tweet.getValue().getPolarity()) {
				incorrect++;
			}
		}
		String[] toreturn = {"Overall model accuracy: " + correct + " correct, " + incorrect + " incorrect, " + ((double)correct/((double)(incorrect + correct))) * 100 + " % accuracy",
				"***** Polarity 4 Tweets: " + positiveguess + " guessed, " + positivereal + " actual",
				"***** Polarity 2 Tweets: " + neutralguess + " guessed, " + neutralreal + " actual",
				"***** Polarity 0 Tweets: " + negativeguess + " guessed, " + negativereal + " actual"};
		return toreturn;
	}
	//Return a 0, 2, or 4 at random
	public int fakepredict(Tweet predictionTweet, HashMap<String, ArrayList<Integer>> predictionData) {	
		Random rand = new Random();
		int randreturn = rand.nextInt(1000);
		if (randreturn > 0 && randreturn < 333) {
			return 0;
		}
		else if (randreturn > 333 && randreturn < 666) {
			return 2;
		}
		else return 4;
	}
	
	//judgeAccuracy but calling fakepredict instead of predict
	public String[] fakejudgeAccuracy(HashMap<String, ArrayList<Integer>> predictionData) {
		int negativeguess = 0;
		int neutralguess = 0;
		int positiveguess = 0;
		int negativereal = 0;
		int neutralreal = 0;
		int positivereal = 0;
		int correct = 0;
		int incorrect = 0;
		Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
		while(twitterator.hasNext()){
			HashMap.Entry<Long, Tweet> tweet = twitterator.next();
			int prediction = this.fakepredict(tweet.getValue(), predictionData);
			if (prediction == 0) {
				negativeguess++;
			}
			else if (prediction == 4) {
				positiveguess++;
			}
			else neutralguess++;
			if (tweet.getValue().getPolarity() == 0) {
				negativereal++;
			}
			else if (tweet.getValue().getPolarity() == 4)
			{
				positivereal++;
			}
			else neutralreal++;
			if (prediction == tweet.getValue().getPolarity()) {
				correct++;
			}
			else if (prediction != tweet.getValue().getPolarity()) {
				incorrect++;
			}
		}
		String[] toreturn = {"Overall model accuracy: " + correct + " correct, " + incorrect + " incorrect, " + ((double)correct/((double)(incorrect + correct))) * 100 + " % accuracy",
				"***** Polarity 4 Tweets: " + positiveguess + " guessed, " + positivereal + " actual",
				"***** Polarity 2 Tweets: " + neutralguess + " guessed, " + neutralreal + " actual",
				"***** Polarity 0 Tweets: " + negativeguess + " guessed, " + negativereal + " actual"};
		return toreturn;
	}
	public void writeFileRunnable(String fn, String filePath) { 
		  String path = ""; 
		  try 
		  { 
		   String[] tokens = filePath.split("\\\\"); 
		   for (int i = 0; i < tokens.length-1; i++) { 
		    path = path + tokens[i] + "\\"; 
		   }     
		   FileWriter fw = new FileWriter(path+fn); 
		   BufferedWriter myOutFile = new BufferedWriter(fw); 
		   Iterator<Entry<Long, Tweet>> twitterator = TweetCollection.entrySet().iterator();
			while(twitterator.hasNext()){
				HashMap.Entry<Long, Tweet> tweet = twitterator.next();
				myOutFile.write(tweet.getValue().getPolarity() + "," + tweet.getValue().getID() + "," + tweet.getValue().getUser() + "," + tweet.getValue().getContent() + "\n");
			}
			myOutFile.flush();
			myOutFile.close();
		  } 
		  catch (Exception e) { 
		   e.printStackTrace(); 
		   System.err.println("Didn't save to " +path+ fn);    
		  } 
		 }
}
