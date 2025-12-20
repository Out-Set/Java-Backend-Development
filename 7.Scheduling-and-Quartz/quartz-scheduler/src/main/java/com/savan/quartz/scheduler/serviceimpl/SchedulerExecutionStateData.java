package com.savan.quartz.scheduler.serviceimpl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.savan.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class SchedulerExecutionStateData {

	private ConcurrentMap<String, SchedulerJobExecutionMonitorVO> schedulerJobExecutionMonitorVOConcurrentMap = new ConcurrentHashMap<>();

    public SchedulerJobExecutionMonitorVO getSchedulerJobExecutionMonitorVO(String compositeJobAndTriggerKey) {
		return schedulerJobExecutionMonitorVOConcurrentMap.get(compositeJobAndTriggerKey);
	}
	
	public SchedulerJobExecutionMonitorVO addSchedulerJobExecutionMonitorVO(String compositeJobAndTriggerKey, SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO) {
		return schedulerJobExecutionMonitorVOConcurrentMap.put(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
	}
}