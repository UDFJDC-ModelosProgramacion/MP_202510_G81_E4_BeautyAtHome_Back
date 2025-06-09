package co.edu.udistrital.mdp.beautyathome.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class ServiceRecordEntity extends BaseEntity{
    private LocalDate datePerformed;

    @ElementCollection
    @CollectionTable(name = "record_result_images", joinColumns = @JoinColumn(name = "record_id"))
    @Column(name = "image_url")
    private Set<String> resultImageUrls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_record_id", unique = true)
    private ReviewEntity review;
}
