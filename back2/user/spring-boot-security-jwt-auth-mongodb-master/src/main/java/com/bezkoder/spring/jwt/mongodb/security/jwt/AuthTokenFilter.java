package com.bezkoder.spring.jwt.mongodb.security.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bezkoder.spring.jwt.mongodb.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
/*Déclaration de la classe AuthTokenFilter qui étend OncePerRequestFilter pour garantir qu'elle ne s'exécute qu'une seule fois par requête.
 */

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

/*Déclaration et injection des dépendances :
JwtUtils pour les opérations sur les tokens JWT.
UserDetailsServiceImpl pour charger les détails de l'utilisateur.
Logger pour la journalisation.*/

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {

   /*Redéfinition de la méthode doFilterInternal de OncePerRequestFilter. Cette méthode contient la logique du filtre et est exécutée pour chaque requête*/

    try {
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }
    filterChain.doFilter(request, response);
  }

  /*Logique principale :
Tente de parser et de valider le token JWT à partir de la requête.
Si le token est valide, récupère le nom d'utilisateur à partir du token et charge les détails de l'utilisateur.
Crée un objet UsernamePasswordAuthenticationToken pour l'authentification.
Configure les détails de l'authentification et définit l'authentification dans le contexte de sécurité de Spring.
En cas d'exception, enregistre une erreur avec le logger.
Passe la requête et la réponse au filtre suivant dans la chaîne (filterChain.doFilter(request, response)).*/

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }
  /*Méthode parseJwt pour extraire le token JWT de l'en-tête Authorization de la requête HTTP :
Vérifie si l'en-tête Authorization contient du texte et commence par "Bearer ".
Si oui, retourne le token JWT en enlevant "Bearer " du début.
Sinon, retourne null*/

/*La classe AuthTokenFilter est un filtre de sécurité qui :

Intercepte chaque requête HTTP.
Extrait le token JWT de l'en-tête Authorization.
Valide le token JWT.
Si le token est valide, charge les détails de l'utilisateur et configure l'authentification dans le contexte de sécurité de Spring.
Enregistre les erreurs éventuelles et passe la requête au filtre suivant dans la chaîne.*/


}
