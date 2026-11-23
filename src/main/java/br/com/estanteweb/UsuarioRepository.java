package br.com.estanteweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Devolve o usuário com esse e-mail, ou null quando não existe nenhum.
    // O e-mail é UNIQUE no banco, então no máximo uma linha volta.
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, email);
        if (linhas.isEmpty()) {
            return null;
        }
        return Usuario.deMap(linhas.get(0));
    }
}
