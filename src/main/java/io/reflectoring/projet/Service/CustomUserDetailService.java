package io.reflectoring.projet.Service;

import io.reflectoring.projet.Model.CustomUserDetail;
import io.reflectoring.projet.Model.User;
import io.reflectoring.projet.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user =userRepository.findUserbyEmail(email);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return  user.map(CustomUserDetail::new).get();
    }
}
