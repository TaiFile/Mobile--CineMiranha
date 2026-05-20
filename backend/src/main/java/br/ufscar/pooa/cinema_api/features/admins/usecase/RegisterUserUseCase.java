package br.ufscar.pooa.cinema_api.features.admins.usecase;

import br.ufscar.pooa.cinema_api.domain.entities.User;
import br.ufscar.pooa.cinema_api.domain.repositories.user.IUserRepository;
import br.ufscar.pooa.cinema_api.features._shared.exceptions.ResourceAlreadyExistsException;
import br.ufscar.pooa.cinema_api.features.admins.dto.RegisterUserRequestDTO;
import br.ufscar.pooa.cinema_api.features.admins.dto.UserResponseDTO;
import br.ufscar.pooa.cinema_api.features.admins.mapper.IUserMapper;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase implements IRegisterUserUseCase {

    private final IUserRepository userRepository;
    private final IUserMapper IUserMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(IUserRepository userRepository, IUserMapper IUserMapper,
        PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.IUserMapper = IUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO execute(RegisterUserRequestDTO requestDTO) {
        Optional<User> userExists = userRepository.findByEmail(requestDTO.getEmail());

        if (userExists.isPresent()) {
            throw new ResourceAlreadyExistsException("User", "email", requestDTO.getEmail());
        }

        User newUser = IUserMapper.toUser(requestDTO);
        newUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User savedUser = userRepository.save(newUser);

        return IUserMapper.toUserResponseDTO(savedUser);
    }
}
