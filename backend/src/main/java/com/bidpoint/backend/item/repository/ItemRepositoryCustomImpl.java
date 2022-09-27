package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.item.dto.SearchItemQueryOutputDto;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.query.QueryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.time.ZoneOffset.UTC;
@Slf4j
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private Long getTotalCount(CriteriaBuilder criteriaBuilder, Predicate[] predicateArray) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Item> root = criteriaQuery.from(Item.class);

        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicateArray);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public SearchItemQueryOutputDto getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, PageRequest pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> item = query.from(Item.class);
        List<Predicate> predicates = new  ArrayList<>();

        for(String category : categories) {
            log.info(category);
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Item> subqueryItem = subquery.from(Item.class);
            Join<Category, Item> subqueryCategory = subqueryItem.join("categories");

            subquery.select(subqueryItem.get("id")).where(
                    cb.equal(subqueryCategory.get("name"), category));

            predicates.add(cb.in(item.get("id")).value(subquery));
        }

        if(!Objects.equals(searchTerm, ""))
            predicates.add(
                    cb.or(
                            cb.like(item.get("name"), "%" + searchTerm + "%"),
                            cb.like(item.get("description"), "%" + searchTerm + "%")
                    )
            );
        if(active != FilterMode.NONE)
            predicates.add(active == FilterMode.TRUE ?
                    cb.equal(item.get("active"), true) :
                    cb.equal(item.get("active"), false));
        if(!Objects.equals(username, ""))
            predicates.add(cb.equal(item.get("user").get("username"), username));
        if(isEnded != FilterMode.NONE)
            predicates.add(isEnded == FilterMode.TRUE ?
                    cb.greaterThan(item.get("dateEnds"), ZonedDateTime.now(UTC)) :
                    cb.lessThan(item.get("dateEnds"), ZonedDateTime.now(UTC)));

        Predicate[] predicatesArray = predicates.toArray(new Predicate[predicates.size()]);

        query
                .select(item)
                .where(predicatesArray)
                .orderBy(QueryUtils.toOrders(pageable.getSort(), item, cb));

        TypedQuery<Item> tQuery =  entityManager.createQuery(query);
        tQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
        tQuery.setMaxResults(pageable.getPageSize());
        Long totalCount = getTotalCount(cb, predicatesArray);
        return new SearchItemQueryOutputDto(new PageImpl<Item>(tQuery.getResultList(), pageable, totalCount), totalCount);
    }
}