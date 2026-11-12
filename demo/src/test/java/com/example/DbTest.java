package com.example;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;

@Singleton
class DbTest {

    private final DataSource dataSource;

    public DbTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @EventListener
    void startup(StartupEvent event) {
        try (Connection c = dataSource.getConnection()) {
            System.out.println("SUCCESS: " + c.getMetaData().getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}