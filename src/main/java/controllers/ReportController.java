package controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import services.ComplaintService;
import services.NoteService;
import services.ReportService;
import domain.Complaint;
import domain.Note;
import domain.Report;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController{

	//Services
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private NoteService noteService;
	
	//List
	@RequestMapping(value = "/referee/list", method= RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Report> reports;
		
		reports = reportService.findNotFinalModeReports();
		
		res = new ModelAndView("report/referee/list");
		res.addObject("reports", reports);
		res.addObject("requestURI", "report/referee/list.do");
		
		return res;
	}
	
	//ListFinalMode
		@RequestMapping(value = "/listFinalMode", method= RequestMethod.GET)
		public ModelAndView listFinalMode() {
			ModelAndView res;
			Collection<Report> reportsFM;
			
			reportsFM = reportService.findFinalModeReports();
			
			res = new ModelAndView("report/listFinalMode");
			res.addObject("reportsFM", reportsFM);
			res.addObject("requestURI", "report/listFinalMode.do");
			
			return res;
		}
	
	//Create and Edit
	@RequestMapping(value="/referee/create", method=RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Report report;
		
		report= reportService.create();
		res= createEditModelAndView(report);
		
		return res;
	}
	
	@RequestMapping(value="/referee/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam int reportId) {
		ModelAndView res;
		Report report;
		
		report = reportService.findOne(reportId);
		Assert.notNull(report);
		res = createEditModelAndView(report);
		
		return res;
	}
	
	//Save
	@RequestMapping(value="/referee/edit", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Report report, BindingResult binding) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(report);
		} else {
			try {
				reportService.save(report);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = createEditModelAndView(report, "report.commit.error");
			}
		}
		return res;
	}
	
	//SaveFinalMode
		@RequestMapping(value="/referee/edit", method=RequestMethod.POST, params="saveFinalMode")
		public ModelAndView saveFinalMode(@Valid Report report, BindingResult binding) {
			ModelAndView res;
			
			if (binding.hasErrors()) {
				res = createEditModelAndView(report);
			} else {
				try {
					reportService.saveFinalMode(report);
					res = new ModelAndView("redirect:listFinalMode.do");
				} catch (Throwable oops) {
					res = createEditModelAndView(report, "report.commit.error");
				}
			}
			return res;
		}
	
	//Delete
	@RequestMapping(value="/referee/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Report report, BindingResult binding) {
		ModelAndView res;
		
		try {
			reportService.delete(report);
			res = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			res= createEditModelAndView(report, "report.commit.error");
		}
		return res;
	}
	
	//Attachments
	@RequestMapping(value="/attachments", method=RequestMethod.GET)
	public ModelAndView attachments(@RequestParam int reportId){
		ModelAndView res;
		Report report;
		Collection<String> attachments;
		
		res = new ModelAndView("report/attachments");
		report = reportService.findOne(reportId);
		Assert.notNull(report);
		attachments = report.getAttachments();
		res.addObject("attachments", attachments);
		
		return res;
	}
	
	//CreateEditModelAndView
	protected ModelAndView createEditModelAndView(Report report) {
		ModelAndView res;
		res = createEditModelAndView(report, null);
		return res;
	}


	protected ModelAndView createEditModelAndView(Report report, String messageCode) {
		ModelAndView res;
		Collection<Complaint> complaints;
		Collection<Note> notes;
		Collection<String> attachments;
		
		complaints = complaintService.findAll();
		notes = noteService.findAll();
		attachments = new ArrayList<>();
		
		res = new ModelAndView("report/referee/edit");
		res.addObject("report", report);
		res.addObject("complaints", complaints);
		res.addObject("notes", notes);
		res.addObject("attachments", attachments);
		
		res.addObject("message", messageCode);
		
		return res;
	}
}
