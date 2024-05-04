package com.alassaneniang.buckpal.account.adapter.outgoing.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime timestamp;
    @Column
    private Long ownerAccountId;
    @Column
    private Long sourceAccountId;
    @Column
    private Long targetAccountId;
    @Column
    private Long amount;

}
