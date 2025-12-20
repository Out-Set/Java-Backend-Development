package com.pk.quartz.scheduler.businessobject;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pk.quartz.scheduler.domainobject.JOBSchedulerDetail;
//import com.nucleus.finnone.pro.scheduler.dao.IJOBSchedulerDAO;
import com.pk.quartz.scheduler.domainobject.JOBSchedulerExecutionLog;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Named("jobSchedulerBusinessObject")
public class JOBSchedulerBusinessObject implements IJOBSchedulerBusinessObject {
	//@Inject
	//@Named("jobSchedulerDAO")
	//private IJOBSchedulerDAO jobSchedulerDAO;

	public List<JOBSchedulerDetail> getNeoJobsByTriggerName(String triggerName) {
		//return this.jobSchedulerDAO.getNeoJobsByTriggerName(triggerName);
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public JOBSchedulerExecutionLog createJobExectutionLog(JOBSchedulerExecutionLog schedulerExecutionLog) {
		//return this.jobSchedulerDAO.createJobExectutionLog(schedulerExecutionLog);
		log.info("Execution log created for: {}",schedulerExecutionLog);
		return null;
	}

	public List<JOBSchedulerDetail> getRegisteredJobs(Long tenantId) {
		//return this.jobSchedulerDAO.getRegisteredJobs(tenantId);
		return null;
	}
	 
	 /* public List<JOBSchedulerDetailVO>
	 * getJOBSchedulerDetailByTriggerGroupAndName(String triggerGroupName, String
	 * triggerName) { return
	 * this.jobSchedulerDAO.getJOBSchedulerDetailByTriggerGroupAndName(
	 * triggerGroupName, triggerName); }
	 * 
	 * public List<JOBSchedulerDetailVO>
	 * getJOBSchedulerDetailVOByJOBGroupAndName(String jobGroupName, String jobName)
	 * { return
	 * this.jobSchedulerDAO.getJOBSchedulerDetailVOByJOBGroupAndName(jobGroupName,
	 * jobName); }
	 * 
	 * public List<JOBSchedulerDetailVO>
	 * getJOBSchedulerDetailByTriggerGroupAndName(String triggerGroupName, String
	 * triggerName, List<Integer> approvalStatus) { return
	 * this.jobSchedulerDAO.getJOBSchedulerDetailByTriggerGroupAndName(
	 * triggerGroupName, triggerName, approvalStatus); }
	 * 
	 * public List<JOBSchedulerDetailVO>
	 * getJOBSchedulerDetailVOByJOBGroupAndName(String jobGroupName, String jobName,
	 * List<Integer> approvalStatus) { return
	 * this.jobSchedulerDAO.getJOBSchedulerDetailVOByJOBGroupAndName(jobGroupName,
	 * jobName, approvalStatus); }
	 */

	public List<JOBSchedulerDetail> getJobSchedulerProcessByProcessGroup(Long id, List<Integer> approvalStatus) {
		//return this.jobSchedulerDAO.getJobSchedulerProcessByProcessGroup(id, approvalStatus);
		return null;
	}

	public JOBSchedulerDetail getJOBSchedulerDetailByExecutionSequence(Long id) {
		//return this.jobSchedulerDAO.getJOBSchedulerDetailByExecutionSequence(id);
		return null;
	}

	public JOBSchedulerDetail getActiveScheduledJobByJobName(String jobName, List<Integer> approvalStatus) {
		//return this.jobSchedulerDAO.getActiveScheduledJobByJobName(jobName, approvalStatus);
		return null;
	}

	public List<Long> getAllMappedProcessIdAccrossAllProcessGroups() {
		//return this.jobSchedulerDAO.getAllMappedProcessIdAccrossAllProcessGroups();
		return null;
	}

	public List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String jobGroupName, String jobName) {
		//return this.jobSchedulerDAO.getJOBSchedulerDetailByJOBGroupAndName(jobGroupName, jobName);
		return null;
	}

	public List<JOBSchedulerDetail> getJOBSchedulerDetailByJOBGroupAndName(String jobGroupName, String jobName, List<Integer> approvalStatus) {
		//return this.jobSchedulerDAO.getJOBSchedulerDetailByJOBGroupAndName(jobGroupName, jobName, approvalStatus);
		return null;
	}

	@Override
	public JOBSchedulerExecutionLog findById(Long jobLogId, Class<JOBSchedulerExecutionLog> class1) {
		// TODO Auto-generated method stub
		return null;
	}
}