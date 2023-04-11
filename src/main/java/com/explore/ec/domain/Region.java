package com.explore.ec.domain;

public enum Region {
    North_India("North India"), South_India("South India"), Central_India("Central India");
    private String label;
    private Region(String label) {
        this.label = label;
    }
    public static Region findByLabel(String byLabel) {
        for(Region r: Region.values()) {
            if (r.label.equalsIgnoreCase(byLabel))
                return r;
        }
        return null;
    }


}
