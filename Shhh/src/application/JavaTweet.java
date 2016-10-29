package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class JavaTweet {
	
	    static String consumerKeyStr = "Z3jX6kt8pC3JiFwdUdKY66gWg";
	    static String consumerSecretStr = "n7yp2HM5sUAUncbhr8AEOdwujzCUywQ2F8c5Ngh7VaeQ0hBUU6";
	    static String accessTokenStr = "391991747-hcvAHlf8f2AtVrqNukfixOwXl7TOsDc26nKqFpZY";
	    static String accessTokenSecretStr = "VUeQtw1ip3iQwo67H1YySMo1uTsqDup6mp7rwKCIKOm6f";
	    Twitter twitter;
	    	
	    public JavaTweet()
	    {
	    	twitter = new TwitterFactory().getSingleton();
	    }
	    public void oauth()
	    {
	       // twitter.setOAuthConsumer("[consumer key]", "[consumer secret]");
	    	twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
	        RequestToken requestToken = null;
	        
			try {
				requestToken = twitter.getOAuthRequestToken();
			} catch (TwitterException e1) {}
	        AccessToken accessToken = null;
	      
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        while (null == accessToken) {
	          
        	
        	
	    	  Desktop desktop = java.awt.Desktop.getDesktop();
	    	  URI oURL;
			try {
				oURL = new URI(requestToken.getAuthorizationURL()); 
				desktop.browse(oURL);
        	
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TextInputDialog dialog = new TextInputDialog("Pin");
        	dialog.setTitle("Text Input Dialog");
        	dialog.setHeaderText("Look, a Text Input Dialog");
        	dialog.setContentText("Please enter your name:");
	        	
        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent()){
        	    System.out.println("Your pin: " + result.get());
        
	    	 
//	        System.out.println("Open the following URL and grant access to your account:");
//	          System.out.println(requestToken.getAuthorizationURL());
//	          System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
	          
	          
	          
	          String pin = null;
			pin = result.get();
	          try{
	             if(pin.length() > 0){
	               accessToken = twitter.getOAuthAccessToken(requestToken, pin);
	             }else{
	               accessToken = twitter.getOAuthAccessToken();
	             }
	             System.out.println(accessToken);
	          } catch (TwitterException te) {
	            if(401 == te.getStatusCode()){
	              System.out.println("Unable to get the access token.");
	            }
	          }
	          }
	        }
	      }
	      
	    private static void storeAccessToken(long useId, AccessToken accessToken){
	        //store accessToken.getToken()
	        //store accessToken.getTokenSecret()
	      }
	    
		public void tweet()
		{
			 try
			    {
				 	
				    
				 	twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
				 	AccessToken accessToken = new AccessToken(accessTokenStr,
			             accessTokenSecretStr);
			
				 	twitter.setOAuthAccessToken(accessToken);
			
				 	twitter.updateStatus("#hackmanchester test");
			
				 	System.out.println("Successfully updated the status in Twitter.");
			    }
			 	catch (TwitterException te) 
			 
			 	{
			    	
			    	te.printStackTrace();
			    }
			 
		}
}