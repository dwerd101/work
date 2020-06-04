package com.example.repository;

import com.example.model.ProfileResultView;
import com.example.specification.ProfileResultConsumer;
import com.example.specification.SearchCriteria;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository
public class ProfileResultDaoRep implements ProfileResultDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<ProfileResultView> searchProfile(List<SearchCriteria> params) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<ProfileResultView> query = builder.createQuery(ProfileResultView.class);
        final Root r = query.from(ProfileResultView.class);

        Predicate predicate = builder.conjunction();
        ProfileResultConsumer searchConsumer = new ProfileResultConsumer(predicate, builder, r);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void save(ProfileResultView entity) {
        entityManager.persist(entity);
    }
}
