package br.unitins.tp1.teclado.service;

import br.unitins.tp1.teclado.dto.AuthRequestDTO;
import br.unitins.tp1.teclado.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(AuthRequestDTO dto);
}