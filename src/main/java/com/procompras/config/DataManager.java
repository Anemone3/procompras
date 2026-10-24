package com.procompras.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DataManager implements AutoCloseable {

    private final EntityManagerFactory emf;

    public DataManager() {
        this.emf = Persistence.createEntityManagerFactory("persistence-procompras");
    }

    public EntityManagerFactory getEntitiyManager() {
        return this.emf;
    }

    @Override
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
