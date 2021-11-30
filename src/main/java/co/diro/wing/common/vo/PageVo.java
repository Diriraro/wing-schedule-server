package co.diro.wing.common.vo;

public class PageVo {
	private int curPage = 1;
	private int blockSize = 5;
	private int pageListSize = 10;
	private int startIndex;
	private int totalListSize;
	private int totalPage;
	private boolean isPaging = true;
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		if(curPage <= 0) {
			curPage = 1;
		}
		this.curPage = curPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getBlockSize() {
		return blockSize;
	}
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}
	public int getPageListSize() {
		return pageListSize;
	}
	public void setPageListSize(int pageListSize) {
		this.pageListSize = pageListSize;
	}
	public int getTotalListSize() {
		return totalListSize;
	}
	public void setTotalListSize(int totalListSize) {
		this.totalListSize = totalListSize;
		this.startIndex = (this.curPage-1) * this.pageListSize;
		this.totalPage = this.totalListSize % this.pageListSize == 0 ? this.totalListSize / this.pageListSize : this.totalListSize / this.pageListSize + 1;
		
	}
	public boolean getIsPaging() {
		return isPaging;
	}
	public void setIsPaging(boolean isPaging) {
		this.isPaging = isPaging;
	}
}
