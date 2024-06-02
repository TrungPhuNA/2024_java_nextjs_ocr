package be.ocrapi.response.WorkingTask;

import be.ocrapi.response.Salary.SalaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ListWorkingTaskResponse {
    private List<WorkingTaskResponse> data;
    private Long total;
}
