package ac.su.kiosk.service;

import ac.su.kiosk.domain.Admin;
import ac.su.kiosk.domain.Category;
import ac.su.kiosk.domain.Menu;
import ac.su.kiosk.repository.AdminRepository;
import ac.su.kiosk.repository.CategoryRepository;
import ac.su.kiosk.repository.MenuRepository;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminService {
    private final MenuRepository menuRepository;
    private final StorageService storageService;
    private final CategoryRepository categoryRepository;

    private final AdminRepository adminRepository;

    Optional<Admin> findAdminByName(String name) {
        return adminRepository.findByName(name);
    }




}
