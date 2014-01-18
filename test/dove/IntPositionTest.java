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
public class IntPositionTest {
    
    public IntPositionTest() {
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
     * Test of SubIndex method, of class IntPosition.
     */
    @Test
    public void testSubIndex() {
        System.out.println("SubIndex");
        int level = 0;
        int levelMask = 1;
        IntPosition instance = new IntPosition(-1,-1,-1);
        int expResult = 7;
        int result = instance.SubIndex(level, levelMask);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of Copy method, of class IntPosition.
     */
    @Test
    public void testCopy() {
        System.out.println("Copy");
        IntPosition p = null;
        IntPosition instance = null;
        instance.Copy(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class IntPosition.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        IntPosition instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
