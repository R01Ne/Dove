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
    protected IntPosition start;
    protected IntPosition finish;
    protected IntPosition delta;

    public ByteVoxel voxel;

    Ray() {
        start = new IntPosition(0,0,0);
        finish = new IntPosition(0,0,0);
    }
    
    public IntPosition getStart() {
        return start;
    }

    public void setStart(IntPosition start) {
        this.start = start;
    }

    public IntPosition getFinish() {
        return finish;
    }

    public void setFinish(IntPosition finish) {
        this.finish = finish;
    }

    public IntPosition getDelta() {
        return delta;
    }

    public void setDelta(IntPosition delta) {
        this.delta = delta;
    }

    public ByteVoxel getVoxel() {
        return voxel;
    }

    public void setVoxel(ByteVoxel voxel) {
        this.voxel = voxel;
    }
    public Ray(IntPosition start, IntPosition finish){
        this.start = start;
        this.finish = finish;
    }
    
    public void Reset(IntPosition st, IntPosition fi){
        start.Copy(st);
        finish.Copy(fi);
    }
    
    @Override
    public String toString(){
        return "Ray: [" + start + " -> " + finish + "]";
    }
}
