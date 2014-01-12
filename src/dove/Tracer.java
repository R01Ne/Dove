/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.awt.image.WritableRaster;

/**
 *
 * @author Jonas
 */
public class Tracer {
    OctTreeNode world;
    
    public void Render(WritableRaster raster, IntPosition cam, IntPosition end){
        //calculate view frustrum
        //first, we need the (x,y) coordinates of the horizon.
        //Assume that the camera position is in the center of a sphere.
        //We know the center position, and a point on the sphere that the camera is facing.
        //We also know two angles, alpha and beta, determining the width and height angles.
        
        
        //Then we calculate the offset from those two positions.
        
        
        
        for(int x = 0; x < raster.getWidth(); x++){
            for(int y = 0; y < raster.getHeight();y++){
                
            }
        }
    }
    
    private Ray[][] initRays(IntPosition start, IntPosition end, int rayCountX,int rayCountY){
        
        return null;
    }
    
}
