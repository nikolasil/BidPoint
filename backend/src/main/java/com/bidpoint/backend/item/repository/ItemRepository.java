package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item,Long> {
    Item findItemById(UUID id);

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

    Page<Item> findAllByDateEndsGreaterThan(ZonedDateTime dateEnds, Pageable pageable);
    Page<Item> findAllByDateEndsLessThan(ZonedDateTime dateEnds, Pageable pageable);
    Long countAllByDateEndsGreaterThan(ZonedDateTime dateEnds);
    Long countAllByDateEndsLessThan(ZonedDateTime dateEnds);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByDateEndsGreaterThan(String searchTerm, ZonedDateTime dateEnds, Pageable pageable);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByDateEndsLessThan(String searchTerm, ZonedDateTime dateEnds, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByDateEndsGreaterThan(String searchTerm, ZonedDateTime dateEnds);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByDateEndsLessThan(String searchTerm, ZonedDateTime dateEnds);

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

    Page<Item> findAllByActiveAndDateEndsGreaterThan(boolean active, ZonedDateTime dateEnds, Pageable pageable);
    Page<Item> findAllByActiveAndDateEndsLessThan(boolean active, ZonedDateTime dateEnds, Pageable pageable);
    Long countAllByActiveAndDateEndsGreaterThan(boolean active, ZonedDateTime dateEnds);
    Long countAllByActiveAndDateEndsLessThan(boolean active, ZonedDateTime dateEnds);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActiveAndDateEndsGreaterThan(String searchTerm, boolean active, ZonedDateTime dateEnds, Pageable pageable);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActiveAndDateEndsLessThan(String searchTerm, boolean active, ZonedDateTime dateEnds, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActiveAndDateEndsGreaterThan(String searchTerm, boolean active, ZonedDateTime dateEnds);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActiveAndDateEndsLessThan(String searchTerm, boolean active, ZonedDateTime dateEnds);

    Page<Item> findAllByUserAndDateEndsGreaterThan(User user, ZonedDateTime dateEnds, Pageable pageable);
    Page<Item> findAllByUserAndDateEndsLessThan(User user, ZonedDateTime dateEnds, Pageable pageable);
    Long countAllByUserAndDateEndsGreaterThan(User user, ZonedDateTime dateEnds);
    Long countAllByUserAndDateEndsLessThan(User user, ZonedDateTime dateEnds);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.user = :user AND i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByUserAndDateEndsGreaterThan(String searchTerm, User user, ZonedDateTime dateEnds, Pageable pageable);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.user = :user AND i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByUserAndDateEndsLessThan(String searchTerm, User user, ZonedDateTime dateEnds, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.user = :user AND i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByUserAndDateEndsGreaterThan(String searchTerm, User user, ZonedDateTime dateEnds);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.user = :user AND i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByUserAndDateEndsLessThan(String searchTerm, User user, ZonedDateTime dateEnds);

    Page<Item> findAllByActiveAndDateEndsGreaterThanAndUser(boolean active, ZonedDateTime dateEnds, User user, Pageable pageable);
    Page<Item> findAllByActiveAndDateEndsLessThanAndUser(boolean active, ZonedDateTime dateEnds, User user, Pageable pageable);
    Long countAllByActiveAndDateEndsGreaterThanAndUser(boolean active, ZonedDateTime dateEnds, User user);
    Long countAllByActiveAndDateEndsLessThanAndUser(boolean active, ZonedDateTime dateEnds, User user);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActiveAndDateEndsGreaterThanAndUser(String searchTerm, boolean active, ZonedDateTime dateEnds, User user, Pageable pageable);
    @Query( value = "SELECT i FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Item> searchItemsByActiveAndDateEndsLessThanAndUser(String searchTerm, boolean active, ZonedDateTime dateEnds, User user, Pageable pageable);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND i.dateEnds > :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActiveAndDateEndsGreaterThanAndUser(String searchTerm, boolean active, ZonedDateTime dateEnds, User user);
    @Query( value = "SELECT COUNT(DISTINCT i) FROM Item i WHERE " +
            "i.active = :active AND i.user = :user AND i.dateEnds < :dateEnds AND (" +
            "i.name LIKE CONCAT('%', :searchTerm, '%')" +
            "OR i.description LIKE CONCAT('%', :searchTerm, '%'))")
    Long countSearchItemsByActiveAndDateEndsLessThanAndUser(String searchTerm, boolean active, ZonedDateTime dateEnds, User user);

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
