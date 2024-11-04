package be.ocrapi.service.Attendance;

import be.ocrapi.model.BonusAndDiscipline;
import be.ocrapi.model.TimeAttendance;
import be.ocrapi.model.User;
import be.ocrapi.repository.AttendanceRepository;
import be.ocrapi.repository.BonusAndDisciplineRepository;
import be.ocrapi.repository.UserRepository;
import be.ocrapi.request.AttendanceRequest;
import be.ocrapi.request.BonusAndDisciplineRequest;
import be.ocrapi.response.Attendance.AttendanceResponse;
import be.ocrapi.response.Attendance.ListAttendanceResponse;
import be.ocrapi.response.Bonus.BonusResponse;
import be.ocrapi.response.Bonus.ListBonusResponse;
import be.ocrapi.response.MappingResponseDto;
import be.ocrapi.service.BonusAndDiscipline.BonusAndDisciplineServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AttendanceService implements AttendanceServiceInterface {
    @Autowired
    private AttendanceRepository repository;

    @Autowired
    private MappingResponseDto responseDto;

    public static Date parseDate(String dateString, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format: " + e.getMessage());
            return null;
        }
    }
    @Autowired
    private UserRepository userRepository;
    public TimeAttendance createOrUpdateData(AttendanceRequest dataRequest, TimeAttendance oldData) {
        TimeAttendance newData = oldData;
        if(oldData == null) {
            newData = new TimeAttendance();
            newData.setCreated_at(new Date());
        }
        User user = newData.getUser();
        if(dataRequest.getUser_id() != null) {
            user = userRepository.getById(dataRequest.getUser_id());
        }

        newData.setFull_name(dataRequest.getFull_name());
        newData.setEmail(dataRequest.getEmail());
        newData.setType(dataRequest.getType());
        newData.setStatus(dataRequest.getStatus());
        if(dataRequest.getCheck_in() != null ) {
            Date date = this.parseDate(dataRequest.getCheck_in() , "yyyy-MM-dd");
            newData.setCheck_in(date);
        }
        if(dataRequest.getCheck_out() != null) {
            Date date = this.parseDate(dataRequest.getCheck_out() , "yyyy-MM-dd");
            newData.setCheck_out(date);
        }
        newData.setUser(user);
        newData.setUpdated_at(dataRequest.getUpdated_at());
        return newData;
    }

    @Override
    public AttendanceResponse findById(Integer id) {
        return responseDto.getInfoAttendance(repository.getById(id));
    }

    @Override
    public ListAttendanceResponse findAll(int page, int page_size) {
        Pageable pageable = PageRequest.of(page, page_size);
        Page<TimeAttendance> results = repository.findAll(pageable);

        ListAttendanceResponse dataListResponse = new ListAttendanceResponse();
        dataListResponse.setTotal(results.getTotalElements());

        if(results.isEmpty()) {
            dataListResponse.setData(new ArrayList<>());
            return dataListResponse;
        }

        List<AttendanceResponse> data = new ArrayList<>();
        for (TimeAttendance item: results) {
            data.add(responseDto.getInfoAttendance(item));
        }
        dataListResponse.setData(data);
        return dataListResponse;
    }

    @Override
    public ListAttendanceResponse findAndCount(int page, int page_size,
                                               String from_date,
                                               String to_date,
                                               String status,
                                               String user_id,
                                               String type) {

        List<TimeAttendance> results = repository.getList(from_date, to_date, type, user_id, status , page, page_size);
        Long total = repository.totalFilter(from_date, to_date,type, user_id, status);
        ListAttendanceResponse dataListResponse = new ListAttendanceResponse();
        dataListResponse.setTotal(total);

        if(results.isEmpty()) {
            dataListResponse.setData(new ArrayList<>());
            return dataListResponse;
        }

        List<AttendanceResponse> data = new ArrayList<>();
        for (TimeAttendance item: results) {
            data.add(responseDto.getInfoAttendance(item));
        }
        dataListResponse.setData(data);
        return dataListResponse;
    }

    @Override
    public AttendanceRequest save(AttendanceRequest dataRequest) {
        TimeAttendance o = this.createOrUpdateData(dataRequest, null);
        repository.save(o);
        return dataRequest;
    }

    @Override
    public AttendanceRequest update(int id, AttendanceRequest dataRequest) {
        var c = repository.getById(id);
        if(c != null) {
            TimeAttendance o = this.createOrUpdateData(dataRequest, c);

            repository.save(o);
            return dataRequest;
        }
        throw new RuntimeException("Không tìm thấy dữ liệu");
    }


    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }
}
