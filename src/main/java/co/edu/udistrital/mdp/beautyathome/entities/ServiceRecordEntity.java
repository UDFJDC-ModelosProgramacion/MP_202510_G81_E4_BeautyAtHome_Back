package co.edu.udistrital.mdp.beautyathome.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ServiceRecordEntity extends BaseEntity{
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate datePerformed;

    @ElementCollection
    @CollectionTable(name = "record_result_images", joinColumns = @JoinColumn(name = "record_id"))
    @Column(name = "image_url")
    private List<String> resultImageUrls = new ArrayList<>();

    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @PodamExclude
    @OneToOne(mappedBy = "serviceRecord", 
    fetch = FetchType.LAZY, 
    optional = true, 
    cascade  = CascadeType.ALL, 
    orphanRemoval = true)
    private ReviewEntity review;
}
