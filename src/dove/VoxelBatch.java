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
public class VoxelBatch {
    public static ByteVoxel[] batch;
    public static NullVoxel[] nullVoxels;
    public static VoxelBatch instance =new VoxelBatch();
    protected VoxelBatch(){
        batch = new ByteVoxel[256];
        nullVoxels = new NullVoxel[32];
        for (int i = 0; i < 255; i++){
            batch[i] = new ByteVoxel((byte)i);
        }
        for (int i = 0; i < 32; i++){
            nullVoxels[i] = new NullVoxel((byte)i);
        }
    }
}
