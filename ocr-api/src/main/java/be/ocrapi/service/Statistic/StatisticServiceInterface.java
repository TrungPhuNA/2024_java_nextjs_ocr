package be.ocrapi.service.Statistic;

import be.ocrapi.request.CertificateRequest;
import be.ocrapi.response.Certificate.CertificateResponse;
import be.ocrapi.response.Certificate.ListCertificateResponse;
import be.ocrapi.response.Statistic.StatisticResponse;

public interface StatisticServiceInterface {
    StatisticResponse getStatistic(String month);
}
