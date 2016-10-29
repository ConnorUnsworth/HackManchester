package application;

import java.awt.Desktop;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.prefs.Preferences;

import javafx.scene.control.TextInputDialog;
import twitter4j.StatusUpdate;
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
	private Preferences twitterAuth;

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
			
			try {
				storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}

	private void storeAccessToken(long useId, AccessToken accessToken){
		twitterAuth.put("access_token", accessToken.getToken());
		twitterAuth.put("access_token_secret", accessToken.getTokenSecret());

		//store accessToken.getToken()
		//store accessToken.getTokenSecret()
	}

	public void tweet(File imageToSend)
	{
		try
		{


			twitter.setOAuthConsumer(consumerKeyStr, consumerSecretStr);
			//				 	AccessToken accessToken = new AccessToken(accessTokenStr,
			//			             accessTokenSecretStr);
			String accessTokenStr = twitterAuth.get("access_token", null);
			String accessTokenSecretStr = twitterAuth.get("access_token_secret", null);

			if(accessTokenStr != null)
			{
				AccessToken accessToken = new AccessToken(accessTokenStr, accessTokenSecretStr); 
				twitter.setOAuthAccessToken(accessToken);
				//twitter.updateStatus("#hackmanchester test");
				
				StatusUpdate statusUpdate = new StatusUpdate("Nothing to see here");
				statusUpdate.setMedia(imageToSend);
				twitter.updateStatus(statusUpdate);

				System.out.println("Successfully updated the status in Twitter.");
			}
			else
			{
				System.out.println("Issue with auth token");
			}

		}
		catch (TwitterException te) 

		{

			te.printStackTrace();
		}

	}
}