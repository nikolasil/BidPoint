package com.bidpoint.backend.recommendation.entity;

import com.vladmihalcea.hibernate.type.array.DoubleArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recommendation")
@TypeDef(
        name = "double_array",
        typeClass = DoubleArrayType.class
)
public class Recommendation {
    @Id
    @SequenceGenerator(
            name = "recommendation_sequence",
            sequenceName = "recommendation_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "recommendation_sequence")
    private Long id;

    @Type(
            type = "double_array",
            parameters = @org.hibernate.annotations.Parameter(
                    name = "sql_array_type",
                    value = "double"
            )
    )
    @Column(
            name = "predictions",
            columnDefinition = "float8[][]"
    )
    private Double[][] predictions;

    @CreationTimestamp
    private ZonedDateTime dateCreated;
}
