/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.io.Reader;
import java.io.Writer;


public class OctTreeNode implements IOctTreeNode<ByteVoxel,IntPosition> {

    protected OctTreeNode(){
        
    }
    
    IOctTreeNode<ByteVoxel,IntPosition>[] SubTree; 
    int levelMask = 1;
    int level = 1;
    public OctTreeNode(int level){
        SetLevel(level);

        if (level == 1){
            this.SubTree = new ByteVoxel[8];
            for (int i = 0; i < 8 ; i++)
            {
                this.SubTree[i] = VoxelBatch.nullVoxels[0];
            }
        }else{
            this.SubTree= new OctTreeNode[8];
            for (int i = 0; i < 8 ; i++)
            {
                this.SubTree[i] = new NullTree((byte)(level-1));
            }
        }
            
    
    }
    
    private void SetLevel(int i ){
        level = i;
        levelMask = 1<<level;
    }
    
    @Override
    public void Insert(ByteVoxel v, IntPosition p) {
        int i = p.SubIndex(level, levelMask);
            
        if (level == 1){
            SubTree[i] = v;
        }else{
            if (SubTree[i].isEmpty()) SubTree[i] = new OctTreeNode(level-1);
            SubTree[i].Insert(v, p);
        }
    }
    

    @Override
    public ByteVoxel Get(IntPosition p) {
        return SubTree[p.SubIndex(level, levelMask)].Get(p);
    }

    @Override
    public boolean Trace(Ray r) {
        return false;
    }

    @Override
    public void Serialize(Writer writer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Deserialize(Reader reader) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int minCoordinate(){
        return -1>>>level;
    }
    public int maxCoordinate(){
        return 1<<level;
    }
    
    public static void fillCube(OctTreeNode node, IntPosition min, IntPosition max, ByteVoxel voxel){
                
        for(int x = min.x; x < max.x; x++)
        {for(int y = min.y; y < max.y; y++)
        {for (int z = min.z; z<max.z;z++)
        {  node.Insert(voxel, new IntPosition(x,y,z));
        }}}
        
    }


    @Override
    public boolean isEmpty() {
        return false;
    }
    
}
