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
    Camera cam;
    boolean isInitialized = false;
    
    public void init(){
        voxelColors = new HashMap<>();
        for(byte b = 0; b < 0xff;b++){
            voxelColors.put(b, new Color(b,b,b));
        }
        cam = new Camera();
        isInitialized = true;
    }
    
    
    public void Render(WritableRaster raster){
        //calculate view frustrum
        Ray[][] rays = cam.generateRays(raster.getWidth(), raster.getHeight());
        for(int x = 0; x < raster.getWidth(); x++){
            for(int y = 0; y < raster.getHeight();y++){
                world.Trace(rays[x][y]);
            }
        }
        
        
        for(int x = 0; x < raster.getWidth(); x++){
            for(int y = 0; y < raster.getHeight();y++){
                ByteVoxel v = rays[x][y].voxel;
                if (v != null)
                    raster.setPixel(y, y, voxelColors.get(v.ID).getColorComponents(new float[3]));
            }
        }
    }
    
    
}
