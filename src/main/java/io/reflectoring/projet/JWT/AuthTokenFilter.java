package io.reflectoring.projet.JWT;

import io.reflectoring.projet.Service.UserDetailsServiceImp;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Récupérer le JWT depuis l'en-tête de la requête
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Récupérer le nom d'utilisateur à partir du JWT
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Charger les détails de l'utilisateur à partir du service UserDetailsService
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Créer un objet d'authentification et le définir dans le contexte de sécurité
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        // Continuer avec le filtre suivant
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        // Récupérer l'en-tête Authorization
        String headerAuth = request.getHeader("Authorization");

        // Vérifier si l'en-tête est valide et commence par "Bearer "
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Retourner le JWT sans le préfixe "Bearer "
            return headerAuth.substring(7);
        }
        return null;
    }
}
