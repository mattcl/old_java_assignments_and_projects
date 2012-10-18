package data;
import script.Script;
import sun.audio.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;



/**
 * @author Matthew Chun-Lum
 *
 */
public class BDataModel {
    private Map<String,BShapeModel> shapeModels;
    private Map<String,BPageModel> pageModels;
	private Map<String,ResourceModel> resources;
	private List<String> resourceNames;
	private BPageCounter pageCounter;
	private BShapeCounter shapeCounter;
	private String documentDirectory;
	public static Script script;
	private boolean isSaved;
    
    // will need to add some array lists for resources
    
    /**
     * Default constructor
     */
    public BDataModel() {
        shapeModels = new HashMap<String,BShapeModel>();
        pageModels = new HashMap<String, BPageModel>();
        resources = new HashMap<String, ResourceModel>();
        resourceNames = new ArrayList<String>();

        pageCounter = new BPageCounter();
        shapeCounter = new BShapeCounter();
        documentDirectory = null;
        script = new Script(this);
        isSaved = false;
    }
    
    public void addShape(BShapeModel model){
    	if(model.getName()==null) model.setName(shapeCounter.getShapeName());
    	shapeModels.put(model.getName(), model);
    	model.setData(this);
    }
    
    public void removeShape(BShapeModel model) {
        if(model != null)
            shapeModels.remove(model.getName());
    }
    
    public void addPage(BPageModel model){
    	if(model.getName()==null) model.setName(pageCounter.getPageName());
    	pageModels.put(model.getName(), model);
    	model.setData(this);
    }
    
    public void removePage(BPageModel model) {
        if(model != null) {
            pageModels.remove(model.getName());
            for(BShape shape : model.getShapes())
                removeShape(shape.getModel());
            model = null;
        }
    }
    
    public void addResource(File object){
    	ResourceModel rm = new ResourceModel();
    	rm.setName(object.getName());
    	rm.setFilePath(object.getAbsolutePath());
    	String filePath = rm.getFilePath();
    	if(filePath.endsWith(".jpeg") || filePath.endsWith(".gif")) rm.setDataType("Image");
    	else if(filePath.endsWith(".au")) rm.setDataType("AudioStream");
    	if(rm.getDataType().equals("Image")) rm.setImage(loadImage(object.getAbsolutePath()));
    	else if(rm.getDataType().equals("AudioStream")) rm.setAudioStream(loadAudio(object.getAbsolutePath()));
    	resources.put(rm.getName(), rm);
    }
    public void addResource(ResourceModel rm){
    	resources.put(rm.getName(), rm);
    }
    public void addResourceName(File object) {
    	resourceNames.add(object.getName());
    }

    public void removeResource(String resourceName) {
        for(BShapeModel shapeModel : shapeModels.values())
            if(shapeModel.getImageName().equals(resourceName))
                shapeModel.setImageName("");
        resources.remove(resourceName);
        resourceNames.remove(resourceName);
    }
    
    public boolean resourceIsInUse(String resourceName) {
        for(BShapeModel shapeModel : shapeModels.values())
            if(shapeModel.getImageName().equals(resourceName))
                return true;
        return false;
    }
     
    public Image getImage(String resourceName){
    	ResourceModel rm = resources.get(resourceName);
    	if(rm == null) return null;
    	return rm.getImage();
    }
    
    public AudioStream getAudioStream(String resourceName){
    	ResourceModel rm = resources.get(resourceName);
    	if(rm == null) return null;
    	return rm.getAudioStream();
    }
    
    public BShapeModel getShape(String shapeName){
    	return shapeModels.get(shapeName);
    }
    
    public BPageModel getPage(String pageName){
    	return pageModels.get(pageName);
    }

    /*
    Demonstrates reading in an Image with the ImageIO class.
    Image is the superclass of BufferedImage.
    Returns null on i/o error.
   */
   public Image loadImage(String filename) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(filename));
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
       
       return image;
   }
   
   public AudioStream loadAudio(String filename) {
	   AudioStream as = null;
	   try {
		   InputStream in = new FileInputStream(filename);
		   as = new AudioStream(in); 
	   }
	   catch (IOException ex) {
		   ex.printStackTrace();
	   }
	   
	   return as;
  }
   
	/**
	 * @return the resources
	 */
	public Map<String,ResourceModel> getResourcesMap() {
		return resources;
	}
	
	public List<String> getResourceNames() {
		return resourceNames;
	}
	public void setResourceNames(List<String> resourceNames) {
		this.resourceNames = resourceNames;
	}
	public Map<String, BShapeModel> getShapeModels() {
		return shapeModels;
	}
	public void setShapeModels(Map<String, BShapeModel> shapeModels) {
		this.shapeModels = shapeModels;
	}
	public Map<String, BPageModel> getPageModels() {
		return pageModels;
	}
	public void setPageModels(Map<String, BPageModel> pageModels) {
		this.pageModels = pageModels;
	}
	
	public String getDocDirectory() {
		return documentDirectory;
	}
	
	public void setDocDirectory(String docdir) {
		documentDirectory = docdir;
	}

	public boolean hasShape(String shapeName) {
		return shapeModels.containsKey(shapeName);
	}
	
	public BPageCounter getPageCounter() {
		return pageCounter;
	}
	
	public void setPageCounter(BPageCounter pageCounter) {
		this.pageCounter = pageCounter;
	}
	
	public BShapeCounter getShapeCounter() {
		return shapeCounter;
	}
	
	public void setShapeCounter(BShapeCounter shapeCounter) {
		this.shapeCounter = shapeCounter;
	}
	
	public void loadResources() {
		for(int i = 0; i<resourceNames.size(); i++) {
			String filePath = documentDirectory + File.separatorChar + resourceNames.get(i);
			File f = new File(filePath);
			addResource(f);
		}
	}
	public void playAudio(String key){
    	ResourceModel r = resources.get(key);
    	if(r != null && r.getDataType().equals(ResourceModel.AUDIOSTREAM)) {
    		AudioStream as = r.getAudioStream();
    		AudioPlayer.player.start(as);
    		r.setAudioStream(loadAudio(r.getFilePath()));
    	}
    }
	
	public boolean getSaved() {
		return isSaved;
	}
	
	public void setSaved(boolean state) {
		isSaved = state;
	}
	/**
	 * clones the datamodel doing a deep copy of the pages and shapes
	 * @return clone
	 */
	public BDataModel clone() {
		BDataModel clone = new BDataModel();
		for(String page: this.pageModels.keySet()){
			BPageModel pgclone = pageModels.get(page).clone();
			clone.addPage(pgclone);
			for(BShape shape: pgclone.getShapes()){
				clone.addShape(shape.getModel());
			}
			pgclone.setData(clone);
		}
		for(String resource: this.resources.keySet()){
			ResourceModel rmclone = resources.get(resource);
			clone.addResource(rmclone);
		}
		clone.setResourceNames(this.resourceNames);
		clone.setPageCounter(pageCounter);
		clone.setShapeCounter(shapeCounter);
		clone.documentDirectory = this.documentDirectory;
		return clone;
	}
}