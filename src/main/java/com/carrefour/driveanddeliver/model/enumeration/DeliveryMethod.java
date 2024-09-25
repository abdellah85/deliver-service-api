package com.carrefour.driveanddeliver.model.enumeration;

public enum DeliveryMethod {
    DRIVE("Drive"),
    DELIVERY("Delivery"),
    DELIVERY_TODAY("Delivery Today"),
    DELIVERY_ASAP("Delivery ASAP");

    private final String label;

    DeliveryMethod(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
