package com.liro.animals.model.dbentities;

import javax.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "record_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String recordType;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "recordType")
    private Set<Record> records;
}