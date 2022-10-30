package ru.otus.spring.belov.product_service.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files_info")
public class FileInfo {

    /** Идентификатор */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    /** Тип документа */
    @Column(name = "content_type", nullable = false)
    private String contentType;

    /** Тип бизнес сущности к которой относится файл */
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_category", nullable = false)
    private EntityCategory entityCategory;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;
}
