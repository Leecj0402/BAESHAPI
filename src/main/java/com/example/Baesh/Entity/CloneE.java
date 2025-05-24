package com.example.Baesh.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Clone")
public class CloneE {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String clone_id;

    @Column
    private String chat_history;

    @Column
    private String memo;

    public CloneE(){}

}
