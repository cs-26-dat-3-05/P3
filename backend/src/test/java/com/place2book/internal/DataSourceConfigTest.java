package com.place2book.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootTest
class DataSourceConfigTest {

    /* Our own database doesn't need any @Qualifer annotation */
    @Autowired
    JdbcClient p3Db;

    /* To get the internal Place2Book JDBC Client, use @Qualifier */
    @Autowired
    @Qualifier("place2book")
    JdbcClient place2bookDb;

    @Test
    void selectOneOnP3Database() {
        assertThat(p3Db.sql("SELECT 1").query(Integer.class).single()).isEqualTo(1);
        assertThat(p3Db.sql("SELECT current_database()").query(String.class).single()).isEqualTo("p3_project");
    }

    @Test
    void selectOneOnPlace2BookDatabase() {
        assertThat(place2bookDb.sql("SELECT 1").query(Integer.class).single()).isEqualTo(1);
        assertThat(place2bookDb.sql("SELECT current_database()").query(String.class).single()).isEqualTo("place2book");
    }
}
