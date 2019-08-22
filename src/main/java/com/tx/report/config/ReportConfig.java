package com.tx.report.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "tx.report")
public class ReportConfig {
    private String locations;
    private List<DatasourceConfig> datasourceConfigs;

    public List<DatasourceConfig> getDatasourceConfigs() {
        return datasourceConfigs;
    }

    public void setDatasourceConfigs(List<DatasourceConfig> datasourceConfigs) {
        this.datasourceConfigs = datasourceConfigs;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }
}
