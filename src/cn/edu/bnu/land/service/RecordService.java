package cn.edu.bnu.land.service;

import java.util.List;

import cn.edu.bnu.land.model.Record;

public interface RecordService {
	public void createRecord(Record record);
	public void changeStatus(Record record);
	public List<Record> showRecord();
	public Record findRecord(Integer id);
	public List<Record> showRecordByProject(String id);
}
