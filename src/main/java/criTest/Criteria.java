package criTest;

//Criteria : (판단이나 결정을 위한) 기준
// => 출력할 Row 를 select 하기 위한 클래스 
public class Criteria {
	
	private int currPage;   // 출력할(요청받은) PageNo
	private int perPageRow; // 1페이지당 보여줄 Row(Record,튜플) 갯수
	private int sno; // startRowNum
	private int eno; // endRowNum
	
	// 생성자로 초기화
	public Criteria() {
		this.currPage=1;
		this.perPageRow=5;
	}
	
	// 1) 출력할(요청받은) PageNo set  
	public void setCurrPage(int currPage) {
		if (currPage<1) currPage=1;
		this.currPage = currPage;
	}
	// 2) 1페이지당 보여줄 Row(Record,튜플) 갯수 확인
	public void setPerPageRow(int perPageRow) {
		if (perPageRow<1 || perPageRow>50)
			perPageRow=5;
		this.perPageRow = perPageRow;
	}
	// 3) startRowNo , endRowNo 계산
	public void setSnoEno() {
		if (sno<1) sno=1;
		sno = (currPage-1)*perPageRow+1;// startRowNum 계산
		eno = (sno+perPageRow)-1;		// endRowNum 계산
	}
 	
	public int getCurrPage() {
		return currPage;
	}
	public int getPerPageRow() {
		return perPageRow;
	}
	public int getSno() {
		return sno;
	}
	public int getEno() {
		return eno;
	}
	
	//method for MySQL Mapper - startRowNum
	//public int getSno() {
	//	return (this.currPage -1)* PerPageRow;
	//}
	
	@Override
	public String toString() {
		return "Criteria [currPage=" + currPage + ", perPageRow=" + perPageRow + ", sno=" + sno + ", eno=" + eno + "]";
	}
} //class
