package pl.sda.jdbcjpa;

import com.google.common.collect.Lists;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class JpaMain {


    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("yyy"); //yyy - nazwa konfiguracji z persistence entity manager faktory potrzebuje tego żeb stworzyć encje

    public static void main(String[] args) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        createCustomer(entityManager);
        findCustomerByLastName(entityManager, "Kowalski");
        System.out.println(findCustomerByPesel("123"));
        createCustomerWithOrders(entityManager);
        Customer customerByPesel = findCustomerByPesel("124");
        entityManager.close();
        ENTITY_MANAGER_FACTORY.close();


    }

    private static List<Customer> findCustomerByLastName(EntityManager entityManager, String xxx) {
        TypedQuery<Customer> query = entityManager.createQuery("select c from Customer c where lastName=:lName", Customer.class);
        query.setParameter("lName", xxx);
        List<Customer> resultList = query.getResultList();
        System.out.println(resultList);
        return resultList;
    }

    public static Customer createCustomer(EntityManager entityManager) {
        Customer customer = new Customer();
        customer.setFirstName("Jan");
        customer.setLastName("Kowalski");
        customer.setAge(30);
        customer.setPesel("123");
        customer.setStatus(CustomerStatus.ACTIVATED);
        customer.getNickname().add("Johny");
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return customer;
    }

    public static Customer findCustomerByPesel(String pesel) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery(
                "select c from Customer c where pesel = :pp", Customer.class);
        query.setParameter("pp", pesel);
        return query.getSingleResult();
    }

    public static Customer createCustomerWithOrders(EntityManager entityManager) {

        Customer customer = new Customer();
        customer.setFirstName("Jan");
        customer.setLastName("Marcinkowki");
        customer.setAge(30);
        customer.setPesel("124");
        customer.setStatus(CustomerStatus.ACTIVATED);
        customer.getNickname().add("Jszczur");
        Order o1 = new Order(null, new BigDecimal(500), customer.getLastName(), customer );
        Order o2 = new Order(null, new BigDecimal(1500), customer.getLastName(), customer);

        customer.setOrders(Lists.newArrayList(o1,o2));
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);

        entityManager.getTransaction().begin();
        entityManager.persist(o1);
        entityManager.persist(o2);
        entityManager.persist(cart);
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return customer;
    }
}
