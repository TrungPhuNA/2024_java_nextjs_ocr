package be.ocrapi.response;

import be.ocrapi.model.*;
import be.ocrapi.response.Attendance.AttendanceResponse;
import be.ocrapi.response.Bonus.BonusResponse;
import be.ocrapi.response.Certificate.CertificateResponse;
import be.ocrapi.response.EmployerType.TypeResponse;
import be.ocrapi.response.Rank.RankResponse;
import be.ocrapi.response.Room.RoomResponse;
import be.ocrapi.response.Salary.SalaryResponse;
import be.ocrapi.response.User.UserRelationResponse;
import be.ocrapi.response.User.UserResponse;
import be.ocrapi.response.WorkingTask.WorkingTaskResponse;
import org.springframework.stereotype.Service;

@Service
public class MappingResponseDto {

    public UserResponse getUserInfo(User data) {
        UserResponse newData = new UserResponse();
        newData.setId(data.getId());
        newData.setName(data.getName());
        newData.setStatus(data.getStatus());
        newData.setCccd(data.getCccd());
        newData.setCode(data.getCode());
        newData.setAvatar(data.getAvatar());
        newData.setAddress(data.getAddress());
        newData.setCccdDate(data.getCccdDate());
        newData.setGender(data.getGender());
        newData.setStatus(data.getStatus());
        newData.setRegion(data.getRegion());
        newData.setDob(data.getDob());
        newData.setEmail(data.getEmail());
        newData.setPhone(data.getPhone());
        newData.setUserType(data.getUserType());
        newData.setCccdAddress(data.getCccdAddress());

        newData.setCertificate(getInfoCertificate(data.getCertificate()));
        newData.setRank(getInfoRank(data.getRank()));
        newData.setRoom(getInfoRoom(data.getRoom()));
        newData.setEmployerType(getInfoEmployerType(data.getEmployerType()));
        return newData;
    }

    public TypeResponse getInfoEmployerType(EmployerType data) {
        if(data != null) {
            TypeResponse newData = new TypeResponse();
            newData.setId(data.getId());
            newData.setName(data.getName());
            newData.setDescription(data.getDescription());
            newData.setStatus(data.getStatus());
            newData.setCreated_at(data.getCreated_at());
            newData.setUpdated_at(data.getUpdated_at());
            UserRelationResponse u = new UserRelationResponse();
            if(data.getUser() != null)  {
                newData.setUser(new UserRelationResponse(data.getUser().getId(),
                        data.getUser().getName(), data.getUser().getEmail(),
                        data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
            }
            else {
                newData.setUser(null);
            }
            return newData;
        }
        return null;
    }

    public RankResponse getInfoRank(Rank data) {
        if(data == null) {
            return null;
        }
        RankResponse newData = new RankResponse();
        newData.setId(data.getId());
        newData.setName(data.getName());
        newData.setCode(data.getCode());
        newData.setSalary(data.getSalary());
        newData.setStatus(data.getStatus());
        newData.setUser_id(data.getUser().getId());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());
        UserRelationResponse u = new UserRelationResponse();
        if(data.getUser() != null)  {
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        }
        else {
            newData.setUser(null);
        }
        return newData;
    }

    public CertificateResponse getInfoCertificate(Certificate data) {
        if(data == null) {
            return null;
        }
        CertificateResponse newData = new CertificateResponse();
        newData.setId(data.getId());
        newData.setName(data.getName());
        newData.setDescription(data.getDescription());
        newData.setStatus(data.getStatus());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());

        UserRelationResponse u = new UserRelationResponse();
        if(data.getUser() != null)  {
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        }
        else {
            newData.setUser(null);
        }
        return newData;
    }

    public RoomResponse getInfoRoom(Room data) {
        if(data == null) {
            return null;
        }
        RoomResponse newData = new RoomResponse();
        newData.setId(data.getId());
        newData.setName(data.getName());
        newData.setDescription(data.getDescription());
        newData.setStatus(data.getStatus());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());

        UserRelationResponse u = new UserRelationResponse();
        if(data.getUser() != null)  {
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        }
        else {
            newData.setUser(null);
        }
        return newData;
    }

    public SalaryResponse getInfoSalary(Salary data) {
        if(data == null) {
            return null;
        }
        SalaryResponse newData = new SalaryResponse();
        newData.setId(data.getId());

        newData.setAllowance(data.getAllowance());
        newData.setReceive_salary(data.getReceive_salary());
        newData.setFrom_date(data.getFrom_date());
        newData.setTo_date(data.getTo_date());
        newData.setWorkday(data.getWorkday());

        newData.setSalary(data.getSalary());
        newData.setStatus(data.getStatus());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());

        if(data.getUser() != null)  {
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        }
        else {
            newData.setUser(null);
        }

        if(data.getUpdatedBy() != null)  {
            newData.setUpdated_by(new UserRelationResponse(data.getUpdatedBy().getId(),
                    data.getUpdatedBy().getName(), data.getUpdatedBy().getEmail(),
                    data.getUpdatedBy().getCode(), data.getUpdatedBy().getGender(), data.getUpdatedBy().getAvatar()));
        }
        else {
            newData.setUpdated_by(null);
        }

        return newData;
    }

    public BonusResponse getInfoBonus(BonusAndDiscipline data) {
        if(data == null) {
            return null;
        }
        BonusResponse newData = new BonusResponse();
        newData.setId(data.getId());
        newData.setType(data.getType());
        newData.setName(data.getName());
        newData.setContent(data.getContent());
        newData.setStatus(data.getStatus());
        newData.setData_value(data.getDataValue());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());

        if(data.getUser() != null)  {
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        }
        else {
            newData.setUser(null);
        }
        if(data.getUpdatedBy() != null)  {
            newData.setUpdated_by(new UserRelationResponse(data.getUpdatedBy().getId(),
                    data.getUpdatedBy().getName(), data.getUpdatedBy().getEmail(),
                    data.getUpdatedBy().getCode(), data.getUpdatedBy().getGender(), data.getUpdatedBy().getAvatar()));
        }
        return newData;
    }

    public WorkingTaskResponse getInfoWorkingTask(WorkingTasks data) {
        if(data == null) {
            return null;
        }
        WorkingTaskResponse newData = new WorkingTaskResponse();
        newData.setId(data.getId());
        newData.setBonus(data.getBonus());
        newData.setName(data.getName());
        newData.setDescription(data.getDescription());
        newData.setStatus(data.getStatus());
        newData.setTo_date(data.getTo_date());
        newData.setFrom_date(data.getFrom_date());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());

        UserRelationResponse u = new UserRelationResponse();
        if(data.getUser() != null)  {
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        }
        else {
            newData.setUser(null);
        }
        return newData;
    }



    public AttendanceResponse getInfoAttendance(TimeAttendance data) {
        if(data == null) {
            return null;
        }
        AttendanceResponse newData = new AttendanceResponse();
        newData.setId(data.getId());
        newData.setType(data.getType());
        newData.setCheck_in(data.getCheck_in());
        newData.setCheck_out(data.getCheck_out());
        newData.setStatus(data.getStatus());
        newData.setFull_name(data.getFull_name());
        newData.setEmail(data.getEmail());
        newData.setStatus(data.getStatus());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());

        if(data.getUser() != null)  {
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        }
        else {
            newData.setUser(null);
        }

        return newData;
    }
}
