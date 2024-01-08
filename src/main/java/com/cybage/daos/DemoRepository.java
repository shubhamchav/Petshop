package com.cybage.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybage.entities.Demo;
@Repository
public interface DemoRepository extends JpaRepository<Demo, Integer>{

}
