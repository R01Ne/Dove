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

/**
 *
 * @author Jonas
 */
public class Tracer {
    OctTreeNode world;
    HashMap<Byte, Color> voxelColors;
    public Camera cam;
    boolean isInitialized = false;
    
    public void init(){
        voxelColors = new HashMap<>();
        
        for(int b = 0; b < 0x00ff;b++){
            voxelColors.put((byte)b, new Color(b,b,b));
        }
        cam = new Camera();
        isInitialized = true;
    }

        public void Render(int[] raster, int width, int height){
        //calculate view frustrum
        if (!isInitialized) init();
        Ray[][] rays = cam.generateRays(width, height);
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height;y++){
                Ray r = rays[x][y];
                r.voxel= trace(r);
                raster[x+width*y] = r.voxel!=null?Color.BLACK.getRGB():Color.BLUE.getRGB();
                
            }
        }
        
    }
    
    public void Render(WritableRaster raster){
        //calculate view frustrum
        if (!isInitialized) init();
        Ray[][] rays = cam.generateRays(raster.getWidth(), raster.getHeight());
        for(int x = 0; x < raster.getWidth(); x++){
            for(int y = 0; y < raster.getHeight();y++){
                Ray r = rays[x][y];
                r.voxel= trace(r);
            }
        }
        
        
        for(int x = 0; x < raster.getWidth(); x++){
            for(int y = 0; y < raster.getHeight();y++){
                ByteVoxel v = rays[x][y].voxel;
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
    
    private ByteVoxel trace(Ray r){
        ByteVoxel ret = null;
        
        IntPosition p0 = r.start;
        IntPosition p1 = r.finish;
        IntPosition delta,step,p,c;
        
        boolean swap_xy, swap_xz;
        int drift_xy, drift_xz;

    
    //'steep' xy Line, make longest delta x plane  
    swap_xy = Math.abs(p1.y - p0.y) > Math.abs(p1.x - p0.x);
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
    swap_xz = Math.abs(p1.z - p0.z) > Math.abs(p1.x - p0.x);  
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
    delta = new IntPosition(Math.abs(p1.x - p0.x),Math.abs(p1.y - p0.y),Math.abs(p1.z - p0.z));
    
    //drift controls when to step in 'shallow' planes
    //starting value keeps Line centred
    drift_xy  = (delta.x >> 1);
    drift_xz  = (delta.x >> 1);
    
    //direction of line
    step = new IntPosition(p0.x > p1.x? -1:1,p0.y > p1.y? -1:1,p0.z > p1.z? -1:1);
    
    //starting point
    p = new IntPosition(p0);
    //step through longest delta (which we have swapped to x)
    //for x = x0 to x1 step step_x
    for (; p.x <=p1.x; p.x += step.x){  
        c = new IntPosition(p);
        
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
       /* 
        //passes through this point
        debugmsg(":" + cx + ", " + cy + ", " + cz)
        */
        ret = world.Get(c);
        if (ret!= null) return ret;
        //update progress in other planes
        drift_xy = drift_xy - delta.y;
        drift_xz = drift_xz - delta.z;

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
