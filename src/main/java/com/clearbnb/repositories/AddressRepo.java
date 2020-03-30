package com.clearbnb.repositories;

import com.clearbnb.entities.Address;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@Repository
public interface AddressRepo extends CrudRepository<Address, Integer>, JpaRepository<Address, Integer>, JpaSpecificationExecutor<Address> {
    public Address findById(int id);

    public List<Address> findByRegionContaining(String region);

    public List<Address> findByCityContaining(String city);

    /*public List<Address> findByRegionContains(String region);
    public List<Address> findByRegionIsContaining(String region);*/
}