package interfaceGraphique;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

import application.Coordonnees;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import interfaceGraphique.CameraManager;

public class Controller {

	private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    Modele model;
    
    
	@FXML
	private Pane pane3D;
	
	@FXML
	private TextField textAnnee;
	
	
	
	
	
	
	
	
	
	
	public void initController(String path) throws Exception {
		textAnnee.setText("1880");
		model=new Modele(path);
		System.out.println(model.getGlobe().getMaxMin());
		System.out.println(model.getGlobe().getValue(new Coordonnees(-88,-178), model.getAnnee()));
		initEarth();
		
		textAnnee.addEventHandler(KeyEvent.KEY_PRESSED , new EventHandler<KeyEvent>(){
			
			public void handle(KeyEvent event) {
				if(model.getSelect()==true) {
					listeRect.get(listeRect.size()-1).setWidth(listeRect.get(listeRect.size()-1).getWidth()-20);
					listeRect.get(listeRect.size()-1).setHeight(listeRect.get(listeRect.size()-1).getHeight()-20);
				}
			}
		});
	}
	
	
	@FXML
	public void initialize() {
		
		
		
		

        
	}
	
	public void initEarth() {
		
		//Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();
        

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
       /* PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);
*/
        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);

        miseAJourVue(root3D);
        
        // Create scene
        SubScene subscene= new SubScene(root3D, 600, 600, true,SceneAntialiasing.BALANCED);
        subscene.setCamera(camera);
        subscene.setFill(Color.GREY);
        pane3D.getChildren().addAll(subscene);
		
	}
	
	
	
	
	
	private void miseAJourVue(Group root3D) {
		Group contour=new Group();
        final PhongMaterial rMaterial = new PhongMaterial();
        final PhongMaterial vMaterial = new PhongMaterial();
        final PhongMaterial orangeMaterial = new PhongMaterial();
        final PhongMaterial greyMaterial = new PhongMaterial();
        Color red=new Color(1,0,0,0.0001);
        Color green=new Color(0,1,0,0.0001);
        Color grey=new Color(0.823,0.819,0.870,0.0001);
        Color orange=new Color(1,0.65,0.22,0.0001);//250, 192, 57
        
        rMaterial.setDiffuseColor(red);
        rMaterial.setSpecularColor(red);
        
        vMaterial.setDiffuseColor(green);
        vMaterial.setSpecularColor(green);
        
        greyMaterial.setDiffuseColor(grey);
        greyMaterial.setSpecularColor(grey);
        
        orangeMaterial.setDiffuseColor(orange);
        orangeMaterial.setSpecularColor(orange);
        
        PhongMaterial tMaterial= new PhongMaterial();
        
        
        for (int lat=-88;lat<88;lat+=4) {
        	for (int lon=-178;lon<178;lon+=4) {
        		Float f=model.getGlobe().getValue(new Coordonnees(lat,lon), model.getAnnee());
        		if(f.equals(Float.NaN)) {
        			tMaterial=greyMaterial;
        		}
        		else if (f>0.8f) {
        			tMaterial=rMaterial;
        		}
        		else if(f>0.3f && f<=0.8f) {
	        		tMaterial=orangeMaterial;
	        	}else if (f<=0.3f){
	        		tMaterial=vMaterial;
	        	}
	        	Point3D topRight= geoCoordTo3dCoord(lat, lon,1.001f);
	        	Point3D bottomRight= geoCoordTo3dCoord(lat-2, lon,1.001f);
	       		Point3D bottomLeft= geoCoordTo3dCoord(lat-2, lon-2,1.001f);
	       		Point3D topLeft= geoCoordTo3dCoord(lat, lon-2,1.001f);
	       		
	       		AddQuadrilateral(contour, topRight, bottomRight, bottomLeft, topLeft ,tMaterial);
	       		
	       	
        	}
        }
        root3D.getChildren().add(contour);
	}
	
	
	
	
	
	
	
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
	
	
}
