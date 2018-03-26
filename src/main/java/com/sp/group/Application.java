package com.sp.group;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        log.info("Creating friends table");

        jdbcTemplate.execute("DROP TABLE friends IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE friends(" +
                "first_email VARCHAR(255), second_email VARCHAR(255))");
        
        
        log.info("Creating subscribe table");

        jdbcTemplate.execute("DROP TABLE subscribe IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE subscribe(" +
                "requestor VARCHAR(255), target VARCHAR(255), "
                + "CONSTRAINT PK_Subscribe PRIMARY KEY (requestor,target))");
        
        log.info("Creating block table");

        jdbcTemplate.execute("DROP TABLE block IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE block(" +
                "requestor VARCHAR(255), target VARCHAR(255), "
                + "CONSTRAINT PK_Block PRIMARY KEY (requestor,target))");

    }
}