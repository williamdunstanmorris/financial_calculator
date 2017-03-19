package components;

import java.awt.Image;

import javax.swing.ImageIcon;

public class HP12CImagePanel extends ImagePanelAbstraction {
	
	private static final long serialVersionUID = -2086455571022091571L;
	
	public HP12CImagePanel(){
		super();
    	init();
	}
	
    public HP12CImagePanel(String url){
    	super(url);
    	init();
    }
    public HP12CImagePanel(Image img){
    	super(img);
    	init();
    }
    public HP12CImagePanel(ImageIcon ico){
    	super(ico);
    	init();
    }
    public void init(){/* Does nothing */}
}
