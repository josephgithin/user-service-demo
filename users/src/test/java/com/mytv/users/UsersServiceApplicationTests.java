package com.mytv.users;

import com.mytv.users.model.GetUserRequest;
import com.mytv.users.model.User;
import com.mytv.users.model.UserDTO;
import com.mytv.users.repositories.UserRepository;
import com.mytv.users.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceApplicationTests {

	@Autowired
	UserService userServiceImpl;

	@Test
	public void testFindUser() {
		User u = new User();
		u.setId(1000L);
		GetUserRequest request = new GetUserRequest();
		request.setEmail(Optional.of("Scott.Zimmerman@MyTV.com"));
		UserRepository userRepository = org.mockito.Mockito.mock(UserRepository.class);
		when(userRepository.findById(1001L)).thenReturn(Optional.of(u));
		UserDTO user = userServiceImpl.findUser(request,
				 Pageable.ofSize(1)).get(0);
		Assert.assertEquals(Optional.of(1001L).get(),user.getId());
	}
	
}