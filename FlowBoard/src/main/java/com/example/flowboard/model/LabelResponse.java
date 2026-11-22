package com.example.flowboard.model;

public record LabelResponse(String name, String color) {
    public static LabelResponse from(Label label){
        return new LabelResponse(label.getName(), label.getColor());
    }
}
