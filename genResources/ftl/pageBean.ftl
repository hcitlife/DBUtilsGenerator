package ${pkg};

import ${pageParamWithPkg};
import java.util.List;

public class PageBean<T> {
    private List<T> records;
    private int pageNum;	//当前页码
    private int totalPageNum;	//总页码

    private int pageSize = Const.PAGE_SIZE;	//每页显示的记录条数
    private int totalRecordsNum;	//总记录条数

    private int prePageNum;	//上一页
    private int nextPageNum;	//下一页
    private int startIndex;	//每页开始记录的索引

    private String url;	//查询分页的请求的地址(通过这个属性可以将页面中的分页部分给抽取成公共部分)

	public PageBean() {
	
	}
	
    public PageBean(PageParam pageParam, int totalRecordsNum) {
    	this.pageNum = pageParam.getPage();
    	this.pageSize = pageParam.getRows();
    	this.totalRecordsNum = totalRecordsNum;

    	//计算总页码
    	totalPageNum = totalRecordsNum % pageSize == 0 ? totalRecordsNum / pageSize : (totalRecordsNum / pageSize + 1);
   	 	startIndex = (pageNum - 1) * pageSize;   //计算开始记录的索引
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getPrePageNum() {
        prePageNum = pageNum - 1;
        if (prePageNum < 1) {
            prePageNum = 1;
        }
        return prePageNum;
    }

    public void setPrePageNum(int prePageNum) {
        this.prePageNum = prePageNum;
    }

    public int getNextPageNum() {
        nextPageNum = pageNum + 1;
        if (nextPageNum > totalPageNum) {
            nextPageNum = totalPageNum;
        }
        return nextPageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecordsNum() {
        return totalRecordsNum;
    }

    public void setTotalRecordsNum(int totalRecordsNum) {
        this.totalRecordsNum = totalRecordsNum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
	    return "PageBean{" +
		    "records=" + records +
		    ", pageNum=" + pageNum +
		    ", totalPageNum=" + totalPageNum +
		    ", pageSize=" + pageSize +
		    ", totalRecordsNum=" + totalRecordsNum +
		    ", prePageNum=" + prePageNum +
		    ", nextPageNum=" + nextPageNum +
		    ", startIndex=" + startIndex +
		    ", url='" + url + '\'' +
		'}';
	}
}