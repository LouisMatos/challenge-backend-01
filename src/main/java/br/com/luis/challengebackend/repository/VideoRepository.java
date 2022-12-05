package br.com.luis.challengebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.luis.challengebackend.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

	public boolean existsByUrl(String url);

	public List<Video> findByTituloContains(String titulo);

}
