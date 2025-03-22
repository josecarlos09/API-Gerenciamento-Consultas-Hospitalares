package com.sistema.gerenciamento.hospitalar.configs.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Classe responsável por fornecer funcionalidades para geração de tokens JWT na aplicação.
 *
 * Esta classe utiliza a biblioteca JJWT para criar tokens seguros, baseados em uma chave secreta
 * e em um tempo de expiração definido nas configurações da aplicação.
 */
@Component
public class TokenJwt {

    private static final Logger logger = LogManager.getLogger(TokenJwt.class);

    //Chave secreta utilizada para assinar os tokens JWT. */
    @Value("${autorizacao.jwtSecret}")
    private String jwtSecret;

    //Tempo de expiração do token JWT em milissegundos. */
    @Value("${autorizacao.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Método responsável por gerar um token JWT com base na autenticação do usuário.
     *
     * @param authentication Objeto contendo as informações do usuário autenticado.
     * @return Token JWT gerado.
     */
    public String gerarJwt(Authentication authentication) {
        UsuarioDetailsImpl usuarioPrincipal = (UsuarioDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject(usuarioPrincipal.getUsername()) // Define o nome do usuário como "subject" do token
                .issuedAt(new Date()) // Define a data de emissão do token
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Define a data de expiração do token
                .signWith(getSecretKey()) // Assina o token com a chave secreta
                .compact(); // Retorna o token gerado
    }

    /**
     * Obtém a chave secreta para assinatura do token JWT.
     *
     * @return Instância de SecretKey utilizada para assinar os tokens.
     */
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsernameJwt(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Valida um token JWT.
     *
     * <p>Este método tenta ValidadorHorarioAntecedencia o token JWT fornecido. Caso o token seja válido, retorna {@code true}.
     * Caso contrário, captura e registra a exceção correspondente e retorna {@code false}.</p>
     *
     * @param authToken O token JWT a ser validado.
     * @return {@code true} se o token for válido, {@code false} caso contrário.
     */
    public boolean validacaoJwt(String authToken) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey()) // verifica se o token está expirado
                    .build()// inicia validação
                    .parseSignedClaims(authToken);
            return true;
        } catch (SecurityException e) {
            logger.error("Assinatura JWT inválida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("O Token JWT não é suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("A string de reivindicações do JWT está vazia: {}", e.getMessage());
        }
        return false;
    }
}