package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PhoneRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhoneHibernateRepository extends AbstractHibernateRepository<Phone, Long> implements PhoneRepository {
    public PhoneHibernateRepository() {
        setEntityClass(Phone.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }
}
