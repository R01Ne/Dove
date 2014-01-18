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
public abstract class Ray {
    protected IntPosition start;
    protected IntPosition finish;
    protected IntPosition delta;

    protected TraversalRecord state;

    public ByteVoxel voxel;
    
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
    
    /**
     * Reset the ray to start position
     */
    private void Init(){
        delta = new IntPosition(Math.abs(finish.x - start.x),Math.abs(finish.y - start.y),Math.abs(finish.z - start.z));
        state = new TraversalRecord();
    }
    
    @Override
    public String toString(){
        return "Ray: [" + start + " -> " + finish + "]";
    }
    public abstract void performSwap();

    public ByteVoxel Step(){
        ByteVoxel ret = null;
        IntPosition step,p,c;
        
        boolean swap_xy, swap_xz;
        int drift_xy, drift_xz;
        
    //'steep' xy Line, make longest delta x plane  
    swap_xy = Math.abs(finish.y - start.y) > Math.abs(finish.x - start.x);
    if (swap_xy) {
        //Swap(x0, y0)
        int temp = start.x;
        start.x = start.y;
        start.y= temp;
        //Swap(x1, y1)
        temp = finish.x;
        finish.x = finish.y;
        finish.y = temp;
    }
    //do same for xz
    swap_xz = Math.abs(finish.z - start.z) > Math.abs(finish.x - start.x);  
    if (swap_xz) {
        //Swap(x0, y0)
        int temp = start.x;
        start.x = start.z;
        start.z= temp;
        //Swap(x1, y1)
        temp = finish.x;
        finish.x = finish.z;
        finish.z = temp;
    }
    //delta is Length in each plane
    
    //drift controls when to step in 'shallow' planes
    //starting value keeps Line centred
    drift_xy  = (delta.x >> 1);
    drift_xz  = (delta.x >> 1);
    
    //direction of line
    step = new IntPosition(start.x > finish.x? -1:1,start.y > finish.y? -1:1,start.z > finish.z? -1:1);
    
    //starting point
    p = new IntPosition(start);
    //step through longest delta (which we have swapped to x)
    //for x = x0 to x1 step step_x
    for (; p.x <=finish.x; p.x += step.x){  
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
       
        //ret = world.Get(c);
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
    
    public static Ray Generate(IntPosition p0, IntPosition p1){
          boolean swap_xy = Math.abs(p1.y - p0.y) > Math.abs(p1.x - p0.x);
          boolean swap_xz = Math.abs(p1.z - p0.z) > Math.abs(p1.x - p0.x);  

        if (swap_xy){
            if (swap_xz){
                return new Ray.SwapXYXZRay(p0, p1);
            } else {
                return new Ray.SwapXYRay(p0, p1);
            }
        } else {
            if (swap_xz){
                return new Ray.SwapXZRay(p0, p1);
            } else{
                return new Ray.StandardRay(p0, p1);
            }
        }
    }

    protected static class StandardRay extends Ray{

        public StandardRay(IntPosition start, IntPosition finish) {
            super(start, finish);
        }

        @Override
        public void performSwap() {
        }
        
        

        
    }
    protected static class SwapXYRay extends Ray{
        public SwapXYRay(IntPosition start, IntPosition finish) {
            super(start, finish);
        }
        @Override
        public void performSwap() {
            int temp = start.x;
            start.x = start.y;
            start.y= temp;
            //Swap(x1, y1)
            temp = finish.x;
            finish.x = finish.y;
            finish.y = temp;
        }
    }
    protected static class SwapXZRay extends Ray{
        
        public SwapXZRay(IntPosition start, IntPosition finish) {
            super(start, finish);
        }
        
        @Override
        public void performSwap() {
            int temp = start.x;
            start.x = start.z;
            start.z= temp;
            //Swap(x1, y1)
            temp = finish.x;
            finish.x = finish.z;
            finish.z = temp;
        }
    }
    protected static class SwapXYXZRay extends Ray{

        public SwapXYXZRay(IntPosition start, IntPosition finish) {
            super(start, finish);
        }
        
        @Override
        public void performSwap() {
            int temp = start.x;
            start.x = start.y;
            start.y= temp;
            //Swap(x1, y1)
            temp = finish.x;
            finish.x = finish.y;
            finish.y = temp;
            
            temp = start.x;
            start.x = start.z;
            start.z= temp;
            //Swap(x1, y1)
            temp = finish.x;
            finish.x = finish.z;
            finish.z = temp;
        }
    
    }
    
    protected static class TraversalRecord{
        IntPosition p,c;
        
        boolean swap_xy, swap_xz;
        int drift_xy, drift_xz;
        
    }
    
}
