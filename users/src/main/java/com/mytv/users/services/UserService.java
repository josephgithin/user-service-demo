package com.mytv.users.services;

import com.mytv.users.model.GetUserRequest;
import com.mytv.users.model.User;
import com.mytv.users.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * user service interface
 */
@Service
public interface UserService {

    List<UserDTO> findUser(GetUserRequest request, Pageable pageable);

}
