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
 * @param <Voxel> Leaf type
 * @param <Position> Position type
 */
public interface IOctTreeNode<Voxel,Position> {
    
    public void Insert(Voxel v, Position p);
    public Voxel Get(Position p);
    public boolean Trace(Ray r);
    public void Serialize(Writer writer);
    public void Deserialize(Reader reader);    
}
