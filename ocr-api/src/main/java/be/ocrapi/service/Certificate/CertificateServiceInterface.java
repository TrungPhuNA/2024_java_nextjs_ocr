package be.ocrapi.service.Certificate;

import be.ocrapi.model.Category;
import be.ocrapi.model.Certificate;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.CertificateRequest;
import be.ocrapi.request.RankRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CertificateServiceInterface {
    Optional<Certificate> findById(Integer id);
    Page<Certificate> findAll(int page, int page_size);
    CertificateRequest save(CertificateRequest data);
    CertificateRequest update(int id, CertificateRequest data);
    void delete(int order);
}
