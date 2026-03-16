package com.geovanna.br.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.geovanna.br.entities.Jogo;
import com.geovanna.br.services.JogoService;

@RestController
@RequestMapping("/jogos")
public class JogoController {

	
	private final JogoService jogoService;
	
	public JogoController(JogoService jogoService) { 
		this.jogoService = jogoService; 
	}
	
	@GetMapping("/home")
	public String paginaInicial() { 
		return "index";
	}
	@GetMapping("/{id}")
	public ResponseEntity<Jogo> getJogo(@PathVariable Long id) {
		
		Jogo jogo = jogoService.getJogoById(id);
		
		if(jogo !=null) {
			return ResponseEntity.ok(jogo);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@PostMapping
	  public ResponseEntity<Jogo> createJogo(@RequestPart("jogo") Jogo jogo, 
			  @RequestPart("thumbnail") MultipartFile thumbnail) throws IOException {
		  
		 jogo.setThumbnail(thumbnail.getBytes()); 
		 
		 Jogo jogoSalvo= jogoService.saveJogo(jogo);
		  return ResponseEntity.status(HttpStatus.CREATED).body(jogoSalvo);
		  
	  }
	@GetMapping
	public List<Jogo> getAllJogos() {
		return jogoService.getAllJogos();
	}
	
	@DeleteMapping("/{id}")
	public void deleteJogo(@PathVariable Long id) {
		jogoService.deleteJogo(id);
	}
	
	
}
