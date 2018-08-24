package com.ptconsultancy.admin;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class TestAdmin {

    private static final String TEST_SERVICE_NAME_1 = "service1";
    private static final String TEST_PATH_1 = "c:\\test\\" + TEST_SERVICE_NAME_1;
    private static final String TEST_SERVICE_NAME_2 = "anotherService";
    private static final String TEST_PATH_2 = "c:\\test\\" + TEST_SERVICE_NAME_2;
    private static final String TEST_SERVICE_NAME_3 = "yetAnotherService";
    private static final String TEST_PATH_3 = "c:\\test\\" + TEST_SERVICE_NAME_3;
    private static final String TEST_URL_1 = "localhost:8080";
    private static final String TEST_URL_2 = "localhost:8200";

    private Admin admin;

    @Before
    public void setUp() {
        admin = new Admin();
    }

    @Test
    public void test_addService_No_Services_Added() {

        assertThat(admin.getServiceByName(TEST_SERVICE_NAME_1), is(nullValue()));
    }

    @Test
    public void test_addService_Services_Added() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.getServiceByName(TEST_SERVICE_NAME_1), is(notNullValue()));
        assertThat(admin.getServiceByName(TEST_SERVICE_NAME_1).getName(), is(TEST_SERVICE_NAME_1));
    }

    @Test
    public void test_addService_Services_Added_Servic_Already_Added() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_1), is(false));
    }

    @Test
    public void test_removeService() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        admin.removeService(TEST_SERVICE_NAME_1);
        assertThat(admin.getServiceByName(TEST_SERVICE_NAME_1), is(nullValue()));
    }

    @Test
    public void test_noServices_No_Services_Created() {

        assertThat(admin.noServices(), is(true));
    }

    @Test
    public void test_noServices_Services_Created() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.noServices(), is(false));
    }

    @Test
    public void test_sorting_of_service_names() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        ArrayList<Service> result = admin.getAllServicesByName();

        assertThat(result.get(0).getName(), is(TEST_SERVICE_NAME_2));
        assertThat(result.get(1).getName(), is(TEST_SERVICE_NAME_1));
    }

    @Test
    public void test_allServiceRunning_No_Services_Running() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        assertThat(admin.allServicesRunning(), is(false));
    }

    @Test
    public void test_allServiceRunning_Some_Services_Running() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        admin.setServiceRunningByName(TEST_SERVICE_NAME_1);

        assertThat(admin.allServicesRunning(), is(false));
    }

    @Test
    public void test_allServiceRunning_All_Services_Running() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        admin.setServiceRunningByName(TEST_SERVICE_NAME_1);
        admin.setServiceRunningByName(TEST_SERVICE_NAME_2);

        assertThat(admin.allServicesRunning(), is(true));
    }

    @Test
    public void test_reportHostPortConflict_No_Conflicts() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        admin.setServiceUrlByname(TEST_SERVICE_NAME_1, TEST_URL_1);
        admin.setServiceUrlByname(TEST_SERVICE_NAME_2, TEST_URL_2);

        assertThat(admin.reportHostPortConflict(), is(0));
    }

    @Test
    public void test_reportHostPortConflict_Conflicts() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        admin.setServiceUrlByname(TEST_SERVICE_NAME_1, TEST_URL_1);
        admin.setServiceUrlByname(TEST_SERVICE_NAME_2, TEST_URL_1);

        assertThat(admin.reportHostPortConflict(), is(1));
    }

    @Test
    public void test_reportHostConflict_For_Individual_Service_No_Conflict() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        admin.setServiceUrlByname(TEST_SERVICE_NAME_1, TEST_URL_1);
        admin.setServiceUrlByname(TEST_SERVICE_NAME_2, TEST_URL_2);

        assertThat(admin.reportHostPortConflict(admin.getServiceByName(TEST_SERVICE_NAME_1)), is(0));
    }

    @Test
    public void test_reportHostConflict_For_Individual_Service_Conflict() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));

        admin.setServiceUrlByname(TEST_SERVICE_NAME_1, TEST_URL_1);
        admin.setServiceUrlByname(TEST_SERVICE_NAME_2, TEST_URL_1);

        assertThat(admin.reportHostPortConflict(admin.getServiceByName(TEST_SERVICE_NAME_1)), is(1));
    }

    @Test
    public void test_reportHostConflict_For_Individual_Service_Multiple_Conflicts() {

        assertThat(admin.addService(TEST_PATH_1), is(true));
        assertThat(admin.addService(TEST_PATH_2), is(true));
        assertThat(admin.addService(TEST_PATH_3), is(true));

        admin.setServiceUrlByname(TEST_SERVICE_NAME_1, TEST_URL_1);
        admin.setServiceUrlByname(TEST_SERVICE_NAME_2, TEST_URL_1);
        admin.setServiceUrlByname(TEST_SERVICE_NAME_3, TEST_URL_1);

        assertThat(admin.reportHostPortConflict(admin.getServiceByName(TEST_SERVICE_NAME_1)), is(2));
    }
}
