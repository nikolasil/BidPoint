package com.bidpoint.backend.item.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image implements Comparable<Image> {
    @Id
    @SequenceGenerator(
            name = "image_sequence",
            sequenceName = "image_sequence",
            allocationSize = 1)
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "image_sequence")
    private Long id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] fileData;

    private String fileDescription;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Override
    public int compareTo(Image o) {
        return this.id.compareTo(o.id);
    }
}
