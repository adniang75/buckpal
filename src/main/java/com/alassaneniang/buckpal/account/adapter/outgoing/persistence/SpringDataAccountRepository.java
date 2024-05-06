package com.alassaneniang.buckpal.account.adapter.outgoing.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataAccountRepository extends JpaRepository<AccountJpaEntity, Long> {

}
