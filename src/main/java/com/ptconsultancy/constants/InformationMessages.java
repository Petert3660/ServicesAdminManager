package com.ptconsultancy.constants;

public class InformationMessages {

    public static final String NO_SERVICE_SELECTED = "No service selected - please enter/select a service before continuing";
    public static final String NO_SERVICE_SELECT = "No service selected - please select a service before continuing";

    public static String NO_SERVICES_YET = "There are no services added as yet - you must add services before they can be ";

    public static String updateNoService(String addInput) {
        return NO_SERVICES_YET + addInput;
    }

    public static String NO_SERVICES_RUNNING_YET = "There are no services running yet - there must be services running before they can be ";

    public static String updateNoServicesRun(String addInput) {
        return NO_SERVICES_RUNNING_YET + addInput;
    }

    public static String NO_SERVICES_ADDED = "There are no services added as yet - you must add services before their details can be ";

    public static String updateNoServicesAdded(String addInput) {
        return NO_SERVICES_ADDED + addInput;
    }
}
