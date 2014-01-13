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

    IntPosition(int x, int y, int z) {
        this.x= x;
        this.y = y;
        this.z = z;
    }
    
    public int SubIndex(int level,int levelMask){
        int ret = ((x&levelMask)>>level);
        ret |= (((y&levelMask)>>level)<<1);
        return ret | (((y&levelMask)>>level)<<2);
    }
    

    
}
