package pl.sda.jdbcjpa;

import pl.sda.jdbcjpa.BaseEntity;
import pl.sda.jdbcjpa.jpa.JpaMain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaDao  { // DAO - DATA ACCESS OBJECT

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("yyy"); //yyy - nazwa konfiguracji z persistence entity manager faktory potrzebuje tego żeb stworzyć encje

    //zakłądamy jednowątkowość
    private static EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();


    public static <E extends BaseEntity> void saveEntity(E entity){
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();

    }

    public static EntityManagerFactory getEMF() {
        return ENTITY_MANAGER_FACTORY;
    }

    public static EntityManager getEM() {
        return entityManager;
    }
}
