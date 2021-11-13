//Holden Davis
//CSCI 3381 - CRN 18741
//Project 3 - Dr. Doderer - Fall 2021

package CSCI3381Project3Package;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CSCI3381Project3Servlet
 */
@WebServlet("/CSCI3381Project3Servlet")
public class CSCI3381Project3Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static TweetCollection tweets;
	private ArrayList<String> users;
    private HashMap<String, ArrayList<Integer>>predictionData;
    private String activeuser;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CSCI3381Project3Servlet() {
        super();
        tweets = new TweetCollection("./CSCI3381Project3Package/testProcessed.txt");
        users = tweets.getallusers();
        predictionData = tweets.createPredictionData();   
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Going back to login from main
		if (request.getParameter("mainindex") != null) {
			RequestDispatcher rd=request.getRequestDispatcher("/index.html");    
			rd.forward(request,response); 
		}
		//Logging in from login page
		else if (request.getParameter("indexmain") != null) {
			//Valid, welcome
			if (request.getParameter("indexusername").equals("md") && request.getParameter("indexpassword").equals("pw")) {
				String label1 = "userlist";
				String label1Value = "<select size=\"10\" name=\"users\">";
				for (String u: users) {
					label1Value += "<option value=\"" + u + "\">" + u + "</option>";
				}
				label1Value += "</select>";
				request.setAttribute(label1, label1Value);
				RequestDispatcher rd=request.getRequestDispatcher("/main.jsp");    
				rd.forward(request,response); 
			}
			//Invalid login
			else {
				response.getWriter().append("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><body>" + "\n"
						+ "<script type=\"text/javascript\">" + "\n"
						+ "alert('Invalid Login!')" + "\n"
						+ "window.location.href='index.html'" + "\n"
						+ "</script>" + "\n"
						+ "</body></html>");
			}
		}
		//Going to main from post
		else if (request.getParameter("postmain") != null) {
			users = tweets.getallusers();
			String label1 = "userlist";
			String label1Value = "<select size=\"10\" name=\"users\">";
			for (String u: users) {
				label1Value += "<option value=\"" + u + "\">" + u + "</option>";
			}
			label1Value += "</select>";
			request.setAttribute(label1, label1Value);
			RequestDispatcher rd=request.getRequestDispatcher("/main.jsp");    
			rd.forward(request,response); 
		}
		//Going to main from tweets
		else if (request.getParameter("tweetsmain") != null) {
			users = tweets.getallusers();
			String label1 = "userlist";
			String label1Value = "<select size=\"10\" name=\"users\">";
			for (String u: users) {
				label1Value += "<option value=\"" + u + "\">" + u + "</option>";
			}
			label1Value += "</select>";
			request.setAttribute(label1, label1Value);
			RequestDispatcher rd=request.getRequestDispatcher("/main.jsp");    
			rd.forward(request,response); 
		}
		//Going to post from main
		else if (request.getParameter("mainpost") != null) {
			activeuser = request.getParameter("users");
			RequestDispatcher rd=request.getRequestDispatcher("/post.jsp");    
			rd.forward(request,response); 
		}
		//Going to tweets from main via search
		else if (request.getParameter("mainsearch") != null) {
			activeuser = request.getParameter("users");
			//Searching by ID
			if (request.getParameter("mainsearchtype").equals("id")) {
				try {
					String thing = request.getParameter("searchfield");
					Long thing2 = Long.parseLong(thing);
					Tweet t = tweets.searchByID(thing2);
					String label2 = "tweetlist";
					String label2Value = "<select size=\"10\" name=\"tweets\">";
					label2Value += "<option value=\"" + t + "\">" + t + "</option>";
					label2Value += "</select>";
					request.setAttribute(label2, label2Value);
					RequestDispatcher rd=request.getRequestDispatcher("/tweets.jsp");    
					rd.forward(request,response); 
				} catch (Exception e) {
					response.getWriter().append("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><body>" + "\n"
							+ "<script type=\"text/javascript\">" + "\n"
							+ "alert('ID must be valid integer!')" + "\n"
							+ "window.location.href='CSCI3381Project3Servlet?indexusername=md&indexpassword=pw&indexmain=Login'" + "\n"
							+ "</script>" + "\n"
							+ "</body></html>");
				}
			}
			//Searching by User
			else if (request.getParameter("mainsearchtype").equals("user")) {
				String thing = request.getParameter("searchfield");
				ArrayList<Tweet> t = tweets.searchByUser(thing);
				String label2 = "tweetlist";
				String label2Value = "<select size=\"10\" name=\"tweets\">";
				for (Tweet t2: t) {
					label2Value += "<option value=\"" + t2 + "\">" + t + "</option>";
				}
				label2Value += "</select>";
				request.setAttribute(label2, label2Value);
				RequestDispatcher rd=request.getRequestDispatcher("/tweets.jsp");    
				rd.forward(request,response); 
			}
			
		}
		//Going to tweets from main via view
		else if (request.getParameter("mainview") != null) {
			activeuser = request.getParameter("users");
			String chosenuser = request.getParameter("users");

			ArrayList<Tweet> usertweets = tweets.searchByUser(chosenuser);
			String label2 = "tweetlist";
			String label2Value = "<select size=\"10\" name=\"tweets\">";
			for (Tweet t: usertweets) {
				label2Value += "<option value=\"" + t + "\">" + t + "</option>";
			}
			label2Value += "</select>";
			request.setAttribute(label2, label2Value);
			
			RequestDispatcher rd=request.getRequestDispatcher("/tweets.jsp");    
			rd.forward(request,response);
		}
		//Posted tweet
		else if (request.getParameter("postpost") != null) {
			int polarity = 2;
			String user = request.getParameter("User");
			String content = request.getParameter("Content");
			Long id = null;
			if (request.getParameter("polarities").equals("4")) {
				polarity = 2;
			}
			else if (request.getParameter("polarities").equals("0")){
				polarity = 0;
			}
			if (request.getParameter("autogencheck") != null) {
				id = tweets.genID();
				Tweet newtweet = new Tweet(polarity, id, user, content);
				tweets.addTweet(newtweet);
			}
			else try {
				id = Long.parseLong(request.getParameter("ID"));
				Tweet newtweet = new Tweet(polarity, id, user, content);
				tweets.addTweet(newtweet);
			} catch (Exception e) {
				response.getWriter().append("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><body>" + "\n"
						+ "<script type=\"text/javascript\">" + "\n"
						+ "alert('ID must be a valid integer!')" + "\n"
						+ "window.location.href='post.jsp'" + "\n"
						+ "</script>" + "\n"
						+ "</body></html>");
			}
			RequestDispatcher rd=request.getRequestDispatcher("/post.jsp");    
			rd.forward(request,response);
		}
		//Deleting Single
		else if (request.getParameter("delete") != null && request.getParameter("deletetype").equals("Single")) {
			try {
				String tweettodie = request.getParameter("tweets");
				String[] tweetparts = tweettodie.split(",");
				Tweet t2 = new Tweet(Integer.parseInt(tweetparts[0]), Long.parseLong(tweetparts[1]), tweetparts[2], tweetparts[3]);
				tweets.removeTweet(t2);
				
				String chosenuser = activeuser;
				ArrayList<Tweet> usertweets = tweets.searchByUser(chosenuser);
				String label2 = "tweetlist";
				String label2Value = "<select size=\"10\" name=\"tweets\">";
				for (Tweet t: usertweets) {
					label2Value += "<option value=\"" + t + "\">" + t + "</option>";
				}
				label2Value += "</select>";
				request.setAttribute(label2, label2Value);
				RequestDispatcher rd=request.getRequestDispatcher("/tweets.jsp");    
				rd.forward(request,response);
			} catch (Exception e) {
				response.getWriter().append("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><body>" + "\n"
						+ "<script type=\"text/javascript\">" + "\n"
						+ "alert('Choose a tweet to delete!')" + "\n"
						+ "window.location.href='CSCI3381Project3Servlet?indexusername=md&indexpassword=pw&indexmain=Login'" + "\n"
						+ "</script>" + "\n"
						+ "</body></html>");
			}
		}
		//Deleting All
		else if (request.getParameter("delete") != null && request.getParameter("deletetype").equals("All")) {
			String chosenuser2 = activeuser;
			ArrayList<Tweet> utwts = tweets.searchByUser(chosenuser2);
			for (Tweet t : utwts) {
				tweets.removeTweet(t);
			}
			
			String chosenuser = activeuser;
			ArrayList<Tweet> usertweets = tweets.searchByUser(chosenuser);
			String label2 = "tweetlist";
			String label2Value = "<select size=\"10\" name=\"tweets\">";
			for (Tweet t: usertweets) {
				label2Value += "<option value=\"" + t + "\">" + t + "</option>";
			}
			label2Value += "</select>";
			request.setAttribute(label2, label2Value);
			RequestDispatcher rd=request.getRequestDispatcher("CSCI3381Project3Servlet?indexusername=md&indexpassword=pw&indexmain=Login");    
			rd.forward(request,response);
		}
		//Predicting Single
		else if (request.getParameter("predict") != null && request.getParameter("predicttype").equals("Single")) {
			try {
			String tweettopredict = request.getParameter("tweets");
			String[] tweetparts = tweettopredict.split(",");
			Tweet t2 = new Tweet(Integer.parseInt(tweetparts[0]), Long.parseLong(tweetparts[1]), tweetparts[2], tweetparts[3]);
			int result = tweets.predict(t2, predictionData);
			System.out.println(result);
			
			response.getWriter().append("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><body>" + "\n"
					+ "<script type=\"text/javascript\">" + "\n"
					+ "alert('Predicting a polarity of " + result + " for the tweet: " + tweettopredict + "')" + "\n"
					+ "window.location.href='CSCI3381Project3Servlet?indexusername=md&indexpassword=pw&indexmain=Login'" + "\n"
					+ "</script>" + "\n"
					+ "</body></html>");
			} catch (Exception e) {
				response.getWriter().append("<html><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"><body>" + "\n"
						+ "<script type=\"text/javascript\">" + "\n"
						+ "alert('Select a tweet to predict on!')" + "\n"
						+ "window.location.href='CSCI3381Project3Servlet?indexusername=md&indexpassword=pw&indexmain=Login'" + "\n"
						+ "</script>" + "\n"
						+ "</body></html>");
			}
		}
		//Predicting All
		else if (request.getParameter("predict") != null && request.getParameter("predicttype").equals("All")) {
			HashMap<String, ArrayList<Integer>>predictionData = tweets.createPredictionData();
			String[] output = tweets.judgeAccuracy(predictionData);
			String label2 = "tweetlist";
			String label2Value = "<select size=\"10\" name=\"tweets\">";
			for (String t: output) {
				label2Value += "<option value=\"" + t + "\">" + t + "</option>";
			}
			label2Value += "</select>";
			request.setAttribute(label2, label2Value);
			RequestDispatcher rd=request.getRequestDispatcher("/tweets.jsp");    
			rd.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
