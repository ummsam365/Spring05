package vo;

import java.util.List;

public class PageVO {
	
	private List<BoardVO> list; // 조회결과 출력할 글 목록
	private int currPage; // 출력할(요청받은) PageNo 
	private int perPage=5;  // 1페이지당 보여줄 Row(Record,튜플) 갯수
	private int totalCount; // 전체 row 갯수
	private int sno; // start Row No
	private int eno; // end Row No
	private int perPageNO=3; // 한 화면에 출력되는 PageNo 갯수 (paging2 에서 사용)
						  
	public List<BoardVO> getList() {
		return list;
	}
	public void setList(List<BoardVO> list) {
		this.list = list;
	}
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getEno() {
		return eno;
	}
	public void setEno(int eno) {
		this.eno = eno;
	}
	public int getPerPageNO() {
		return perPageNO;
	}
	public void setPerPageNO(int perPageNO) {
		this.perPageNO = perPageNO;
	}
	@Override
	public String toString() {
		return "PageVO [list=" + list + ", currPage=" + currPage + ", perPage=" + perPage + ", totalCount=" + totalCount
				+ ", sno=" + sno + ", eno=" + eno + ", perPageNO=" + perPageNO + "]";
	}

} // class
