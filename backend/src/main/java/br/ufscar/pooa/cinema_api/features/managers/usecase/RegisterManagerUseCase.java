package br.ufscar.pooa.cinema_api.features.managers.usecase;

import br.ufscar.pooa.cinema_api.domain.repositories.manager.IManagerRepository;
import br.ufscar.pooa.cinema_api.domain.repositories.user.IUserRepository;
import br.ufscar.pooa.cinema_api.features._shared.exceptions.ResourceAlreadyExistsException;
import br.ufscar.pooa.cinema_api.features.managers.dto.ManagerResponseDTO;
import br.ufscar.pooa.cinema_api.features.managers.dto.RegisterManagerRequestDTO;
import br.ufscar.pooa.cinema_api.features.managers.mapper.IManagerMapper;
import br.ufscar.pooa.cinema_api.domain.entities.Manager;
import br.ufscar.pooa.cinema_api.domain.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterManagerUseCase implements IRegisterManagerUseCase {

    private final IUserRepository userRepository;
    private final IManagerRepository managerRepository;
    private final IManagerMapper IManagerMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterManagerUseCase(IUserRepository userRepository, IManagerRepository managerRepository, IManagerMapper IManagerMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.IManagerMapper = IManagerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ManagerResponseDTO execute(RegisterManagerRequestDTO requestDTO) {
        Optional<User> userExists = userRepository.findByEmail(requestDTO.getEmail());
        if (userExists.isPresent()) {
            throw new ResourceAlreadyExistsException("User", "email", requestDTO.getEmail());
        }

        Optional<Manager> managerExists = managerRepository.findByCpf(requestDTO.getCpf());
        if (managerExists.isPresent()) {
            throw new ResourceAlreadyExistsException("Manager", "CPF", requestDTO.getCpf());
        }

        Manager newManager = IManagerMapper.toManager(requestDTO);
        newManager.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        Manager savedUser = managerRepository.save(newManager);

        return IManagerMapper.toManagerResponseDTO(savedUser);
    }
}
