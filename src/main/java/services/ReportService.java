package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Complaint;
import domain.Customer;
import domain.HandyWorker;
import domain.Note;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	@Autowired
	private ReportRepository reportrepository;
	
	
	public Report create() {
		Report res;
		Complaint c = new Complaint();
		Note n = new Note();
		
		c.setAttachments(new ArrayList<String>());
		n.setComments(new ArrayList<String>());
		
		Collection<Complaint> complaints = new ArrayList<>();
		Collection<Note> notes = new ArrayList<>();
		
		complaints.add(c);
		notes.add(n);
		final Date moment = new Date(System.currentTimeMillis() - 1);
		
		res = new Report();
		res.setMoment(moment);
		res.setComplaints(complaints);
		res.setNotes(notes);
		res.setAttachments(new ArrayList<String>());
		
		
		
		return res;		
	}

	public Report save(Report report) {
		Assert.notNull(report);
		Assert.isTrue(!report.isFinalMode());
		final Date moment = new Date(System.currentTimeMillis() - 1);
		report.setMoment(moment);
		return reportrepository.save(report);
	}

	public Report saveFinalMode(Report report) {
		Assert.notNull(report);
		report.setFinalMode(true);
		final Date moment = new Date(System.currentTimeMillis() - 1);
		report.setMoment(moment);
		return reportrepository.save(report);
	}
	
	public Report findOne(Integer id) {
		Assert.notNull(id);
		return reportrepository.findOne(id);
	}

	public boolean exists(Integer id) {
		Assert.notNull(id);
		return reportrepository.exists(id);
	}

	public void delete(Report report) {
		Assert.notNull(report);
		Assert.isTrue(!report.isFinalMode());
		reportrepository.delete(report);
	}

	public List<Report> findAll() {
		return reportrepository.findAll();
	}
	
	public Collection<Report> findNotFinalModeReports() {
		Collection<Report> res = reportrepository.findNotFinalModeReports();
		Assert.notEmpty(res);
		return res;
	}
	
	public Collection<Report> findFinalModeReports() {
		Collection<Report> res = reportrepository.findFinalModeReports();
		Assert.notEmpty(res);
		return res;
	}
	
	public Collection<Report> findReportsByCustomer(Customer customer){
		Assert.notNull(customer);
		Assert.isTrue(customer.getId()!=0);
		Collection<Report> res = reportrepository.findReportsByCustomerId(customer.getId());
		Assert.notEmpty(res);
		return res;
	}
	
	public Collection<Report> findReportsInFinalModeByReferee (Referee referee) {
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);
		Collection<Report> res = reportrepository.findReportsInFinalModeByRefereeId(referee.getId());
		Assert.notNull(res);
		return res;	
	}
	
	public Collection<Report> findReportsByHandyWorker(HandyWorker handyWorker){
		Assert.notNull(handyWorker);
		Assert.isTrue(handyWorker.getId()!=0);
		Collection<Report> res = reportrepository.findReportsByHandyWorkerId(handyWorker.getId());
		Assert.notEmpty(res);
		return res;
	}
	
}
