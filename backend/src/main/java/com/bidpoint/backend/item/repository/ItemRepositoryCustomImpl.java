package com.bidpoint.backend.item.repository;

import com.bidpoint.backend.item.dto.SearchQueryOutputDto;
import com.bidpoint.backend.item.entity.Category;
import com.bidpoint.backend.item.entity.Item;
import com.bidpoint.backend.item.enums.FilterMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.util.Pair;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SearchQueryOutputDto getItemsSearchPageableSortingFiltering(List<String> categories, String searchTerm, FilterMode active, String username, FilterMode isEnded, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> item = query.from(Item.class);
        List<Predicate> predicates = new  ArrayList<>();

        for(String category : categories) {
            Subquery<UUID> subquery = query.subquery(UUID.class);
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

        query
                .select(item)
                .where(predicates.toArray(new Predicate[predicates.size()]))
                .orderBy(QueryUtils.toOrders(pageable.getSort(), item, cb));

        TypedQuery<Item> tQuery =  entityManager.createQuery(query);

        return new SearchQueryOutputDto(new PageImpl<Item>(tQuery.getResultList(), pageable, tQuery.getResultList().size()), (long) tQuery.getResultList().size());
    }
}