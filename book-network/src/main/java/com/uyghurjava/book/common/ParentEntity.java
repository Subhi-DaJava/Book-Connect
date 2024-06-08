package com.uyghurjava.book.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder // to use a builder pattern in child classes
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // to use this class as a parent class
@EntityListeners(AuditingEntityListener.class) // related to last modified and created by
public class ParentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt; // should be set only once when the book is created

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedAt; // only when the book is updated

    @Column(nullable = false, updatable = false)
    @CreatedBy
    private Integer createdBy; // User ID

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModified; // User ID, only when the book is updated
}
