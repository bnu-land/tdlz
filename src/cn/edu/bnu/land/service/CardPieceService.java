package cn.edu.bnu.land.service;



import java.util.List;

import cn.edu.bnu.land.model.CardPiece;

public interface CardPieceService {
	public void addCard(CardPiece cp);
	public void deleteCard(Integer id);
	public void modifyCard(CardPiece cp);
	public List<CardPiece> showCard();
	public List<CardPiece> showBySFZ(String sfz);
}
