package com.vinic.minhasfinancas.api.dto;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
	private String email;
	private String nome;
	private String senha;
	
}
