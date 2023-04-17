/*
 * **************************************************************
 * **************                                  **************
 * **************      COPYRIGHT INFORMATION       **************
 * **************                                  **************
 * **************************************************************
 *_______________________#########______________________________*
 *______________________############____________________________*
 *_____________________##############___________________________*
 *_____________________###############__________________________*
 *____________________#################_________________________*
 *____________________###_########__####________________________*
 *___________________###__##########_####_______________________*
 *__________________####__###########_####______________________*
 *_________________#####_############__#####____________________*
 *________________######_###__########___#####__________________*
 *_______________#####___###___########___######________________*
 *______________######___###__###########___######______________*
 *_____________######___####_##############__######_____________*
 *____________#######__#####################_#######____________*
 *____________#######__##############################___________*
 *___________#######__######_#################_#######__________*
 *___________#######__######_######_#########___######__________*
 *___________#######____##__######___######_____######__________*
 *___________#######________######____#####_____#####___________*
 *____________######________#####_____#####_____####____________*
 *_____________#####________####______#####_____###_____________*
 *______________#####______;###________###______#_______________*
 *_______________##_______####________####_____________________*
 * **************************************************************
 * **************                                  **************
 * **************         信赛博佛祖 拜初音未来     **************
 * **************                                  **************
 * **************************************************************
 */
package demo.v2;

import demo.v2.util.Resource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainApp extends Application
{
	Scene mainScene;
	Stage mainStage;
	@Override
	public void start(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Resource.path_MainWindowFXML));
		mainScene = new Scene(loader.load());
		
		mainStage = stage;
		mainStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource(Resource.path_MainIcon).toExternalForm())));
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	public static void main(String[] args) {launch(args);}
}
