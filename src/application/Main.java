package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import data.CSVReader;

public class Main {

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
			
			
			/*
			Coordonnees cor=new Coordonnees(-88,-178);
			System.out.println(globe.getValueZone(cor));
			ArrayList max=globe.getMaxMin();
			System.out.println(max);
			HashMap hash=globe.getValue(1952);
			System.out.println(hash);
			System.out.println(globe.getValue(new Coordonnees(12,154), 1881));
			
			
			SimpleTest test=new SimpleTest();
			test.setUp();
			test.TemperatureAnomalyTest();
			test.MaxTemperatureTest();
			*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
