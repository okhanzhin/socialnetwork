package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PhoneRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneSpringRepository extends AbstractSpringRepository<Phone, Long>, PhoneRepository {
}
