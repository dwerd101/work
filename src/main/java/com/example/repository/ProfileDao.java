package com.example.repository;

import com.example.model.ProfileResultView;
import com.example.specification.ProfileResultPredicate;
import com.example.specification.ProfileResultSpecificationConsumer;
import com.example.specification.SearchCriteria2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Consumer;


@Repository
public class ProfileDao implements ProfileResultDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ProfileResultView> searchProfile(List<SearchCriteria2> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ProfileResultView> query = builder.createQuery(ProfileResultView.class);
        final Root r = query.from(ProfileResultView.class);

        Predicate predicate = builder.conjunction();
        ProfileResultSpecificationConsumer searchConsumer = new ProfileResultSpecificationConsumer(predicate, builder, r);
        params.stream().forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void save(ProfileResultView entity) {
        entityManager.persist(entity);
    }
}
