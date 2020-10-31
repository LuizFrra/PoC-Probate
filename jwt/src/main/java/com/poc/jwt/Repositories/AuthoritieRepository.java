package com.poc.jwt.Repositories;

import com.poc.jwt.Models.Authoritie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthoritieRepository extends CrudRepository<Authoritie, Long> {
}
