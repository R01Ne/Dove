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
public class Ray {
    public IntPosition start;
    public IntPosition finish;
    public IntPosition delta;
    
    public ByteVoxel voxel;
    
    public Ray(IntPosition start, IntPosition finish){
        this.start = start;
        this.finish = finish;
    }
    
    @Override
    public String toString(){
        return "Ray: [" + start + " -> " + finish + "]";
    }
}
