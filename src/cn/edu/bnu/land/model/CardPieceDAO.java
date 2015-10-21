package cn.edu.bnu.land.model;

import java.util.List;

public interface CardPieceDAO {
	public void save(CardPiece cp);
	public void update(CardPiece cp);
	public void delete(Integer id);
	public CardPiece getCardPiece(Integer id);
	public List<CardPiece> findBySFZ(String card);
	public List<CardPiece> findAll();
	public List<CardPiece> findByProjectID(String projectID);
}
