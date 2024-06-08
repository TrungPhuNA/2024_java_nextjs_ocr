package be.ocrapi.response.Statistic;

import be.ocrapi.response.User.UserRelationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StatisticResponse {
    private Long total_user;
    private Long total_room;
    private Long total_rank;
    private List<StatisticBonusResponse> data_bonus;
    private List<StatisticUserResponse> user_ranks;
    private List<StatisticUserResponse> user_rooms;
}
