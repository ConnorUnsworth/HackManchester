package application;
	
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
private Button btnHideMessage;

@FXML
private TextField txtImagePath;
@FXML
private TextField txtSoundPath;

@FXML
private TextArea txtMessageToHide;

@FXML
private ImageView imgLoadedImage;

@FXML
private Text txtErrMsg;
	
IOController ioController = new IOController();

	@Override
	public void start(Stage primaryStage) {
		
	
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/MainUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
		
			Scene scene = new Scene(root, 600, 350);
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
			}
		});
			
	}


	

	protected void saveEncryptedMessage() {

		System.out.println("ENCRYPT MESSAGE");
		//ANTON AND ALEX
		
		txtErrMsg.setVisible(true);
		txtErrMsg.setText("Message Hidden");
		
	}


	protected void importImage() {
	 	CustomBufferedImageStorage storage = ioController.getBufferedImage();
	 	txtImagePath.setText(storage.getFilePath());
	 	File file =new File(storage.getFilePath());
	 	Image image = new Image(file.toURI().toString());
	 	imgLoadedImage.setImage(image);
	 	
	}
	
	protected void importSound() {
		// TODO Auto-generated method stub
		System.out.println("Import Audio File");
		CustomSoundStorage storage = ioController.getSound();
		txtSoundPath.setText(storage.getFilePath());
		
	 	File file =new File(storage.getFilePath());


		//Load Sound into here
		
	}

	

}