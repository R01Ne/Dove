/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.io.Reader;
import java.io.Writer;


public class OctTreeNode implements IOctTreeNode<ByteVoxel,IntPosition> {

    IOctTreeNode<ByteVoxel,IntPosition>[] SubTree; 
    int levelMask = 1;
    int level = 1;
    public OctTreeNode(int level){
        this.SubTree = level==0?new ByteVoxel[8]:new OctTreeNode[8];
        SetLevel(level);
    
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
            if (SubTree[i] == null) SubTree[i] = new OctTreeNode(level-1);
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

}
