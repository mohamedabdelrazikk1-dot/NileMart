package org.route;

public enum CategoryEnum {

    ELECTRONICS("Electronics"),
    FASHION("Fashion"),
    HOME_KITCHEN("Home & Kitchen"),
    BEAUTY("Beauty"),
    BOOKS("Books");

    private final String label;

    CategoryEnum(String label) {
        this.label = label;
    }
}