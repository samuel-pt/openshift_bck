package com.springml.deployR.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import com.revo.deployr.client.RClient;
import com.revo.deployr.client.RProject;
import com.revo.deployr.client.RProjectExecution;
import com.revo.deployr.client.RProjectResult;
import com.revo.deployr.client.RUser;
import com.revo.deployr.client.about.RProjectDetails;
import com.revo.deployr.client.about.RProjectExecutionDetails;
import com.revo.deployr.client.auth.RAuthentication;
import com.revo.deployr.client.auth.basic.RBasicAuthentication;
import com.revo.deployr.client.factory.RClientFactory;

/**
 *
 */
public class RScriptExecutor {
    private static int count = 1;
    private RClient rClient;
    private RUser rUser;

    public RScriptExecutor(String deployrEndpoint) throws Exception {
        rClient = RClientFactory.createClient(deployrEndpoint);
    }

    public boolean login(String username, String password) throws Exception {
        RAuthentication authToken = new RBasicAuthentication(username, password);
        rUser = rClient.login(authToken);

        return true;
    }

    public String createTempProject() throws Exception {
        RProject tempProj = rUser.createProject();
        RProjectDetails projectDetails = tempProj.about();
        return projectDetails.id;
    }

    public URL exec(String project, String fileName,
            String dir, String author, String version) throws Exception {
        RProject rProject = rUser.getProject(project);
        RProjectExecution exec = rProject.executeScript(fileName, dir, author, version);
        List<RProjectResult> listResults = exec.listResults();
        for (RProjectResult rProjectResult : listResults) {
            InputStream download = rProjectResult.download();
            writeToFile(download);
        }

        RProjectExecutionDetails execDetails = exec.about();
        System.out.println("Console : \n" + execDetails.console);
        System.out.println("Code : \n" + execDetails.code);
        System.out.println("Error : \n" + execDetails.error);
        System.out.println("Error code : \n" + execDetails.errorCode);
//        System.out.println(execDetails.results);
        return exec.downloadResults();
    }

    private void writeToFile(InputStream download) throws Exception {
        FileOutputStream fos = new FileOutputStream("//home/sam/tmp/deployR/" + count);
        fos.
    }
}
