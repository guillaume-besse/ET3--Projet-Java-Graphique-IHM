package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.CSVReader;
import interfaceGraphique.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
/*
	public static void main(String[] args) throws Exception
	{
		
		try {
			Globe globe=new Globe();
			if(args.length>0) {
				globe.chargerCSV(args[0]); // on charge les données du CSV dans l'instance de Globe.
			}
			else {
				System.out.println("vous devriez entrer le chemin du csv en parametre");
			}
			
			
			// tests de la partie applicative
			
			
			SimpleTest test=new SimpleTest();
			test.setUp();
			test.TemperatureAnomalyTest();
			test.MaxTemperatureTest();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*/
	
	private static String path;
	
	@Override
    public void start(Stage primaryStage) throws Exception{
		
		
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../interfaceGraphique/Vue.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("../interfaceGraphique/Vue.fxml"));
		Parent root =loader.load();
		Controller controller=loader.getController();
		controller.initController(path);
		
        primaryStage.setTitle("Etude Climatique");
        primaryStage.setScene(new Scene(root, 600, 958));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception
    {
    	path=args[0];
    	Globe globe=new Globe();
    	globe.chargerCSV(args[0]);
        launch(args);
    }
	
	
}




