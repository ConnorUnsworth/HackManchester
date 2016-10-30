package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

import image.ImageDataEncoder;
import image.ImageExpander;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Main extends Application implements Initializable{


	@FXML
	private Button btnBrowseImage;
	@FXML
	private Button btnBrowseSound;
	@FXML
	private Button btnTwitterSignIn;
	@FXML
	private Button btnTweetImage;
	@FXML
	private Button btnSaveImage;
	@FXML
	private Button btnHideMessage;
	@FXML
	private Button btnGenerateKeys;
	@FXML
	private Button btnDecodeImage;


	@FXML
	private TextField txtImagePath;
	@FXML
	private TextField txtSoundPath;

	@FXML
	private TextArea txtMessageToHide;

	@FXML
	private ImageView imgLoadedImage;
	@FXML
	private ImageView imgHiddenMessage;

	@FXML
	private Text txtErrMsg;


	IOController ioController = new IOController();
	CustomBufferedImageStorage storage;
	JavaTweet tweet = new JavaTweet();
	

	@Override
	public void start(Stage primaryStage) {



		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/MainUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Scene scene = new Scene(root, 900, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Shhh");
			primaryStage.setScene(scene);


			primaryStage.setOnCloseRequest(e -> Platform.exit());

			primaryStage.show();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {

		launch(args);
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub



		btnBrowseImage.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				importImage();
			}
		});

		btnBrowseSound.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				importSound();
			}
		});

		btnHideMessage.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				saveEncryptedMessage();
				
				btnTwitterSignIn.setDisable(false);
				btnTweetImage.setDisable(false);
			}
		});
		
		btnSaveImage.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				ioController.saveImgToPath(imgHiddenMessage.getImage());
			}
		});

		Image twitterIcon = new Image(getClass().getResourceAsStream("resources/twitter.png"));
		btnTwitterSignIn.setGraphic(new ImageView(twitterIcon));
		btnTwitterSignIn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {


				tweet.oauth();
			}
		});

		btnTweetImage.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				tweet.tweet(ioController.getConvertedFile());
			}
		});
		
		btnGenerateKeys.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				//generateKeys();
			}
		});
		
		btnDecodeImage.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {

				//DECODE IMAGE
				
				decodeImg();
			}
		});

	}

	protected void decodeImg() {
		// TODO Auto-generated method stub
		
		BufferedImage imageToDecode = storage.getImage();
		
		ImageExpander imgExpander = new ImageExpander();
		ImageDataEncoder imgDataEncoder = new ImageDataEncoder();
		
		Byte[] byteArr = imgDataEncoder.decodeImage(
				imgExpander.expand(
						imgExpander.recoverKeyImage(
								imageToDecode)), imageToDecode
				);
		
	
		
		
		//Byte[] messageArr = txtMessageToHide.getText().getBytes();
		byte[] msgArr = new byte[byteArr.length];
		int i = 0;
		for(Byte b : byteArr)
		{
			msgArr[i++] = b;
		}
		
	//String s = new String(msgArr);
//		
//		txtMessageToHide.setText(s);
		
		
		//String s = new String("Plz Convert me lol");
		
		
//		RSA rsa = new RSA();
		
//		String s = new String(msgArr);
//		System.out.println(s);
		
		//try {
			//String string = new String(rsa.decrypt(msgArr), Charset.forName("UTF-8"));
			
			
			txtMessageToHide.setText(new String(msgArr));
			
//		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
//				| BadPaddingException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}


//	protected void generateKeys() {
//		// TODO Auto-generated method stub
////		RSA rsa = new RSA();
//		try {
//			rsa.generateNewKeys();
//		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}


	protected void saveEncryptedMessage() {

		System.out.println("ENCRYPT MESSAGE");
		//ANTON AND ALEX

		ImageExpander iExpander = new ImageExpander();
		ImageDataEncoder idEncoder = new ImageDataEncoder();
		
		
//		RSA  rsa = new RSA();
		
		byte[] messageArr = txtMessageToHide.getText().getBytes();
		
	////ENCRYPTION////
//		byte[] finalMsgArray = null;
//		try {
//			finalMsgArray = rsa.encrypt(messageArr);
//			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
//					| BadPaddingException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		
/////////////////
		
		
		Byte[] messageArrObjs = new Byte[messageArr.length];
		int i = 0;
		for(byte b : messageArr)
		{
			messageArrObjs[i++] = b;
		}
		
		
		
		
		
		
		
		ioController.tempSaveNewImage(idEncoder.encode(iExpander.expand(storage.getImage()), messageArrObjs));

		imgHiddenMessage.setImage(ioController.getNewImage());


		txtErrMsg.setVisible(true);
		txtErrMsg.setText("Message Hidden");

	}
	



	protected void importImage() {
		storage = ioController.getBufferedImage();
		txtImagePath.setText(storage.getFilePath());
		File file =new File(storage.getFilePath());
		Image image = new Image(file.toURI().toString());
		imgLoadedImage.setImage(image);
		
		btnHideMessage.setDisable(false);
		btnDecodeImage.setDisable(false);

	}

	protected void importSound() {
		// TODO Auto-generated method stub
		System.out.println("Import Audio File");
		CustomSoundStorage storage = ioController.getSound();
		txtSoundPath.setText(storage.getFilePath());

		File file = new File(storage.getFilePath());

		//Load Sound into here

	}



}
