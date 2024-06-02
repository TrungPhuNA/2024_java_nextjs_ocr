package be.ocrapi.service.Certificate;

import be.ocrapi.model.Category;
import be.ocrapi.model.Certificate;
import be.ocrapi.request.CategoryRequest;
import be.ocrapi.request.CertificateRequest;
import be.ocrapi.request.RankRequest;
import be.ocrapi.response.Certificate.CertificateResponse;
import be.ocrapi.response.Certificate.ListCertificateResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CertificateServiceInterface {
    CertificateResponse findById(Integer id);
    ListCertificateResponse findAll(int page, int page_size);
    CertificateRequest save(CertificateRequest data);
    CertificateRequest update(int id, CertificateRequest data);
    void delete(int order);
}
