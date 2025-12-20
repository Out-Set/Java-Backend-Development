package com.pk.quartz.scheduler.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Component;

import com.pk.quartz.scheduler.vo.SchedulerJobExecutionMonitorVO;

@Component("schedulerExecutionStateData")
public class SchedulerExecutionStateData {
	private ConcurrentMap<String, SchedulerJobExecutionMonitorVO> schedulerJobExecutionMonitorVOConcurrentMap = new ConcurrentHashMap<>();

	public ConcurrentMap<String, SchedulerJobExecutionMonitorVO> getSchedulerJobExecutionMonitorVOConcurrentMap() {
		return schedulerJobExecutionMonitorVOConcurrentMap;
	}
	
	public void setSchedulerJobExecutionMonitorVOConcurrentMap(ConcurrentMap<String, SchedulerJobExecutionMonitorVO> schedulerJobExecutionMonitorVOConcurrentMap) {
		this.schedulerJobExecutionMonitorVOConcurrentMap = schedulerJobExecutionMonitorVOConcurrentMap;
	}
	
	public SchedulerJobExecutionMonitorVO getSchedulerJobExecutionMonitorVO(String compositeJobAndTriggerKey) {
		return schedulerJobExecutionMonitorVOConcurrentMap.get(compositeJobAndTriggerKey);
	}
	
	public SchedulerJobExecutionMonitorVO addSchedulerJobExecutionMonitorVO(String compositeJobAndTriggerKey, SchedulerJobExecutionMonitorVO schedulerJobExecutionMonitorVO) {
		return schedulerJobExecutionMonitorVOConcurrentMap.put(compositeJobAndTriggerKey, schedulerJobExecutionMonitorVO);
	}
}