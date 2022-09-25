package com.digitalbook.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.digitalbook.RoleRepository;
import com.digitalbook.UserRepository;
import com.digitalbook.entity.ERole;
import com.digitalbook.entity.Role;
import com.digitalbook.entity.User;
import com.digitalbook.service.impl.UserServiceImpl;

/**
 * 
 * @author supriya
 * This is UserServiceImplTest which is used for testing UserServiceImpl methods
 *
 */

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	RoleRepository roleRepository;
	
	@InjectMocks
	UserServiceImpl userService;
	
	Role getAuthorRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_AUTHOR);
		return role;
	}
	
	Role getReaderRole() {
		Role role = new Role();
		role.setId(1);
		role.setName(ERole.ROLE_READER);
		return role;
	}
	
	User getAuthor() {
		User author = new User();
		author.setId(1);
		author.setName("Author");
		author.setEmailId("author@gmail.com");
		author.setUserName("author");
		author.setPassword("123456");
		Set<Role> roles = new HashSet<>();
		roles.add(getAuthorRole());
		author.setRoles(roles);
		return author;
	}
	
	User getReader() {
		User reader = new User();
		reader.setId(1);
		reader.setName("Reader");
		reader.setEmailId("reader@gmail.com");
		reader.setUserName("reader");
		reader.setPassword("123456");
		Set<Role> roles = new HashSet<>();
		roles.add(getReaderRole());
		reader.setRoles(roles);
		return reader;
	}
	
	@Test
	void testGetAuthor(){
		Optional<User> author = Optional.of(getAuthor());
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_AUTHOR;
		Optional<Role> userRole = Optional.of(getAuthorRole());
		when(roleRepository.findByName(roleUser)).thenReturn(userRole);
		when(userRepository.findById(authorId)).thenReturn(author);
		User actual = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		assertEquals(author.get(),actual);
	}
	
	@Test
	void testGetAuthor1(){
		Optional<User> author = Optional.empty();
		User user = null;
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_AUTHOR;
		Optional<Role> userRole = Optional.of(getAuthorRole());
		when(roleRepository.findByName(roleUser)).thenReturn(userRole);
		when(userRepository.findById(authorId)).thenReturn(author);
		User actual = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		assertEquals(user,actual);
	}
	
	@Test
	void testGetAuthor2(){
		Optional<User> reader = Optional.of(getReader());
		User user = null;
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_AUTHOR;
		Optional<Role> userRole = Optional.of(getAuthorRole());
		when(roleRepository.findByName(roleUser)).thenReturn(userRole);
		when(userRepository.findById(authorId)).thenReturn(reader);
		User actual = userService.getUser(authorId, ERole.ROLE_AUTHOR);
		assertEquals(user,actual);
	}
	
	@Test
	void testGetAuthor3(){
		Integer authorId = 1;
		ERole roleUser = ERole.ROLE_READER;
		Exception e = new RuntimeException("Error: Role is not found.");
		when(roleRepository.findByName(roleUser)).thenThrow(e);
		assertThrows(RuntimeException.class,()->{
			userService.getUser(authorId, ERole.ROLE_AUTHOR);
		});
	}
	
}
