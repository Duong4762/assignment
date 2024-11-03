package com.demo.assignment.security;

import com.demo.assignment.entity.NguoiDung;
import com.demo.assignment.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        NguoiDung nguoiDung = nguoiDungRepository.findByTaiKhoan(username);
        if (nguoiDung == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(nguoiDung);
    }

    public UserDetails loadUserById(Integer id) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(id).get();
        return new CustomUserDetails(nguoiDung);
    }
}
