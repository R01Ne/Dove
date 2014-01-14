/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

/**
 *
 * @author Jonas
 */
public class Camera {
    
    //direction and view width, horizontal
    public double horizontalAngle = 0, horizontalView = Math.PI /3;
    public double verticalAngle = 0, verticalView = Math.PI /3;
    
    public double x=0,y=0,z=0;
    public double backplaneDistance = 1000;
    
    IntPosition lowerLeft = new IntPosition(0,0,0), lowerRight = new IntPosition(1,0,0), upperLeft = new IntPosition(0,1,0), upperRight = new IntPosition(1,1,0);

    IntPosition position = new IntPosition((int)x,(int)y,(int)z);
    
    public Camera(){
    }
    
    
    private void updateIntPositions(){
        setPoint(lowerLeft,x,y,z,backplaneDistance,horizontalAngle-horizontalView, verticalAngle-verticalView);
        setPoint(lowerRight,x,y,z,backplaneDistance,horizontalAngle+horizontalView, verticalAngle-verticalView);
        setPoint(upperLeft,x,y,z,backplaneDistance,horizontalAngle-horizontalView, verticalAngle+verticalView);
        setPoint(upperRight,x,y,z,backplaneDistance,horizontalAngle+horizontalView, verticalAngle+verticalView);
        position = new IntPosition((int)x,(int)y,(int)z);
    
    }
    
    /**
     * this is wrong! the points end up on a sphere, and that makes it triangular as the points approach the north/south poles.
     * a cylinder, rotated with verticalAngle would work better. 
     * @param p
     * @param x
     * @param y
     * @param z
     * @param distance
     * @param horAngle
     * @param vertAngle 
     */
    private void setPoint(IntPosition p, double x, double y, double z, double distance, double horAngle, double vertAngle ){
        p.x = (int)(x+(distance*Math.cos(horAngle)*Math.cos(vertAngle))); 
        p.y = (int)(y+(distance*Math.sin(horAngle)*Math.cos(vertAngle))); 
        p.z = (int)(z+(distance*Math.sin(vertAngle))); 
    }
    
    
    /**
     * find a point p between two points, such that the |p - p1| == scale
     * @param p1
     * @param p2
     * @param scale
     * @return 
     */
    private IntPosition intermediate(IntPosition p1, IntPosition p2, double scale){
        return new IntPosition((int) (((p2.x-p1.x)*scale) + p1.x),
                (int) (((p2.y-p1.y)*scale) + p1.y),
                (int) (((p2.z-p1.z)*scale) + p1.z));
    }
    
    
    public Ray[][] generateRays(int width, int height){
        Ray[][] ret = new Ray[width][];
        updateIntPositions();
        for(int i = 0; i < width; i++)
        {
            IntPosition lineUpper = intermediate(upperLeft,lowerLeft, (double)i/((double)width));
            IntPosition lineLower = intermediate(upperRight,lowerRight, (double)i/((double)width));
            
            ret[i] = new Ray[height];
            for(int j = 0;j < height; j++)
            {
                ret[i][j] = new Ray(position,intermediate(lineUpper,lineLower,(double)j/(double)height));
            }
        }
        return ret;
    }
    
}
