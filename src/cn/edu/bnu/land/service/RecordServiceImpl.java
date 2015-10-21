package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.Record;
import cn.edu.bnu.land.model.RecordDAO;
import cn.edu.bnu.land.service.RecordService;
@Service
public class RecordServiceImpl implements RecordService{

	RecordDAO recordDAO;

	public RecordDAO getRecordDAO() {
		return recordDAO;
	}
	@Autowired
	public void setRecordDAO(RecordDAO recordDAO) {
		this.recordDAO = recordDAO;
	}
	
	public void createRecord(Record record)
	{
		recordDAO.save(record);
	}
	
	public void changeStatus(Record record)
	{
		recordDAO.update(record);
	}
	
	public List<Record> showRecord()
	{
		return recordDAO.findAll();
	}
	
	public Record findRecord(Integer id)
	{
		return recordDAO.get(id);
	}

	public List<Record> showRecordByProject(String id) {
		// TODO Auto-generated method stub
		return (List<Record>)recordDAO.getByProject(id);
	}
	
}
