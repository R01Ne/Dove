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
public class NullVoxel extends ByteVoxel{

    public NullVoxel(byte i) {
        super(i);
    }

    @Override
    public boolean isEmpty() {
        return true; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
