package com.bidpoint.backend.item.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue
    private UUID id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] fileData;

    private String fileDescription;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
