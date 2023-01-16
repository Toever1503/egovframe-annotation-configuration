package test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
