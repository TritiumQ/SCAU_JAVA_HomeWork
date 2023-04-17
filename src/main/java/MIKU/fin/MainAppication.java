package MIKU.fin;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainAppication extends Application
{
	private Stage currentStage;
	@Override
	public void start(Stage stage) throws Exception
	{
		currentStage = stage;
		
		
		currentStage.show();
	}
	
}
