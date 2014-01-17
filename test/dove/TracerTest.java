/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
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
public class TracerTest {
    
    public TracerTest() {
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
     * Test of init method, of class Tracer.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        Tracer instance = new Tracer();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Render method, of class Tracer.
     */
    @Test
    public void testRender() {
        System.out.println("Render");
        WritableRaster raster;
        raster = new BufferedImage(30,30,4).getRaster();
        
        Tracer instance = new Tracer();
        instance.init();
        instance.cam.z += 10;
        instance.cam.verticalAngle -= (Math.PI/8);
        instance.world = OctTreeNodeTest.FillBlock(new IntPosition(12,2,2), new IntPosition(17,7,7), new ByteVoxel((byte)0x34));
        
        instance.Render(raster);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
