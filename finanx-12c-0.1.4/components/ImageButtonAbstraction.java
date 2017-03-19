package components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import core.KeysEnumInterface;

public abstract class ImageButtonAbstraction extends JButton implements ImageButtonInterface{
	
	private static final long serialVersionUID = 7805147263610450678L;
	
	protected Image image;
   	protected KeysEnumInterface key;

    public ImageButtonAbstraction(){ }
    
    public ImageButtonAbstraction(String url){
    	this.setImage(url);
    	this.repaint();
    	this.init();    	
    }
    public ImageButtonAbstraction(Image img){
    	if(img!=null){
    		this.image = img;
        	this.repaint();
        	this.init();
    	}
    }
    public ImageButtonAbstraction(ImageIcon ico){
    	if(ico!=null) this.image = ico.getImage();
    	this.repaint();
    	this.init();    	
    }
    
    public abstract void init();
    
    public void setImage(String url){
    	if(url!=null){
    		this.image = new ImageIcon(url).getImage();
        	this.repaint();
    	}
    }
    
    public void setImage(Image img){ 
    	if(img!=null) {
    		this.image = img;
        	this.repaint(); 
    	}
    }
    
    public void setIcon(ImageIcon ico){ 
    	if(ico!=null){
    		this.image = ico.getImage();
        	this.repaint();
    	}
    }
    
    public void setKey(KeysEnumInterface key){
    	this.key = key;
    }
    
    public KeysEnumInterface getKey(){
    	return this.key;
    }
  
    public void fitImage(){
        if(image != null) {
	          Dimension dim = this.getSize();
	     
	          double width = dim.getWidth();
	          double height = dim.getHeight();
	          
	          image = new ImageIcon(this.image.getScaledInstance( (int) width, (int) height, Image.SCALE_DEFAULT)).getImage();  
        }
    }
    
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(image != null) {
	          g.drawImage(image, 0, 0, this);
        }
	} 
}
