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
    public NullTree(int level) {
        super();
        this.level = level;
    }

    @Override
    public ByteVoxel Get(IntPosition p) {
        return VoxelBatch.nullVoxels[level+1];
    }

    @Override
    public void Insert(ByteVoxel v, IntPosition p) {
        
    }


    @Override
    public boolean isEmpty() {
        return true;
    }
}
