package pl.sda.jdbcjpa.jpa;

import com.google.common.collect.Lists;
import pl.sda.jdbcjpa.JpaDao;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManager entityManager = JpaDao.ENTITY_MANAGER_FACTORY.createEntityManager();
//        createCustomer(entityManager);
//        findCustomerByLastName(entityManager, "Kowalski");
//        System.out.println(findCustomerByPesel("123"));
        createCustomerWithOrders(entityManager);
        Customer customerByPesel = findCustomerByPesel("124");

        addNewBook("Pan Tadeusz twarda oprawa",new Book());
        addNewBook("Pan Tadeusz ebook",new Ebook());

        System.out.println(basicNamedQuery("Jan"));

        entityManager.close();
        JpaDao.getEMF().close();

    }

    private static List<Customer> basicNamedQuery(String name) {
        EntityManager entityManager = JpaDao.getEMF().createEntityManager();
        TypedQuery<Customer> findByFirstName = entityManager.createNamedQuery("findByFirstName", Customer.class);
        findByFirstName.setParameter("fn",name);
        return Lists.newArrayList(findByFirstName.getResultList());
    }
    // polimorfizm ebook i book są produktami
    private static void addNewBook(String title, Product book) {
        book.setProductName(title);
//        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(book);
//        entityManager.getTransaction().commit();
        JpaDao.saveEntity(book); // to nam zastępuje to u góry
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

        // ważne !!! musimy mieć trazakcje zanim coś z javy trafi do db
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return customer;
    }

    public static Customer findCustomerByPesel(String pesel) {
        EntityManager entityManager = JpaDao.getEMF().createEntityManager();
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
        OrderLine ol1 = new OrderLine(null,"Rower", BigDecimal.valueOf(1500.8));
        OrderLine ol2 = new OrderLine(null,"Rower", BigDecimal.valueOf(1500.8));

        Order o1 = new Order(new BigDecimal(500), customer.getLastName(),Lists.newArrayList(ol1), customer );
        Order o2 = new Order(new BigDecimal(1500), customer.getLastName(),Lists.newArrayList(ol1), customer);

        ol1.setOrderHeader(o1);
        ol1.setOrderHeader(o2);

        customer.setOrders(Lists.newArrayList(o1,o2));
        Cart cart = new Cart();
        cart.setCustomer(customer);
        customer.setCart(cart);

        entityManager.getTransaction().begin();
        entityManager.persist(o1);             // element musi trafić do persistens contecstu żeby trafć do bazy danych
        entityManager.persist(o2);
      //  entityManager.persist(cart);   // z tego zrezygnowaliśmy z racji wybrania cascade w Customerze
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
        return customer;
    }
}
