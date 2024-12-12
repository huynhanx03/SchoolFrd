package com.scs.identity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scs.identity.entity.InvalidatedToken;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {}
