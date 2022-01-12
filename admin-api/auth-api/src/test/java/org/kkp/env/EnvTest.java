package org.kkp.env;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author Klaus
 * @since 2022/1/13
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EnvTest {

    @Autowired
    Environment env;

    @Before
    public void before() {
        System.out.println("before start ");
    }

    @After
    public void after() {
        System.out.println("after test over");
    }

    @Test
    public void getEnv() {
        assertNotNull(env.getProperty("a"));
    }
}
