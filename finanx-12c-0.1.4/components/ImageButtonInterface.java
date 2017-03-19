package components;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import core.KeysEnumInterface;

public interface ImageButtonInterface {

    public void init();
	
	// Sets the image that will be displayed over the button.
	// It receives the "address" of the image in the file system.
    public void setImage(String url);

	// Sets the image that will be displayed over the button.
	// It receives an instance of an image class.
    public void setImage(Image img);
    
	// Sets the icon that will be displayed over the button.
	// It receives an instance of an ImageIcon class.
    public void setIcon(ImageIcon ico);
    
    // Associates a key to the image button
    // The key is an implementation of KeysEnumInterface
    public void setKey(KeysEnumInterface key);
    
    // Returns the key associated to the image button
    // The key is an implementation of KeysEnumInterface
    public KeysEnumInterface getKey();
    
    // Fits image to panel's dimensions.
    // It can decrease performance if used without moderation.
    public void fitImage();
    
    // Renders the image button.
    public void paintComponent(Graphics g);
}
