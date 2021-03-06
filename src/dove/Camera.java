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
    public double verticalAngle = 0, verticalView = Math.PI /4;
    
    public double x=0,y=0,z=0;
    private double backplaneDistance = 1000;
    
    private double adjBackplaneDistance = backplaneDistance*(1/Math.sin(verticalView));
    
    private Ray[] rayBuffer;
    
    
    IntPosition lowerLeft = new IntPosition(0,0,0), 
            lowerRight = new IntPosition(1,0,0), 
            upperLeft = new IntPosition(0,1,0), 
            upperRight = new IntPosition(1,1,0);
    IntPosition position = new IntPosition((int)x,(int)y,(int)z);
    private IntPosition lineUpper = new IntPosition(),lineLower = new IntPosition(), temp = new IntPosition();
    
    public void SetBackPlaneDistance(double dist){
        backplaneDistance = dist;
        adjBackplaneDistance = dist*(1/Math.cos(verticalView));
    
    }
 
    public Camera(){
        initRayBuffer(1,1);
    }
    
    private void initRayBuffer(int width, int height){
        rayBuffer = new Ray[width*height];
        for (int i = 0 ; i < width*height; i++){
            rayBuffer[i] = new Ray();
        }
    }
    
    private void updateIntPositions(){
        position = new IntPosition((int)x,(int)y,(int)z);

        setPoint(lowerLeft,adjBackplaneDistance,horizontalAngle, verticalAngle-verticalView);
        setPoint(upperLeft,adjBackplaneDistance,horizontalAngle, verticalAngle+verticalView);
        
        lowerRight.Copy(lowerLeft);
        upperRight.Copy(upperLeft);
        
        double viewWidth = adjBackplaneDistance*Math.sin(horizontalView);
        
        translatePoint(lowerLeft, x - Math.sin(horizontalAngle)*viewWidth, y + Math.cos(horizontalAngle)*viewWidth,z);
        translatePoint(lowerRight, x + Math.sin(horizontalAngle)*viewWidth, y - Math.cos(horizontalAngle)*viewWidth,z);
        translatePoint(upperLeft, x - Math.sin(horizontalAngle)*viewWidth, y + Math.cos(horizontalAngle)*viewWidth,z);
        translatePoint(upperRight, x + Math.sin(horizontalAngle)*viewWidth, y - Math.cos(horizontalAngle)*viewWidth,z);
        
    }
    
    /**
     * @param p
     * @param distance
     * @param horAngle
     * @param vertAngle 
     */
    private void setPoint(IntPosition p, double distance, double horAngle, double vertAngle ){
        p.x = (int)(distance*(Math.cos(vertAngle)* Math.cos(horAngle))); 
        p.y = (int)(distance*(Math.cos(vertAngle)* Math.sin(horAngle))); 
        p.z = (int)((distance*Math.sin(vertAngle))); 
    }
    
    private void translatePoint(IntPosition p, double x0, double y0, double z0){
        p.x += x0;
        p.y += y0;
        p.z += z0;
    }
    /**
     * find a point p between two points, such that the |p - p1| == scale
     * @param p1
     * @param p2
     * @param scale
     * @return 
     */
    private void intermediate(IntPosition target, IntPosition p1, IntPosition p2, double scale){
        target.x = intermediate(p1.x,p2.x,scale);
        target.y = intermediate(p1.y,p2.y,scale);
        target.z = intermediate(p1.z,p2.z,scale);
    }
    //x+ (y-x)*
    private int intermediate(int x, int y, double scale){
        return (int)(((float)x) + ((float)(y-x)*(scale)));
    }
    
    
    public Ray[] generateRays(int width, int height){
        if (rayBuffer.length != width*height) initRayBuffer(width, height);
        //Ray[][] ret = new Ray[width][];
        updateIntPositions();
        int rayIndex = 0;
        for(int i = 0; i < width; i++)
        {
            intermediate(lineUpper,upperLeft,upperRight, ((double)i)/(((double)width)-1));
            intermediate(lineLower,lowerLeft,lowerRight, ((double)i)/(((double)width)-1));
            
            
            //ret[i] = new Ray[height];
            for(int j = 0;j < height; j++)
            {
                intermediate(temp,lineUpper,lineLower,((double)j)/((double)height-1));
                rayBuffer[rayIndex++].Reset(position,temp);
            }
        }
        return rayBuffer;
    }
    
}
