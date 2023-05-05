package com.enviro.assessment.grd001.EdwinMakwala.repository;

import com.enviro.assessment.grd001.EdwinMakwala.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {
     Optional<AccountProfile> findByAccountHolderNameAndAccountHolderSurname(String name, String surname);
}
