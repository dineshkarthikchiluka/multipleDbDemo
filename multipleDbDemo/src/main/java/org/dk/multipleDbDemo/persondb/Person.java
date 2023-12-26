package org.dk.multipleDbDemo.persondb;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String personName;
}
// datasourceproperties.class
// datasourcebuilder.class

// if there is method which is non transactional in simplejpa -> then we will still require transaction manager

// update persistence policy
// if we are adding something
// table which are earlier using now we are not using it.
// with update you cannot add a constraint
// adding a column or adding a new table can be possible with update
// deletion is not possile with update.