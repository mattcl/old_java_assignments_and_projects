package data;
import sun.audio.*;


import java.awt.Image;

/**
 * 
 * @author Junichi Tsutsui
 *
 */
public class ResourceModel {
	public static final String AUDIOSTREAM = "AudioStream";
	public static final String IMAGE = "Image";
	
	private String name;
	private String dataType;
	private String filePath;
	private Image image;
	private AudioStream audio;
	
	/**
	 * Default constructor
	 */
	public ResourceModel() {
		name = null;
		dataType = null;
		filePath = null;
		image = null;
		audio = null;
	}
	
	/**
	 * Returns the name of the resource
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the resource
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the file path of the resource
	 * @return String
	 */
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Sets the file path of the resource
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Return the Data Type of the resource 
	 * @return String
	 */
	public String getDataType() {
		return dataType;
	}
	
	/**
	 * Sets the data type of the resource
	 * @param dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * Return the image of the resource 
	 * @return Image
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Sets the image of the resource
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	/**
	 * Return the AudioClip of the resource 
	 * @return AudioClip
	 */
	public AudioStream getAudioStream() {
		return audio;
	}
	
	/**
	 * Sets the AudioClip of the resource
	 * @param audio
	 */
	public void setAudioStream(AudioStream audio) {
		this.audio = audio;
	}
	
	public String toString() {
	    return name;
	}
	
}
