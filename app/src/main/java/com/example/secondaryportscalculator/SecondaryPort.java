package com.example.secondaryportscalculator;

public class SecondaryPort {
    // fields
    private String portName;
    private String standardPortName;
    private double differenceHWSprings;
    private double differenceLWSprings;
    private double differenceHWNeaps;
    private double differenceLWNeaps;
    // constructors
    public SecondaryPort() {}
    public SecondaryPort(
            String port_name,
            String standard_port_name,
            double difference_hw_springs,
            double difference_hw_neaps,
            double difference_lw_neaps,
            double difference_lw_springs) {
        this.portName = port_name;
        this.standardPortName = standard_port_name;
        this.differenceHWSprings = difference_hw_springs;
        this.differenceHWNeaps = difference_hw_neaps;
        this.differenceLWNeaps = difference_lw_neaps;
        this.differenceLWSprings = difference_lw_springs;
    }
    // properties
    public void setPortName(String port_name) {
        this.portName = port_name;
    }
    public String getPortName() {
        return this.portName;
    }

    public void setStandardPortName(String standard_port_name) {
        this.standardPortName = standard_port_name;
    }
    public String getStandardPortName() {
        return this.standardPortName;
    }

//    public void setPortData(
//            double difference_hw_springs,
//            double difference_lw_springs,
//            double difference_hw_neaps,
//            double difference_lw_neaps) {
//        this.differenceHWSprings = difference_hw_springs;
//        this.differenceHWNeaps = difference_hw_neaps;
//        this.differenceLWNeaps = difference_lw_neaps;
//        this.differenceLWSprings = difference_lw_springs;
//    }

    public void setDifferenceHWSprings(double difference_hw_springs) {
        this.differenceHWSprings = difference_hw_springs;
    }
    public double getDifferenceHWSprings() {
        return this.differenceHWSprings;
    }

    public void setDifferenceHWNeaps(double difference_hw_neaps) {
        this.differenceHWNeaps = difference_hw_neaps;
    }
    public double getDifferenceHWNeaps() {
        return this.differenceHWNeaps;
    }

    public void setDifferenceLWNeaps(double difference_lw_neaps) {
        this.differenceLWNeaps = difference_lw_neaps;
    }
    public double getDifferenceLWNeaps() {
        return this.differenceLWNeaps;
    }

    public void setDifferenceLWSprings(double difference_lw_springs) {
        this.differenceLWSprings = difference_lw_springs;
    }
    public double getDifferenceLWSprings() {
        return this.differenceLWSprings;
    }
}
