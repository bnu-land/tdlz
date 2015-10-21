package cn.edu.bnu.land.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bnu.land.model.CardPiece;
import cn.edu.bnu.land.model.CardPieceDAO;
import cn.edu.bnu.land.service.CardPieceService;
@Service
public class CardPieceServiceImpl implements CardPieceService{
	
	CardPieceDAO cpDAO;
	
	public CardPieceDAO getCpDAO() {
		return cpDAO;
	}
	@Autowired
	public void setCpDAO(CardPieceDAO cpDAO) {
		this.cpDAO = cpDAO;
	}

	public void addCard(CardPiece cp){
		cpDAO.save(cp);
	}
	
	public void deleteCard(Integer id){
		cpDAO.delete(id);
	}
	
	public void modifyCard(CardPiece cp){
		cpDAO.update(cp);
	}
	
	public List<CardPiece> showCard(){
		return cpDAO.findAll();
	}
	
	public List<CardPiece> showBySFZ(String sfz){
		return cpDAO.findBySFZ(sfz);
	}
	
}
