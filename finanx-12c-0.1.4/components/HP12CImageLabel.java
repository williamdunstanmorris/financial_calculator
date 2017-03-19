package components;

import java.awt.Image;

import javax.swing.ImageIcon;

public class HP12CImageLabel extends ImageLabelAbstraction{
	
	private static final long serialVersionUID = 5537846439838203424L;
	
	public HP12CImageLabel(){
		super();
    	init();
	}
	
    public HP12CImageLabel(String url){
    	super(url);
    	init();
    }
    public HP12CImageLabel(Image img){
    	super(img);
    	init();
    }
    public HP12CImageLabel(ImageIcon ico){
    	super(ico);
    	init();
    }
    public void init(){/* Does nothing */}
}
