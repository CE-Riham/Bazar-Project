package org.common.enums;

public enum Ports {
    CATALOG_SERVICE_PORT("8081"),
    ORDER_SERVICE_PORT("8082"),
    FRONT_TIER_PORT("1218");

    private final String port;

    Ports(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
