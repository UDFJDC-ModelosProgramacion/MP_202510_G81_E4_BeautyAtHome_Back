package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.beautyathome.entities.ImageEntity;

@Repository // Identifica esta interfaz como un componente de repositorio Spring
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}