package com.springml.deployR.client;

import java.net.URL;

import org.junit.Before;

/**
 * Unit test for simple App.
 */
public class RScriptExecutorTest {
    private RScriptExecutor rsExecutor;

    @Before
    public void setup() throws Exception {
        rsExecutor = new RScriptExecutor("http://localhost:7400/deployr");
    }

    @org.junit.Test
    public void testExec() throws Exception {
        rsExecutor.login("testuser", "changeme");
        String projectId = rsExecutor.createTempProject();
        System.out.println("projectId : " + projectId);
        URL response = rsExecutor.exec(projectId, "Histogram of Auto Sales", "root", "testuser", null);
        System.out.println("response : " + response);
    }
}
