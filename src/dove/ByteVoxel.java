/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonas
 */
public class ByteVoxel implements IOctTreeNode<ByteVoxel, IntPosition> {
    public byte ID =0;

    public ByteVoxel(byte i) {
        ID = i;
    }

    @Override
    public void Insert(ByteVoxel v, IntPosition p) {
       
    }

    @Override
    public ByteVoxel Get(IntPosition p) {
        return this;
    }

    @Override
    public boolean Trace(Ray r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Serialize(Writer writer) {
        try {
            writer.write(ID);
        } catch (IOException ex) {
            Logger.getLogger(ByteVoxel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void Deserialize(Reader reader) {
        try {
            ID = (byte)reader.read();
        } catch (IOException ex) {
            Logger.getLogger(ByteVoxel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
