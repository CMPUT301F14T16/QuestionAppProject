package ca.ualberta.cmput301f14t16.easya.Model;

import android.graphics.Bitmap;

public class PixelBitmap {
	public int[] pixel;
	public int width;
	public int height;
	
	public PixelBitmap(int w, int h){
		this.width=w;
		this.height=h;
	}
    public void getColors(Bitmap mBitmap) { 
        pixel = new int[width * height]; 
        mBitmap.getPixels(pixel, 0, width, 0, 0, width, height); 
    } 

    public Bitmap createBitmap() { 
        Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565); 
        img.setPixels(pixel, 0, width, 0, 0, width, height); 
        return img; 
    } 

}

