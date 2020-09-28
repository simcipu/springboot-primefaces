package it.laura.palestra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface Repository<E, ID extends Serializable> extends JpaSpecificationExecutor<E>, JpaRepository<E, ID> {

}
