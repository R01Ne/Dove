/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.awt.Color;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas
 */
public class Tracer {
    OctTreeNode world;
    Color[] voxelColors;
    public Camera cam;
    boolean isInitialized = false;
    
    public void init(){
        voxelColors = new Color[256];
        
        for(int b = 0; b < 0x00ff;b++){
            voxelColors[b]= new Color(b,b,b);
        }
        voxelColors[0x88]=Color.BLUE;
        cam = new Camera();
        isInitialized = true;
    }

    public void Render(int[] raster, int width, int height){
        //calculate view frustrum
        if (!isInitialized) init();
        Ray[] rays = cam.generateRays(width, height);
        for (Ray r : rays) r.voxel = trace(r);
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height;y++){
                Ray r = rays[(x*height) +y];
                raster[x+width*y] = r.voxel!=null?voxelColors[0x000000ff&r.voxel.ID].getRGB():Color.black.getRGB();
                
            }
        }

    }    
     
    public void parallelRender(int[] raster, int width, int height){
        //calculate view frustrum
        if (!isInitialized) init();
        Ray[] rays = cam.generateRays(width, height);
        int threadCount = 8;
        TracerThread[] tts = new TracerThread[threadCount]; 
        
        for (int i = 0; i <threadCount; i++ ){
            tts[i] = new TracerThread(rays,((i*rays.length)/threadCount),(((i+1)*rays.length)/threadCount),world);
            tts[i].start();
        }
  
        for(TracerThread thread : tts) try {
            thread.join();
            
//        for (Ray r : rays) r.voxel = trace(r);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tracer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
//        for (Ray r : rays) r.voxel = trace(r);
    
        
        
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height;y++){
                Ray r = rays[(x*height) +y];
                raster[x+width*y] = r.voxel!=null?voxelColors[0x000000ff&r.voxel.ID].getRGB():Color.black.getRGB();
                
            }
        }

        
    }
    
    public void Render(WritableRaster raster){
        //calculate view frustrum
        if (!isInitialized) init();
        Ray[] rays = cam.generateRays(raster.getWidth(), raster.getHeight());
        
        for(Ray r : rays) r.voxel=trace(r);
        
        /*
        for(int x = 0; x < raster.getWidth(); x++){
            for(int y = 0; y < raster.getHeight();y++){
                Ray r = rays[x][y];
                r.voxel= trace(r);
            }
        }*/
        
        
        for(int x = 0; x < raster.getWidth(); x++){
            for(int y = 0; y < raster.getHeight();y++){
                ByteVoxel v = rays[x*raster.getWidth() + y].voxel;
                if (v != null) {
                    //raster.setPixel(x, y, voxelColors.get(v.ID).getColorComponents(new float[3]));
                    raster.setPixel(y, y, Color.RED.getColorComponents(new float[4]));
                    ///System.out.println("render " + x + " " +y);
                } else {
                    raster.setPixel(y, y, Color.BLUE.getColorComponents(new float[4]));
                }
            }
        }
    }
    
    private int abs(int a){
        return a>0?a:-a;
    }
    
    IntPosition delta = new IntPosition(),step = new IntPosition(),p = new IntPosition(),c=new IntPosition();
    IntPosition lastVisited  = new IntPosition();
    
    private ByteVoxel trace(Ray r){
        ByteVoxel ret = VoxelBatch.nullVoxels[0];
        
        IntPosition p0 = r.start;
        IntPosition p1 = r.finish;
        //IntPosition delta,step,p,c;
        
        boolean swap_xy, swap_xz;
        int drift_xy, drift_xz;
        int bitMap;
    //'steep' xy Line, make longest delta x plane  
    swap_xy = abs(p1.y - p0.y) > abs(p1.x - p0.x);
    
    if (swap_xy) {
        //Swap(x0, y0)
        int temp = p0.x;
        p0.x = p0.y;
        p0.y= temp;
        //Swap(x1, y1)
        temp = p1.x;
        p1.x = p1.y;
        p1.y = temp;
    }
    //do same for xz
    swap_xz = abs(p1.z - p0.z) > abs(p1.x - p0.x);  
    
    if (swap_xz) {
        //Swap(x0, y0)
        int temp = p0.x;
        p0.x = p0.z;
        p0.z= temp;
        //Swap(x1, y1)
        temp = p1.x;
        p1.x = p1.z;
        p1.z = temp;
    }
    //delta is Length in each plane
    delta.set(
            abs(p1.x - p0.x),
            abs(p1.y - p0.y),
            abs(p1.z - p0.z));
    
    //drift controls when to step in 'shallow' planes
    //starting value keeps Line centred
    drift_xy  = (delta.x >>1);
    drift_xz  = (delta.x >>1);
    
    //direction of line
    step.set(
            p0.x > p1.x? -1:1,
            p0.y > p1.y? -1:1,
            p0.z > p1.z? -1:1);
    
    //starting point
    p.Copy(p0);
    c.Copy(p0);
    lastVisited.Copy(p);
    
//step through longest delta (which we have swapped to x)
    //for x = x0 to x1 step step_x
    boolean positiveDirection = p0.x<p1.x;
    for (; positiveDirection?(p.x <=p1.x):(p.x >=p1.x); p.x += step.x){  
if(p.differsOnLevel(lastVisited, ret.ID)){
        
            c.Copy(p);
            if (swap_xz) {
                int temp = c.x;
                c.x = c.z;
                c.z= temp;
            }

            if (swap_xy) {
                int temp = c.x;
                c.x = c.y;
                c.y= temp;
            }
            lastVisited.Copy(p);
            ret = world.Get(c);
            if (!ret.isEmpty()) return ret;
        }
        //update progress in other planes
        drift_xy -= delta.y;
        drift_xz -= delta.z;

        //step in y plane
        if (drift_xy < 0) {
            p.y += step.y;
            drift_xy += delta.x;
        }
        //same in z
        if (drift_xz < 0) { 
            p.z += step.z;
            drift_xz += delta.x;
        }        
      }
      return ret;
    }

    public void setWorld(OctTreeNode world) {
        this.world = world;
    }
}
