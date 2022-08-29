package com.mytv.users.services.impl;

import com.mytv.users.model.GetUserRequest;
import com.mytv.users.model.User;
import com.mytv.users.model.UserDTO;
import com.mytv.users.repositories.UserRepository;
import com.mytv.users.services.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default implementation of user service
 */
@Service
public class DefaultUserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public DefaultUserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
//    @HystrixCommand(fallbackMethod = "fallback")
    public List<UserDTO> findUser(GetUserRequest gr,
                                  Pageable pageable) {
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase()
                .withIgnorePaths("id");
        Example<User> example = Example.of(User.from(gr),caseInsensitiveExampleMatcher);
        return userRepository.findAll(example,pageable)
                .map(UserDTO::from)
                .stream()
                .collect(Collectors.toList());
    }
}
