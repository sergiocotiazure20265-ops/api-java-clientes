package br.com.cotiinformatica.api_java_clientes.services;

import br.com.cotiinformatica.api_java_clientes.entities.Cliente;
import br.com.cotiinformatica.api_java_clientes.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este e-mail.");
        }

        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este CPF.");
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente não encontrado com o ID: " + id)
                );
    }

    public Cliente atualizar(Long id, Cliente dadosAtualizados) {
        Cliente cliente = buscarPorId(id);

        if (!cliente.getEmail().equals(dadosAtualizados.getEmail())
                && clienteRepository.existsByEmail(dadosAtualizados.getEmail())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este e-mail.");
        }

        if (!cliente.getCpf().equals(dadosAtualizados.getCpf())
                && clienteRepository.existsByCpf(dadosAtualizados.getCpf())) {
            throw new RuntimeException("Já existe um cliente cadastrado com este CPF.");
        }

        cliente.setNome(dadosAtualizados.getNome());
        cliente.setEmail(dadosAtualizados.getEmail());
        cliente.setTelefone(dadosAtualizados.getTelefone());
        cliente.setCpf(dadosAtualizados.getCpf());

        return clienteRepository.save(cliente);
    }

    public void excluir(Long id) {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}