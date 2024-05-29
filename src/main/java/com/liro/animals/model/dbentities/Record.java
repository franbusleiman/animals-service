package com.liro.animals.model.dbentities;

import com.liro.animals.model.dbentities.idclasses.AnimalsSharedUserIdIdClass;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "records", indexes = {
        @Index(name = "vet_user_index", columnList = "vetUserId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String dataString;

    @ManyToOne(optional = false)
    @JoinColumn(name = "record_type_id", nullable = false)
    private RecordType recordType;

    private String details;
    private Boolean validData;

    @ManyToOne(optional = false)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    private Long vetUserId;
}