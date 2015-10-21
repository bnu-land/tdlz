package cn.edu.bnu.land.model;

import java.util.Date;
import java.util.List;

public interface RecordDAO {

	public void save(Record record);
	public void update(Record record);
	public Record get(Integer id);
	public List<Record> findAll();
	public List<Record> getByProject(String id);
}
