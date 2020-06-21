package interfaceGraphique;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import application.Coordonnees;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import interfaceGraphique.CameraManager;





public class Controller {

	private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    private Modele model;
    
    private Group root3D;
    
    
	@FXML
	private Pane pane3D;
	
	@FXML
	private TextField textAnnee;
	
	@FXML
	private Slider sliderAnnee;
	
	@FXML
	private Button animer;
	
	@FXML
	private Button stop;
	
	@FXML
	private RadioButton rectangle;
	
	@FXML
	private RadioButton histogramme;
	
	@FXML
	private Button plus;
	
	@FXML
	private Button moins;
	
	@FXML
	private Label text_vitesse;
	
	@FXML
	HBox box=new HBox();
	
	@FXML
	Rectangle rec1;
	@FXML
	Rectangle rec2;
	@FXML
	Rectangle rec3;
	@FXML
	Rectangle rec4;
	@FXML
	Rectangle rec5;
	
	
	
	
	
	
	/**
	* Fonction qui initialise le controller et initialise tous les enventHadler de l'interface.
	* 
	* @param  String  Chemin du fichier CSV
	* @throws Exception
	*/
	
	public void initController(String path) throws Exception {
		root3D = new Group();
		textAnnee.setText("1880");
		model=new Modele(path);
		initEarth();
		rectangle.setSelected(true);
		
		textAnnee.addEventHandler(KeyEvent.KEY_PRESSED , new EventHandler<KeyEvent>(){
			
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER)) {
					model.setAnnee(Integer.parseInt(textAnnee.getText()));
					sliderAnnee.setValue(model.getAnnee());
				}
			}
		});
		
		sliderAnnee.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
            	model.setAnnee( new_val.intValue());
             	miseAJourVue();
            	
            }
		});
		
		animer.addEventHandler(MouseEvent.MOUSE_CLICKED , new EventHandler<MouseEvent>(){
			public synchronized void handle(MouseEvent event) {
				model.setAnimation(true);
				
				final Service<Void> calculateService = new Service<Void>() {

		            @Override
		            protected Task<Void> createTask() {
		                return new Task<Void>() {

		                    @Override
		                    protected Void call() throws Exception {
		                    	
		                    	
				
				
								while (model.getAnimation()==true ) {
									
									if(model.getAnnee()>=2020) {
										model.setAnimation(false);
									}else {
										
										try {
									        Thread.sleep(3000);
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										sliderAnnee.setValue(model.getAnnee()+model.getVitesse());
										
									}
								}
			
					        return null;
	                    }
	                };
	            }
	        };
	        
	        
	        calculateService.start();
	        
			}
		});
		stop.addEventHandler(MouseEvent.MOUSE_CLICKED , new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				model.setAnimation(false);
			}
		});
		rectangle.addEventHandler(MouseEvent.MOUSE_CLICKED , new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				model.setRectangle(true);
				miseAJourVue();
			}
		});
		histogramme.addEventHandler(MouseEvent.MOUSE_CLICKED , new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				model.setRectangle(false);
				miseAJourVue();
			}
		});
		
		plus.addEventHandler(MouseEvent.MOUSE_CLICKED , new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				model.augmenteVitesse();
				text_vitesse.setText("Vitesse d'animation x "+ model.getVitesse() +" :");
			}
		});
		moins.addEventHandler(MouseEvent.MOUSE_CLICKED , new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				model.diminueVitesse();
				text_vitesse.setText("Vitesse d'animation x "+ model.getVitesse() +" :");
			}
		});
		
	}
	
	
	/**
	* Fonction appelé au chargement du fichier FXML que l'on n'utilise pas
	* 
	*/
	@FXML
	public void initialize() {
		  
	}
	
	/**
	* Fonction qui initialise l'interface avec la géométrie de la terre et les rectangles autour.
	* 
	*/
	public void initEarth() {
		
		// Load geometry
        String path=new File("").getAbsolutePath();
        System.out.println(path);
        ObjModelImporter objImporter = new ObjModelImporter();
        try {
        	URL modelUrl=this.getClass().getResource("Earth/earth.obj");
        	objImporter.read(modelUrl);
        }catch(ImportException e) {
        	// handle exception
        	System.out.println(e.getMessage());
        }
        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);
        root3D.getChildren().add(earth);
        
       
        // Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);
        new CameraManager(camera, pane3D, root3D);

        // Add point light
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);
        
        Group contour=new Group();
        root3D.getChildren().add(contour);
        
        Color red=new Color(1,0,0,1);
        Color green=new Color(0,1,0,1);
        Color orange=new Color(1,0.65,0.22,1);
        Color jaune=new Color(1,1,0,1);
        Color bleu=new Color(0,0,1,1);
        rec1.setFill(bleu);
        rec2.setFill(green);
        rec3.setFill(jaune);
        rec4.setFill(orange);
        rec5.setFill(red);
        
        
        miseAJourVue();
        
        // Create scene
        SubScene subscene= new SubScene(root3D, 600, 600, true,SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
        pane3D.getChildren().addAll(subscene);
		
	}
	
	
	
	
	/**
	* Fonction qui met à jour la vue après chaque action de l'utilisateur.
	* 
	*/
	public void miseAJourVue() {
		
		
		textAnnee.setText(model.getAnnee().toString());
		Group contour=new Group();
		

                    	
                    	
		
		
		final PhongMaterial rMaterial = new PhongMaterial();
		final PhongMaterial vMaterial = new PhongMaterial();
		final PhongMaterial orangeMaterial = new PhongMaterial();
		final PhongMaterial jauneMaterial = new PhongMaterial();
		final PhongMaterial bleuMaterial = new PhongMaterial();
		final PhongMaterial greyMaterial = new PhongMaterial();
		Color red=new Color(1,0,0,0.000001);
		Color green=new Color(0,1,0,0.000001);
		Color grey=new Color(0.823,0.819,0.870,0.000001);
		Color orange=new Color(1,0.65,0.22,0.000001);
		Color jaune=new Color(1,1,0,0.000001);
		Color bleu=new Color(0,0,1,0.000001);
		
		
		rMaterial.setDiffuseColor(red);
		
		rMaterial.setSpecularColor(red);
        
        vMaterial.setDiffuseColor(green);
        vMaterial.setSpecularColor(green);
        
        greyMaterial.setDiffuseColor(grey);
        greyMaterial.setSpecularColor(grey);
        
        orangeMaterial.setDiffuseColor(orange);
        orangeMaterial.setSpecularColor(orange);
        
        bleuMaterial.setDiffuseColor(bleu);
        bleuMaterial.setSpecularColor(bleu);
        
        jauneMaterial.setDiffuseColor(jaune);
        jauneMaterial.setSpecularColor(jaune);
        
        
        
        PhongMaterial tMaterial= new PhongMaterial();
        
        
        for (int lat=-88;lat<=88;lat+=4) {
        	for (int lon=-178;lon<=178;lon+=4) {
        		Float anomalie=model.getGlobe().getValue(new Coordonnees(lat,lon), model.getAnnee());
        		if (anomalie>1.0f) {
    				tMaterial=rMaterial;
            	}
            	else if(anomalie>0.6f && anomalie<=1.0f) {
            		tMaterial=orangeMaterial;
    	        }
            	else if (anomalie>0.3f && anomalie<=0.6){
            		tMaterial=jauneMaterial;
    	        }
            	else if (anomalie<=0.3f && anomalie>0){
            		tMaterial=vMaterial;
    	        }
            	else if ( anomalie<=0){
            		tMaterial=bleuMaterial;
    	        }
	        	
        		if (model.getRectangle()==true) {
        			
	        		Point3D topRight= geoCoordTo3dCoord(lat, lon,1.001f);
		        	Point3D bottomRight= geoCoordTo3dCoord(lat-3.9f, lon,1.001f);
		       		Point3D bottomLeft= geoCoordTo3dCoord(lat-3.9f, lon-3.9f,1.001f);
		       		Point3D topLeft= geoCoordTo3dCoord(lat, lon-3.9f,1.001f);
		       		
		       		AddQuadrilateral(contour, topRight, bottomRight, bottomLeft, topLeft ,tMaterial);
        		}else {
        			float multiplicateurTaille=0.f;
        			
        			if (anomalie>1.0f) {
        				multiplicateurTaille=1.5f;
        				tMaterial=rMaterial;
                	}
                	else if(anomalie>0.6f && anomalie<=1.0f) {
                		multiplicateurTaille=1.35f;
                		tMaterial=orangeMaterial;
        	        }
                	else if (anomalie>0.3f && anomalie<=0.6){
                		multiplicateurTaille=1.18f;
                		tMaterial=jauneMaterial;
        	        }
                	else if (anomalie<=0.3f && anomalie>0){
                		multiplicateurTaille=1.1f;
                		tMaterial=vMaterial;
        	        }
                	else if ( anomalie<=0){
                		multiplicateurTaille=1;
                		tMaterial=bleuMaterial;
        	        }
        			
        			
        			Point3D origine =new Point3D(0,0,0);
    	       		Point3D top= geoCoordTo3dCoord(lat, lon,1.001f);
    	       		createCylinder(contour,top,origine,multiplicateurTaille,tMaterial);
        				
                }
	       	
        	}
        }
				        
        
        
        Runnable command = new Runnable() {
	        @Override
	        public void run() {
	            
	        	root3D.getChildren().remove(root3D.getChildren().size()-1);
        		root3D.getChildren().add(contour);
        		 
	        }
	    };
	    if (Platform.isFxApplicationThread()) {
	        // Nous sommes déjà dans le thread graphique
	        command.run();
	    } else {
	        // Nous ne sommes pas dans le thread graphique
	        // on utilise runLater.
	        Platform.runLater(command);
	    }
        
        
        
        
        
        
	}
	
	
	/**
	* Fonction qui calcule les coordonnée 3D d'un point corespondant à une latitude, une longitude et le rayon de la sphère
	* 
	* @param  float  lattitude de la zone
	* @param  float  longitude de la zone
	* @param  float  rayon de la sphère.
	* @return Point3D coordonnées 3D du point corespondant.
	*/
	public static Point3D geoCoordTo3dCoord(float lat, float lon,float radius) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))*radius,
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor))*radius,
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor))
                        * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))*radius);
    }
	
	
	/**
	* Fonction qui ajoute à un groupe parent un rectangle en 3D délimité par quatre points en 3D. On associe un material au rectangle.
	* 
	* @param  Group  groupe parent
	* @param  Point3D  point en haut à droite
	* @param  Point3D  point en bas à droite
	* @param  Point3D  point en bas à gauche
	* @param  Point3D  point en haut à gauche
	* @param  PhongMaterial  material que l'on attribut au rectangle
	*/
	private void AddQuadrilateral(Group parent, Point3D topRight,Point3D bottomRight,Point3D bottomLeft,Point3D topLeft,PhongMaterial material) {
    	
    	final TriangleMesh triangleMesh=new TriangleMesh();
    	final float[] points= {
    			(float)topRight.getX(),(float)topRight.getY(),(float)topRight.getZ(),
    			(float)topLeft.getX(),(float)topLeft.getY(),(float)topLeft.getZ(),
    			(float)bottomLeft.getX(),(float)bottomLeft.getY(),(float)bottomLeft.getZ(),
    			(float)bottomRight.getX(),(float)bottomRight.getY(),(float)bottomRight.getZ()
    	};
    	final float[] texCoords= {
    		1,1,
    		1,0,
    		0,1,
    		0,0
    	};
    	final int[] faces= {
    		0,1,1,0,2,2,
    		0,1,2,2,3,3
    	};
    	
    	
    	triangleMesh.getPoints().setAll(points);
    	triangleMesh.getTexCoords().setAll(texCoords);
    	triangleMesh.getFaces().setAll(faces);
    	
    	final MeshView meshView = new MeshView(triangleMesh);
    	meshView.setMaterial(material);
    	parent.getChildren().addAll(meshView);
    	
    }
	
	

	/**
	* Fonction qui ajoute à un groupe parent un cylindre en 3D délimité par deux points en 3D. On associe un material au cylindre.
	* La taille du cylindre sera proportionelle à la valeur multiplicateurTaille
	* 
	* @param  Group  groupe parent
	* @param  Point3D  début du cylindre
	* @param  Point3D  fin du cylindre
	* @param  float  on multiplie la taille du cylindre par cette valeur
	* @param  PhongMaterial  material que l'on attribut au cylindre
	*/
	static public void createCylinder(Group parent,Point3D start,Point3D end,float multiplicateurTaille,PhongMaterial tMaterial) {
    
		Point3D axeY = new Point3D(0,1,0);
		Point3D vect = end.subtract(start);
    	Point3D millieu = end.midpoint(start);
    	Point3D axe = vect.crossProduct(axeY);
    	double angle = Math.acos(vect.normalize().dotProduct(axeY));
    	double taille = vect.magnitude()*multiplicateurTaille;
    	Cylinder cylindre = new Cylinder(0.01, taille);
    	cylindre.getTransforms().addAll(new Translate(millieu.getX(), millieu.getY(), millieu.getZ()), new Rotate(-Math.toDegrees(angle), axe));
    	cylindre.setMaterial(tMaterial);
    	parent.getChildren().add(cylindre);
    
	} 


	
	     
	


}
