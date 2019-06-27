package ${pkg};

public class PageParam {
	private int page;	//当前显示的页面
	private int rows;  //每页有多少条数据

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public PageParam(int page, int rows) {
		super();
		this.page = page;
		this.rows = rows;
	}

	public PageParam() {
		super();
	}

    @Override
    public String toString() {
	    return "PageParam{" +
		    "page=" + page +
		    ", rows=" + rows +
		'}';
	}
}
