/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dove;

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
public class CameraTest {
    
    public CameraTest() {
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
     * Test of generateRays method, of class Camera.
     */
    @Test
    public void testGenerateRays() {
        System.out.println("generateRays");
        int width = 3;
        int height = 3;
        Camera instance = new Camera();
        instance.x = 0;
        instance.y = 0;
        instance.z = 0;
        instance.SetBackPlaneDistance(100);
        instance.horizontalAngle = 0;
        instance.verticalAngle = Math.PI/2;
        Ray[][] expResult = null;
        Ray[][] result = instance.generateRays(width, height);
        for(Ray[] rs : result){
            for (Ray r : rs){
                System.out.println(r.finish);
            }
        }
        
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
