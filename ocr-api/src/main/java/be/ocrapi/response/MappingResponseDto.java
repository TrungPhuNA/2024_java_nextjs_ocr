package be.ocrapi.response;

import be.ocrapi.model.*;
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
        newData.setSalary(getInfoSalary(data.getSalary()));
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
            newData.setUser(new UserRelationResponse(data.getUser().getId(),
                    data.getUser().getName(), data.getUser().getEmail(),
                    data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
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
        newData.setUser(new UserRelationResponse(data.getUser().getId(),
                data.getUser().getName(), data.getUser().getEmail(),
                data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
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
        newData.setUser(new UserRelationResponse(data.getUser().getId(),
                data.getUser().getName(), data.getUser().getEmail(),
                data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
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
//        newData.setUser(new UserRelationResponse(data.getUser().getId(),
//                data.getUser().getName(), data.getUser().getEmail(),
//                data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        return newData;
    }

    public SalaryResponse getInfoSalary(Salary data) {
        if(data == null) {
            return null;
        }
        SalaryResponse newData = new SalaryResponse();
        newData.setId(data.getId());
        newData.setSalary(data.getSalary());
        newData.setStatus(data.getStatus());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());
        newData.setUser(new UserRelationResponse(data.getUser().getId(),
                data.getUser().getName(), data.getUser().getEmail(),
                data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        return newData;
    }

    public BonusResponse getInfoBonus(BonusAndDiscipline data) {
        if(data == null) {
            return null;
        }
        BonusResponse newData = new BonusResponse();
        newData.setId(data.getId());
        newData.setType(data.getType());
        newData.setStatus(data.getStatus());
        newData.setCreated_at(data.getCreated_at());
        newData.setUpdated_at(data.getUpdated_at());
        newData.setUser(new UserRelationResponse(data.getUser().getId(),
                data.getUser().getName(), data.getUser().getEmail(),
                data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
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
        newData.setUser(new UserRelationResponse(data.getUser().getId(),
                data.getUser().getName(), data.getUser().getEmail(),
                data.getUser().getCode(), data.getUser().getGender(), data.getUser().getAvatar()));
        return newData;
    }
}
