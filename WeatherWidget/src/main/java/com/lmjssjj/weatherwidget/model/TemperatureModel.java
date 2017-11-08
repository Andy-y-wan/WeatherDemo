package com.lmjssjj.weatherwidget.model;

/**
 * Created by ya.wan on 2017/11/2.
 */

public class TemperatureModel {

    private MetricModel Metric;
    private MetricModel Imperial;
    public MetricModel getMetric() {
        return Metric;
    }
    public void setMetric(MetricModel metric) {
        Metric = metric;
    }
    public MetricModel getImperial() {
        return Imperial;
    }
    public void setImperial(MetricModel imperial) {
        Imperial = imperial;
    }
}
