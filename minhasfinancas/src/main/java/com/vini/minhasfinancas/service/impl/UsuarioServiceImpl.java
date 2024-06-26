package com.vini.minhasfinancas.service.impl;

import java.util.Optional;

//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vini.minhasfinancas.exception.ErroAutenticacao;
import com.vini.minhasfinancas.exception.RegraNegocioException;
import com.vini.minhasfinancas.model.entity.Usuario;
import com.vini.minhasfinancas.model.repository.UsuarioRepository;
import com.vini.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;
    private PasswordEncoder encoder;

    public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
        super();
        this.repository = repository;
        this.encoder=encoder;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if (!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
        }
        boolean senhasBatem = encoder.matches(senha, usuario.get().getSenha());
        if (!senhasBatem) {
            throw new ErroAutenticacao("Senha invalida!.");
        }
        return usuario.get();
    }

    @Override
    @Transactional // serve para garantir que a transação seja fechada ao final do método
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        criptografaSenha(usuario);
        return repository.save(usuario);
    }

    private void criptografaSenha(Usuario usuario) {
        String senha = usuario.getSenha();
        String senhaCripto = encoder.encode(senha);
        usuario.setSenha(senhaCripto);
    }


    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }

}
