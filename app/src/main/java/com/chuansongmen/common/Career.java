package com.chuansongmen.common;

public enum Career {
    WORKER("员工"), DRIVER("司机");
    private String description;

    Career(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
