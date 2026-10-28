package com.procompras.dao;

import com.procompras.model.Product;
import jakarta.persistence.EntityManagerFactory;

public class ProductDao {
    private final EntityManagerFactory emf;

    public ProductDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Product product){
        emf.runInTransaction(em -> {
            em.persist(product);
        });
    }
}
