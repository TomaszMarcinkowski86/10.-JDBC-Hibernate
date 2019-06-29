package pl.sda.jdbcjpa.jpa2;

import com.google.common.collect.Lists;
import javafx.geometry.Pos;
import pl.sda.jdbcjpa.JpaDao;

import javax.persistence.Entity;
import javax.persistence.EntityManager;


public class JpaMain2  {

    public static void main(String[] args) {
        addPost("title 0","body 0");
        addPost("title 1","body 1");
        Integer post2Id = addPost("title 2", "body 2");
        addPost("title 3","body 3");

        findPostById(post2Id);

    }

    private static Post findPostById(Integer id) {
        EntityManager em = JpaDao.getEM();
        Post post = em.find(Post.class, id);
        System.out.println(post);
        return post;
    }

    private static Integer  addPost(String title, String body) {
        Comment c1 = new Comment();

        Post post = Post.builder()
                .title(title)
                .body(body)
                .build();
        System.out.println("Przed zapisem" + post);
        JpaDao.saveEntity(post);
        System.out.println("Po zapisie" + post);
        return post.getId();
    }

}
