package org.sysc4806.sysc4806_group20.Repository;

import org.flywaydb.core.Flyway;

public class migration {
    public static void main(String[] args) {
        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:h2:file:./data/testdb", "sa", "password")
                .cleanDisabled(false)
                .load();

        flyway.clean();
        flyway.migrate();

    }
}
