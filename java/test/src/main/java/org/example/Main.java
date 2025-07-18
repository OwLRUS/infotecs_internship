package org.example;

import org.testng.TestNG;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class Main {
    private static final String SCRIPT_DIR = "scripts" + File.separator;

    public static void main(String[] args) {
        org.testng.TestNG testng = new org.testng.TestNG();
        testng.setTestClasses(new Class[]{Main.class});
        testng.run();
    }

    private void runScript(String... command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(new File(SCRIPT_DIR));
        builder.inheritIO();
        Process process = builder.start();
        int exitCode = process.waitFor();
        Assert.assertEquals(exitCode, 0, "Script failed: " + String.join(" ", command));
    }

    private void runExpectTest(String scriptName) throws IOException, InterruptedException {
        runScript("." + File.separator + scriptName);
    }

    @Test
    public void testAddPair() throws Exception {
        runExpectTest("test_add_pair.exp");
    }

    @Test
    public void testAddPairNegative() throws Exception {
        runExpectTest("test_add_pair_negative.exp");
    }

    @Test
    public void testPrintList() throws Exception {
        runExpectTest("test_print_list.exp");
    }

    @Test
    public void testPrintListExist() throws Exception {
        runExpectTest("test_print_list_exist.exp");
    }

    @Test
    public void testGetIP() throws Exception {
        runExpectTest("test_get_IP.exp");
    }

    @Test
    public void testGetIPNegative() throws Exception {
        runExpectTest("test_get_IP_negative.exp");
    }

    @Test
    public void testGetDomain() throws Exception {
        runExpectTest("test_get_domain.exp");
    }

    @Test
    public void testGetDomainNegative() throws Exception {
        runExpectTest("test_get_domain_negative.exp");
    }

    @Test
    public void testConfigEditor() throws Exception {
        runExpectTest("test_config_editor.exp");
    }

    @Test
    public void testConfigEditorNegative() throws Exception {
        runExpectTest("test_config_editor_negative.exp");
    }

    @Test(priority = 1, dependsOnMethods = {"testAddPair"})
    public void testDeletePairByIP() throws Exception {
        runExpectTest("test_delete_pair_IP.exp");
    }

    @Test(priority = 2, dependsOnMethods = {"testAddPair"})
    public void testDeletePairByDomain() throws Exception {
        runExpectTest("test_add_pair.exp");
        runExpectTest("test_delete_pair_domain.exp");
    }

    @Test
    public void testDeletePairNegative() throws Exception {
        runExpectTest("test_delete_pair_negative.exp");
    }
}