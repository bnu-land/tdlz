package cn.edu.bnu.land.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.bnu.land.service.DBBackupRecoeryService;

/*
 * ���@Controller���
 * 2013-8-29  @LF
 */
@Controller
public class DBBackupRecoveryController {
	private DBBackupRecoeryService backupRecoveryService;

	/*
	 * ���@Autowire��� 2013-8-29 @LF
	 */
	@Autowired
	public DBBackupRecoveryController(DBBackupRecoeryService backupRecoveryService) {
		this.backupRecoveryService = backupRecoveryService;
	}

	/*
	 * 查询记录
	 * 2013-8-29 @LF
	 */
	@RequestMapping(value = "/get_BackupRecoveryList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> handle(
			@RequestParam("start") String start,
			@RequestParam("limit") String limit
			) throws IOException {

		// System.out.println("time to getInfoArticleList");
		// System.out.println(start);
		// System.out.println(limit);
		// System.out.println();
		Map<String, Object> myBackupRecordList = this.backupRecoveryService.getBackupData(start, limit);
		System.out.println("backupRecord Count:"+myBackupRecordList.get("total"));
		return (myBackupRecordList);
	}

}