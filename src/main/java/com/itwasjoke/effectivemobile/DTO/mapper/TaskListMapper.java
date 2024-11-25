package com.itwasjoke.effectivemobile.DTO.mapper;

import com.itwasjoke.effectivemobile.DTO.TaskResponseDTO;
import com.itwasjoke.effectivemobile.entity.Task;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = TaskMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface TaskListMapper {
    List<TaskResponseDTO> map(List<Task> tasks);
}
