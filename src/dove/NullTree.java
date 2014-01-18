/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author Jonas
 */
public class NullTree extends OctTreeNode{
    public static NullVoxel nullVoxel = new NullVoxel((byte)0);
    public NullTree(int level) {
        super();
        this.level = level;
    }

    @Override
    public ByteVoxel Get(IntPosition p) {
        return nullVoxel;
    }

    @Override
    public void Insert(ByteVoxel v, IntPosition p) {
        int i = 1/0;
    }


    @Override
    public boolean isEmpty() {
        return true;
    }
}
