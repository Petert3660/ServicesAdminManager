package com.ptconsultancy.admin;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class TestAdmin {

    private static final String TEST_SERVICE_NAME = "service1";
    private static final String TEST_PATH = "c:\\test\\" + TEST_SERVICE_NAME;

    private Admin admin;

    @Before
    public void setUp() {
        admin = new Admin();
    }

    @Test
    public void test_addService_No_Services_Added() {

        assertThat(admin.getServiceByName(TEST_SERVICE_NAME), is(nullValue()));
    }

    @Test
    public void test_addService_Services_Added() {

        assertThat(admin.addService(TEST_PATH), is(true));
        assertThat(admin.getServiceByName(TEST_SERVICE_NAME), is(notNullValue()));
        assertThat(admin.getServiceByName(TEST_SERVICE_NAME).getName(), is(TEST_SERVICE_NAME));
    }

    @Test
    public void test_addService_Services_Added_Servic_Already_Added() {

        assertThat(admin.addService(TEST_PATH), is(true));
        assertThat(admin.addService(TEST_PATH), is(false));
    }

    @Test
    public void test_noServices_No_Services_Created() {

        assertThat(admin.noServices(), is(true));
    }

    @Test
    public void test_noServices_Services_Created() {

        assertThat(admin.addService(TEST_PATH), is(true));
        assertThat(admin.noServices(), is(false));
    }
}
