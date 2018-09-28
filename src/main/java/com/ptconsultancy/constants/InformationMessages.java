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

    public static final String ALL_SERVICES_RUNNING = "All available services are already running - you cannot run any further services";
    public static final String NOT_ALL_SERVICES_RUNNING = "Not all services are running - all services must be running before they can all be restarted";
    public static final String NO_SERVICES_RUNNING = "There are no services currently running - there must be services running before they can be stopped";

    public static final String NO_SERVICES_ADDED_ENDPOINT = "There are no services added as yet - you must add services before they're endpoints may be tested";
    public static final String NO_SERVICES_RUNNING_ENDPOINT = "There are no services currently running - there must be services running before they're endpoints may be tested";

    public static final String ENDPOINT_CONFLICT = "There are host/port conflicts between imported services - please resolve before starting all";

    public static final String NO_PROJ_CONFIG = "No project configuration was previously saved - cannot load!";
    public static final String NO_PROJ_SELECTED = "No project selected - please try again!";
    public static final String PROJ_SAVED = "The project has been successfully saved";
    public static final String NO_PROJ_SEL = "No project selected - cannot save configuration!";
    public static final String STOP_BEFORE_SAVE = "Services running, would you like to stop these before saving project";

    public static final String PROJ_NAME_NOT_EMPTY = "The name of the new project cannot be empty - please choose a name";
    public static final String SERVICE_NAME_NOT_EMPTY = "The name of the new service cannot be empty - please choose a name";
    public static final String SERVICE_PORT_INTEGER = "The new service port must be an integer - please re-enter or leave empty for default value";

    public static String getUnable(String type, String input) {
        return "Unable to create " + type + ": " + input + " , there may already be a service with this name";
    }
}
