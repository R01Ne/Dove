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
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import javax.swing.JFrame;

/**
 *
 * @author Jonas
 */
public class EngineFrame extends JFrame{
    private EngineCanvas canvas;
    private Tracer tracer;
    private Engine engine;
    
    public EngineFrame(){
        canvas = new EngineCanvas();
        
        tracer = new Tracer();
        
        //Raster raster = this.rootPane.createVolatileImage(this.getWidth(), this.getHeight()).getSnapshot().getData();
        //tracer.Render((WritableRaster)raster);
        //this.createBufferStrategy(2);
    }

    public EngineFrame(Engine e) {
        engine = e;
        tracer = new Tracer();
        tracer.setWorld( e.getWorld());
        //this.createBufferStrategy(2);
        canvas = new EngineCanvas();
        canvas.engine = engine;
        canvas.tracer = tracer;
        this.add(canvas);
        
    }

    

    
    
}
