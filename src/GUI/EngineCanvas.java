/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GUI;

import dove.Engine;
import dove.Tracer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.swing.JComponent;

/**
 *
 * @author Jonas
 */
public class EngineCanvas extends Canvas{
    Tracer tracer;
    Engine engine;
    
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.
        if (this.getBufferStrategy()==null)this.createBufferStrategy(2);
        System.out.println("@EngineFrame: update");
        WritableRaster raster;
        Graphics g2 = this.getBufferStrategy().getDrawGraphics();
        BufferedImage i = new BufferedImage(this.getWidth(),this.getHeight(),4); 
        raster = i.getRaster();
        this.tracer.Render(raster);
        
        i.setData(raster);
        grphcs.setPaintMode();
        grphcs.setColor(Color.black);
        grphcs.drawLine(3, 3, 10, 10);
        
        //gr.drawImage(i, 0,0, this);
        this.getBufferStrategy().show();
        
    }
    
    
}
