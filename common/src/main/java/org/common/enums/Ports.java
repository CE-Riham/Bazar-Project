package org.common.enums;

/**
 * This enum represents the ports used by different services in the application.
 * Each enum constant represents a specific service and its associated port.
 */
public enum Ports {
    CATALOG_SERVICE_PORT("8081"),
    ORDER_SERVICE_PORT("8082"),
    FRONT_TIER_PORT("1218");

    private final String port;

    /**
     * Constructor for the Ports enum.
     * @param port The port to be associated with the service.
     */
    Ports(String port) {
        this.port = port;
    }

    /**
     * This method returns the port associated with the service.
     * @return The port as a string.
     */
    public String getPort() {
        return port;
    }
}