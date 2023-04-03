/***
 * @author TritiumQun
 * @version alpha0.1
 *
 */
package demo.v1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.controlsfx.control.NotificationPane;

import java.io.File;

public class demoController {
	@FXML
	private VBox vbox;
	@FXML
	private MenuBar m_bar;
	
	@FXML
	private Menu menu;
	
	@FXML
	private MenuItem m_open, m_close, m_about;
	
	@FXML
	private ImageView v_image;
	
	@FXML
	private Label l_address;
	
	@FXML
	public void OnClickExitAction()
	{
		System.exit(0);
	}
	
	@FXML
	public void OnClickOpenAction()
	{
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Image File");
		chooser.setInitialDirectory(new File(System.getProperty("user.home")));
		chooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("GIF", "*.gif"),
				new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				new FileChooser.ExtensionFilter("PNG", "*.png")
		);
		File imageFile = chooser.showOpenDialog(null);
		if(imageFile != null)
		{
			String imagePath = imageFile.getPath();
			setImage(imagePath);
		}
	}
	
	@FXML
	public void OnClickAboutAction()
	{
		/*
	}
		try
		{
			Text groupMember = new Text("小组成员：aaa, bbb, ccc \n");
			groupMember.setFont(Font.loadFont("file:src/main/resources/Fonts/smiley-sans-v1.1.1/SmileySans-Oblique.ttf", 12));
			groupMember.setFill(Color.BLUE);
			
			TextFlow about = new TextFlow();
			about.getChildren().add(groupMember);
			about.setTextAlignment(TextAlignment.CENTER);
			
			VBox vbox = new VBox(about);
			vbox.setAlignment(Pos.CENTER);
			
			Scene newScene = new Scene(vbox, 240, 160);
			
			Stage stage = new Stage();
			
			stage.setTitle("Image Browser demoVersion");
			stage.getIcons().add(new Image("file:"+"src/main/resources/UIElements/icon1.png"));
			stage.setScene(newScene);
			stage.initStyle(StageStyle.UNDECORATED);
			
			stage.show();
		} catch (Exception e) { e.printStackTrace(); }
		*/
		NotificationPane pane = new NotificationPane(vbox);
		Label l = new Label("CESHICEHSI");
		vbox.getChildren().add(l);
		pane.setText("WARING");
		pane.show();
		System.out.println(pane.getText());
		
	}
	
	@FXML
	private void setImage(String path)
	{
		try
		{
			Image img = new Image("file:" + path);
			v_image.setImage(img);
			l_address.setText(path);
		}catch(Exception e) { e.printStackTrace(); }
	}
}