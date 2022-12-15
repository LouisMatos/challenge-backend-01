package br.com.luis.challengebackend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.luis.challengebackend.model.Categoria;
import br.com.luis.challengebackend.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

	public boolean existsByUrl(String url);

	public Page<Video> findByTituloContains(String titulo, Pageable page);

	public List<Video> findByTituloContains(String titulo);

	@Query(nativeQuery = true, value = "SELECT * FROM videos v LIMIT 5")
	public List<Video> findByFree();

	public Page<Video> findAllByCategoriaId(Categoria categoria, Pageable pageable);

}
