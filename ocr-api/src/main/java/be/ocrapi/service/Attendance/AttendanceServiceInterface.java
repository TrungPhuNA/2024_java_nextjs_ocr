package be.ocrapi.service.Attendance;

import be.ocrapi.request.AttendanceRequest;
import be.ocrapi.request.BonusAndDisciplineRequest;
import be.ocrapi.response.Attendance.AttendanceResponse;
import be.ocrapi.response.Attendance.ListAttendanceResponse;
import be.ocrapi.response.Bonus.BonusResponse;
import be.ocrapi.response.Bonus.ListBonusResponse;

public interface AttendanceServiceInterface {
    AttendanceResponse findById(Integer id);
    ListAttendanceResponse findAll(int page, int page_size);
    AttendanceRequest save(AttendanceRequest data);
    ListAttendanceResponse findAndCount(int page, int page_size, String from_date,
                                        String to_date, String status, String user_id, String type);

    AttendanceRequest update(int id, AttendanceRequest data);
    void delete(int order);
}
