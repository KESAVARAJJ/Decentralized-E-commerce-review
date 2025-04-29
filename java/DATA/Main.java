/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

/**
 *
 * @author vinoth_m
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
/* w  ww. j  a v a 2  s. c  o m*/
import javax.imageio.ImageIO;

public class Main {

   // File file = new File("D:\\fashion\\s3.png");
   HashSet<String> set=new HashSet<String>(); 
  HashSet<String> set1=new HashSet<String>(); 
 ArrayList<String> seussCountActivities = new ArrayList<String>();
 
  public void printPixelARGB(int pixel) {
    int alpha = (pixel >> 24) & 0xff;
    System.out.println(alpha);
    int red = (pixel >> 16) & 0xff;
   
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
        ColorUtils cc=new ColorUtils();
    String ss=cc.getColorNameFromRgb(red,green,blue);
    set.add(ss);  
    seussCountActivities.add(ss);
    //  String ss=cc.getColorNameFromRgb(red,green,blue);
//    System.out.println("Red Color value = " + red);
//    System.out.println("Green Color value = " + green);
//    System.out.println("Blue Color value = " + blue);
//     System.out.println(" Color name = " + ss);
//    System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
  }

  public HashSet marchThroughImage(BufferedImage image) {
    int w = image.getWidth();
    int h = image.getHeight();
   System.out.println("width, height: " + w + ", " + h);
    int im=0;
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
       // System.out.println("x,y: " + j + ", " + i);
        int pixel = image.getRGB(j, i);
        im++;
        System.out.println(im);
        printPixelARGB(pixel);
        j=j+100;
       // System.out.println("");
      }
      i=i+100;
    }
    Iterator<String> itr=set.iterator();  
  while(itr.hasNext()){
      String str=itr.next();
 System.out.println(str);  
   int oneCount = Collections.frequency(seussCountActivities, str);
 
   if(oneCount>=10)
   {
       set1.add(str);
       System.out.println("Count of "+str+" is:  "+ oneCount);
   }
		//System.out.println("Count of "+str+" is:  "+ oneCount);
  }

  return set1;
  }

  public Main() {
   
  }

    
    /*BufferedImage image = ImageIO.read(file);
    
    int x = 10;
    int y = 10;
    
    int clr = image.getRGB(x, y);
    int red = (clr & 0x00ff0000) >> 16;
    int green = (clr & 0x0000ff00) >> 8;
    int blue = clr & 0x000000ff;
    ColorUtils cc=new ColorUtils();
    String ss=cc.getColorNameFromRgb(red,green,blue);
    System.out.println("Red Color value = " + red);
    System.out.println("Green Color value = " + green);
    System.out.println("Blue Color value = " + blue);
     System.out.println(" Color name = " + ss+","+cc.getColorNameFromHex(clr));*/
  
}