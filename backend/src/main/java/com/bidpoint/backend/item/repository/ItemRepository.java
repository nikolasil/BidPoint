package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemById(Long id);

//    Pagination Queries, searching and counting
    Page<Item> findAllByActive(boolean active, Pageable pageable);
    Long countAllByActive(boolean active);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActive(String searchTerm, boolean active, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActive(String searchTerm, boolean active);

    Page<Item> findAllByUser(User user, Pageable pageable);
    Long countAllByUser(User user);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.user = :user AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByUser(String searchTerm, User user, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.user = :user AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByUser(String searchTerm, User user);

    Page<Item> findAllByDateEndsGreaterThan(ZonedDateTime min, Pageable pageable);
    Long countAllByDateEndsGreaterThan(ZonedDateTime min);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByDateEndsGreaterThan(String searchTerm, ZonedDateTime min, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByDateEndsGreaterThan(String searchTerm, ZonedDateTime min);

    Page<Item> findAllByActiveAndUser(boolean active, User user, Pageable pageable);
    Long countAllByActiveAndUser(boolean active, User user);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActiveAndUser(String searchTerm, boolean active, User user, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActiveAndUser(String searchTerm, boolean active, User user);

    Page<Item> findAllByActiveAndDateEndsGreaterThan(boolean active, ZonedDateTime min, Pageable pageable);
    Long countAllByActiveAndDateEndsGreaterThan(boolean active, ZonedDateTime min);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActiveAndDateEndsGreaterThan(String searchTerm, boolean active, ZonedDateTime min, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActiveAndDateEndsGreaterThan(String searchTerm, boolean active, ZonedDateTime min);

    Page<Item> findAllByUserAndDateEndsGreaterThan(User user, ZonedDateTime min, Pageable pageable);
    Long countAllByUserAndDateEndsGreaterThan(User user, ZonedDateTime min);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.user = :user AND i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByUserAndDateEndsGreaterThan(String searchTerm, User user, ZonedDateTime min, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.user = :user AND i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByUserAndDateEndsGreaterThan(String searchTerm, User user, ZonedDateTime min);

    Page<Item> findAllByActiveAndDateEndsGreaterThanAndUser(boolean active, ZonedDateTime min, User user, Pageable pageable);
    Long countAllByActiveAndDateEndsGreaterThanAndUser(boolean active, ZonedDateTime min, User user);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActiveAndDateEndsGreaterThanAndUser(String searchTerm, boolean active, ZonedDateTime min, User user, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND i.dateEnds > :min AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActiveAndDateEndsGreaterThanAndUser(String searchTerm, boolean active, ZonedDateTime min, User user);

    //    Search queries for all the items
    @Query( "SELECT i FROM Item i WHERE " +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%')")
    Page<Item> searchItems(String searchTerm, Pageable pageable);
    @Query( "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%')")
    Long countSearchItems(String searchTerm);
}
