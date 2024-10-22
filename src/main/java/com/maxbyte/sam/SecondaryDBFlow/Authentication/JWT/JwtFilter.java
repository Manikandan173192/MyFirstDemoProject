package com.maxbyte.sam.SecondaryDBFlow.Authentication.JWT;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter implements UserDetailsService {

    @Autowired
    JwtService jwtService;

    @Autowired
    private UserInfoRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        if(authHeader != null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token);
        }
        if(userName != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = loadUserByUsername(userName);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> optionalProduct =repository.findByuserName(username);
        if(optionalProduct.isPresent()){
            UserInfo product =optionalProduct.get();
            return new UserDetailsData(product.getUserName(), product.getUserPassword());
        }
        throw new UsernameNotFoundException(username);
    }
}
