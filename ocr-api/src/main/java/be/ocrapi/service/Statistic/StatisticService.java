package be.ocrapi.service.Statistic;

import be.ocrapi.model.Certificate;
import be.ocrapi.model.Rank;
import be.ocrapi.model.Room;
import be.ocrapi.model.User;
import be.ocrapi.repository.*;
import be.ocrapi.request.CertificateRequest;
import be.ocrapi.response.Certificate.CertificateResponse;
import be.ocrapi.response.Certificate.ListCertificateResponse;
import be.ocrapi.response.MappingResponseDto;
import be.ocrapi.response.Statistic.StatisticBonusResponse;
import be.ocrapi.response.Statistic.StatisticResponse;
import be.ocrapi.response.Statistic.StatisticUserResponse;
import be.ocrapi.service.Certificate.CertificateServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatisticService implements StatisticServiceInterface {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BonusAndDisciplineRepository bonusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RankRepository rankRepository;


    @Autowired
    private MappingResponseDto responseDto;

    @Override
    public StatisticResponse getStatistic(String month) {
        Integer totalUser = userRepository.countByConditions("ACTIVE", "","", "", "", "", "USER");
        Long totalRoom = roomRepository.countRoomByStatus("ACTIVE");
        Long totalRank = rankRepository.countRankByStatus("ACTIVE");
        Long totalBonus = rankRepository.countRankByStatus("BONUS");
        Long totalDiscipline = rankRepository.countRankByStatus("DISCIPLINE");
        List<StatisticBonusResponse> bonusData = new ArrayList<>();
        bonusData.add(new StatisticBonusResponse("BONUS", totalBonus));
        bonusData.add(new StatisticBonusResponse("DISCIPLINE", totalDiscipline));
        var rooms = roomRepository.findAll();
        var ranks = rankRepository.findAll();
        List<StatisticUserResponse> roomData = new ArrayList<>();
        List<StatisticUserResponse> rankData = new ArrayList<>();
        if(rooms != null) {
            for (Room room: rooms) {
                Integer totalItem = userRepository.countByConditions("ACTIVE", "","", "", room.getId() + "", "", "USER");
                roomData.add(new StatisticUserResponse(room.getName(), Long.parseLong(totalItem + "")));
            }
        }
        if(ranks != null) {
            for (Rank room: ranks) {
                Integer totalItem = userRepository.countByConditions("ACTIVE", "","", room.getId() + "","",  "", "USER");
                rankData.add(new StatisticUserResponse(room.getName(), Long.parseLong(totalItem + "")));
            }
        }
        StatisticResponse data = new StatisticResponse();
        data.setTotal_room(totalRoom);
        data.setTotal_rank(totalRank);
        data.setTotal_user(Long.parseLong(totalUser + ""));
        data.setUser_rooms(roomData);
        data.setUser_ranks(rankData);
        data.setData_bonus(bonusData);
        return data;
    }
}
