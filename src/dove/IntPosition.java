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
public class IntPosition {
    public int x;
    public int y;
    public int z;

    public IntPosition(int x, int y, int z) {
        this.x= x;
        this.y = y;
        this.z = z;
    }

    IntPosition(IntPosition p0) {
        this.x = p0.x;
        this.y = p0.y;
        this.z = p0.z;
        
    }
    
    public int SubIndex(int level,int levelMask){
        assert ((1<<level)^levelMask)==0;
        int ret = ((x&levelMask)>>level);
        ret |= (((y&levelMask)>>level)<<1);
        ret |= (((z&levelMask)>>level)<<2);
        assert 0<=ret && ret <=7;
        return ret;
    }
    
    public void Copy(IntPosition p){
        x=p.x;
        y=p.y;
        z=p.z;
    }
    
    @Override
    public String toString(){
        return "{" + x + ", " + y + ", " + z + " }";
    }
}
