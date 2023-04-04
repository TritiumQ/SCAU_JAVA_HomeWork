package demo.v1;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TestApp extends Application {
	VBox vBox1, vBox2;
	Button btn1, btn2, btn3;
	Scene scn1, scn2;
	Stage stg;
	int state = 1;
	int j = 0;
	@Override
	public void init()
	{
		/*通用*/
		btn2 = new Button("切换场景");
		btn2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				switchScene();
			}
		});
		btn3 = new Button("切换场景");
		btn3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				switchScene();
			}
		});
		/*场景1*/
		Label label1 = new Label(Integer.toString(j));
		label1.setTextAlignment(TextAlignment.CENTER);
		vBox1 = new VBox();
		btn1 = new Button("修改");
		btn1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				/*Stage stg = new Stage();
				VBox vBox = new VBox();
				Label label = new Label("这是一个测试语句");
				label.setTextAlignment(TextAlignment.CENTER);
				vBox.setAlignment(Pos.CENTER);
				vBox.getChildren().add(label);
				Scene scn = new Scene(vBox, 240, 240);
				stg.setScene(scn);
				stg.show();*/
				label1.setText(Integer.toString(++j));
			}
		});
		vBox1.setAlignment(Pos.CENTER);
		vBox1.getChildren().addAll(btn1,btn2,label1);
		scn1 = new Scene(vBox1, 320, 320);
		
		/*场景2*/
		vBox2 = new VBox();
		vBox2.setAlignment(Pos.CENTER);
		Label label2 = new Label("这是窗口2");
		vBox2.getChildren().addAll(label2, btn3);
		scn2 = new Scene(vBox2, 320, 320);
	}
	@Override
	public void start(Stage stage) throws Exception {
		stg = stage;
		stage.setScene(scn1);
		stage.show();
	}
	
	public void switchScene()
	{
		if(state == 1)
		{
			state = 2;
			stg.setScene(scn2);
		}
		else
		{
			state = 1;
			stg.setScene(scn1);
		}
	}
	
	public static void main(String[] args) { launch(args); }
}
