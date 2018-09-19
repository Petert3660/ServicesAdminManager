package com.ptconsultancy.helpers;

import com.ptconsultancy.domain.guicomponents.FreeLabelTextFieldPair;
import com.ptconsultancy.domain.utilities.FileUtilities;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

abstract public class NewServiceHelper extends JFrame {

    protected static final String PROJECT_PATH = "C:/GradleTutorials";

    protected String filename = "C:/GradleTutorials/ServicesAdminManager/gitinit.bat";

    protected void removeGitDependency(FreeLabelTextFieldPair comp0) {
        File srcDir;
        final String NEW_TARGET = PROJECT_PATH + "/" + comp0.getText();
        srcDir = new File(NEW_TARGET);
        File[] files = srcDir.listFiles();

        for (File targfile : files) {
            if (targfile.getName().equals(".git") && targfile.isDirectory()) {
                try {
                    FileUtilities.deleteDirectory(targfile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            if (targfile.getAbsolutePath().contains(".iml")) {
                FileUtilities.deleteFile(targfile.getAbsolutePath());
            }
        }
    }

    protected void createNewServiceFiles(File targDir, String projectPath) {
        File srcDir = new File(projectPath);
        final String SETTINGS_GRADLE = "/settings.gradle";
        final String RUN_BAT = "/run.bat";
        final String SETUP_BAT = "/setup.bat";
        try {
            FileUtilities.copyAllFilesFromSrcDirToTargetDir(srcDir.getAbsolutePath(),
                targDir.getAbsolutePath());
            FileUtilities.deleteFile(targDir.getAbsolutePath() + SETTINGS_GRADLE);
            FileUtilities.writeStringToFile(
                targDir.getAbsolutePath() + SETTINGS_GRADLE,
                "rootProject.name = '" + targDir.getName() + "'\n");
            FileUtilities.deleteFile(targDir.getAbsolutePath() + RUN_BAT);
            FileUtilities.writeStringToFile(targDir.getAbsolutePath() + RUN_BAT,
                "cd build/libs\n\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + RUN_BAT,
                "java -jar " + targDir.getName() + ".jar\n");
            FileUtilities.writeStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "echo off\n\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "set myDirName = \".\\build\\libs\"\n\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "if exist myDirName (\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "    cd build\\libs\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "    del *.*\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "    cd ..\\..\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                ")\n\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "call gradlew clean build\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "call run\n");
            FileUtilities.appendStringToFile(targDir.getAbsolutePath() + SETUP_BAT,
                "cd ..\\..\n");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    protected void updateBuildGradleFile(FreeLabelTextFieldPair comp0, String projectTitle) {
        final String BUILD_GRADLE_FILE = PROJECT_PATH + "/" + comp0.getText() + "/build.gradle";
        File buildFile = new File(BUILD_GRADLE_FILE);

        try {
            String allGradleContents = FileUtilities.writeFileToString(BUILD_GRADLE_FILE);
            if (buildFile.exists()) {
                buildFile.delete();
            }
            allGradleContents = allGradleContents.replace("projectName = \"" + projectTitle.replaceAll(" ", "") + "\"",
                "projectName = \"" + comp0.getText() + "\"");
            allGradleContents = allGradleContents.replace("projectTitle = \"" + projectTitle + "\"",
                "projectTitle = \"" + comp0.getText() + "\"");
            FileUtilities.writeStringToFile(BUILD_GRADLE_FILE, allGradleContents);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    protected void updateGitAndJenkins(FreeLabelTextFieldPair comp0, JFrame tg, String TITLE) {
        try {
            FileUtilities.writeStringToFile(filename, "cd\\\n");
            FileUtilities.appendStringToFile(filename, "cd C:\\GradleTutorials\\" + comp0.getText() + "\n\n");
            FileUtilities.appendStringToFile(filename,"git init\n");
            FileUtilities.appendStringToFile(filename,"git add *\n");
            FileUtilities.appendStringToFile(filename,"git commit -m \"First Commit\"\n");
//                                FileUtilities.appendStringToFile(filename, "git remote add origin https://github.com/Petert3660/" + comp0.getText() + ".git\n");
//                                FileUtilities.appendStringToFile(filename, "git push -u origin master");
            Process process = Runtime.getRuntime().exec("C:\\GradleTutorials\\ServicesAdminManager\\gitinit.bat");

            JOptionPane.showMessageDialog(tg,
                "IMPORTANT! About to set up new Jenkins Project.\n\n First you should make sure the new repository, "
                    + comp0.getText() + ".git is published to the remote origin.\n\nDo this NOW before proceeding.",
                TITLE, JOptionPane.INFORMATION_MESSAGE);

            String jenkinsFile = "C://GradleTutorials/ServicesAdminManager/JenkinsModelConfig.xml";
            String xml = FileUtilities.writeFileToString(jenkinsFile);
            xml = xml.replace("<Project Name Here>", comp0.getText());

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<String> request = new HttpEntity<>(xml, httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("petert3660", "2905e6a2fd0251f555bd90c055e0ff18"));
            String endpoint = "http://localhost:8080/createItem?name=" + comp0.getText();
            URI uri = new URI(endpoint);
            try {
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);
            } catch (RestClientException rce) {
                System.out.println("In REST exception catch");
                rce.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    protected void deleteServiceFromJenkinsTarget(String serviceName) throws IOException {
        String source = "C:/PTConsultancy/LocalTestEnvironment/";
        source = source + serviceName;
        if (!source.equals("C:/PTConsultancy/LocalTestEnvironment/")) {
            FileUtilities.deleteDirectory(new File(source));
        }
    }

    protected void deleteServiceFromSource(String serviceName) throws IOException {
        String source = "C:/GradleTutorials/";
        source = source + serviceName;
        if (!source.equals("C:/GradleTutorials/")) {
            FileUtilities.deleteDirectory(new File(source));
        }
    }
}
