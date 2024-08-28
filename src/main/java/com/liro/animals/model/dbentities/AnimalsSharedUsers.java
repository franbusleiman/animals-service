package com.liro.animals.model.dbentities;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "animals_shared_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalsSharedUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private Boolean readOnly;
}