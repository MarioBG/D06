package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.ReportRepository;
import domain.Report;


@Component
@Transactional
public class StringToReportConverter implements Converter<String, Report>{

	@Autowired 
	ReportRepository reportRepository;
	
	@Override
	public Report convert(String text) {
		Report res;
		int id;
		
		try {
			if (StringUtils.isEmpty(text)) {
				res = null;
			} else {
				id = Integer.valueOf(text);
				res = reportRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}
}
