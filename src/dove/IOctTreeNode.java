/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.io.Reader;
import java.io.Writer;

public interface IOctTreeNode {
    
    public void Insert(ByteVoxel v, IntPosition p);
    public ByteVoxel Get(IntPosition p);
    public boolean Trace(Ray r);
    public void Serialize(Writer writer);
    public void Deserialize(Reader reader);    
    public boolean isEmpty();
}
