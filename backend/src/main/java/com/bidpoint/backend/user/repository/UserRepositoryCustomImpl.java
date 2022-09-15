package com.bidpoint.backend.user.repository;

import com.bidpoint.backend.enums.FilterMode;
import com.bidpoint.backend.user.dto.SearchUserQueryOutputDto;
import com.bidpoint.backend.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.query.QueryUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private Long getTotalCount(CriteriaBuilder criteriaBuilder, Predicate[] predicateArray) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);

        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(predicateArray);

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    public SearchUserQueryOutputDto getUsersSearchPageableSorting(String searchTerm, FilterMode approved, PageRequest pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);
        List<Predicate> predicates = new  ArrayList<>();

        if(!Objects.equals(searchTerm, ""))
            predicates.add(cb.like(user.get("username"), "%" + searchTerm + "%"));
        if(approved != FilterMode.NONE)
            predicates.add(approved == FilterMode.TRUE ?
                    cb.equal(user.get("approved"), true) :
                    cb.equal(user.get("approved"), false));

        Predicate[] predicatesArray = predicates.toArray(new Predicate[predicates.size()]);

        query
                .select(user)
                .where(predicatesArray)
                .orderBy(QueryUtils.toOrders(pageable.getSort(), user, cb));

        TypedQuery<User> tQuery =  entityManager.createQuery(query);
        tQuery.setFirstResult(Math.toIntExact(pageable.getOffset()));
        tQuery.setMaxResults(pageable.getPageSize());
        Long totalCount = getTotalCount(cb, predicatesArray);
        return new SearchUserQueryOutputDto(new PageImpl<User>(tQuery.getResultList(), pageable, totalCount), totalCount);
    }
}