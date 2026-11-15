package org.route.Repository;

import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Repository
public interface Repository<T, ID> {

    List<T> findAll();
    Optional<T> findById(ID id);
    T save(T entity);
    
}
