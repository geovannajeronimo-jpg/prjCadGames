package com.geovanna.br.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geovanna.br.entities.Jogo;
import com.geovanna.br.repositories.JogoRepository;

@Service
public class JogoService {

	private final JogoRepository jogoRepository;

	@Autowired
	private JogoService(JogoRepository jogoRepository) {
		this.jogoRepository = jogoRepository;
	}

	public Jogo getJogoById(Long id) {
		return jogoRepository.findById(id).orElse(null);
	}

	public List<Jogo> getAllJogos() {
		return jogoRepository.findAll();
	}

	public Jogo saveJogo(Jogo jogo) {
		return jogoRepository.save(jogo);
	}

	public void deleteJogo(Long id) {
		jogoRepository.deleteById(id);
	}
}
