package components;

import java.awt.Image;

import javax.swing.ImageIcon;

public class HP12CImageButton extends ImageButtonAbstraction{

	private static final long serialVersionUID = 4829848805289053159L;

	public HP12CImageButton(){ 
		super();
    	init();
	}
	
    public HP12CImageButton(String url){
    	super(url);
    	init();
    }
    public HP12CImageButton(Image img){
    	super(img);
    	init();
    }
    public HP12CImageButton(ImageIcon ico){
    	super(ico);
    	init();
    }
    
    public void init(){
    }
    
}
