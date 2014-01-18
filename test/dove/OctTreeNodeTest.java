/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.io.Reader;
import java.io.Writer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonas
 */
public class OctTreeNodeTest {
    
    public OctTreeNodeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Insert method, of class OctTreeNode.
     */
    @Test
    public void testInsert() {
        System.out.println("Insert");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(1,2,3);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }

    
    /**
     * Test of Insert method, of class OctTreeNode.
     */
    @Test
    public void testInsertNegX() {
        System.out.println("Insert neg");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(-1,2,3);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }

        /**
     * Test of Insert method, of class OctTreeNode.
     */
    @Test
    public void testInsertNegY() {
        System.out.println("Insert neg");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(1,-2,3);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }

        /**
     * Test of Insert method, of class OctTreeNode.
     */
    @Test
    public void testInsertNegZ() {
        System.out.println("Insert neg");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(1,2,-3);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }

    @Test
    public void testInsertNegXZ() {
        System.out.println("Insert neg");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(-7,2,-3);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }

        @Test
    public void testInsertNegXY() {
        System.out.println("Insert neg");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(-7,-2,3);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }
    public void testInsertNegXYZ() {
        System.out.println("Insert neg");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(-7,-2,-3);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }

   @Test
    public void testInsertXYZ() {
        System.out.println("Insert neg");
        ByteVoxel v = new ByteVoxel((byte)0x01);
        IntPosition p = new IntPosition(-10,-3,0);
        OctTreeNode instance = new OctTreeNode(5);
        instance.Insert(v, p);
        
        ByteVoxel result = instance.Get(p);
        assertEquals(v, result);
    }
    
    /**
     * Test of Trace method, of class OctTreeNode.
     */
    @Test
    public void testTrace() {
        System.out.println("Trace");
        Ray r = Ray.Generate(new IntPosition(0,0,0), new IntPosition(10,0,0));
        ByteVoxel v = new ByteVoxel((byte)0x01);
        OctTreeNode instance = FillBlock(new IntPosition(1,1,1), new IntPosition(5,5,5),v);
        boolean expResult = false;
        boolean result = instance.Trace(r);
        assertEquals(expResult, result);
        r = Ray.Generate(new IntPosition(0,2,2), new IntPosition(10,2,2));
        assertEquals(true, instance.Trace(r));

    }

    /**
     * Test of Serialize method, of class OctTreeNode.
     */
    @Test
    public void testSerialize() {
        System.out.println("Serialize");
        Writer writer = null;
        OctTreeNode instance = null;
        instance.Serialize(writer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Deserialize method, of class OctTreeNode.
     */
    @Test
    public void testDeserialize() {
        System.out.println("Deserialize");
        Reader reader = null;
        OctTreeNode instance = null;
        instance.Deserialize(reader);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    public static OctTreeNode FillBlock(IntPosition min, IntPosition max, ByteVoxel v){
        OctTreeNode node = new OctTreeNode(10);
        for(int x = min.x; x < max.x; x++)
        for(int y = min.y; y < max.y; y++)
        for (int z = min.z; z<max.z;z++)
           node.Insert(v, new IntPosition(x,y,z));
        
            
        return node;
        
        
    }
    
}
