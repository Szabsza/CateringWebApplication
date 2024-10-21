package edu.bbte.idde.csim2126.backend.util;

import lombok.Data;

@Data
public class Config {
    private String databaseType = "mem";
    private String driverClass;
    private String jdbcUrl;
    private String username = "";
    private String password = "";

}
