package ms;

public class ReportsData {

	
	String CategoryName;
	String average;
	int numberOfAnswers;
	long tmpGradeSums;
	long categoryId;
	
	
	public String getCategoryName() {
		return CategoryName;
	}
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public int getNumberOfAnswers() {
		return numberOfAnswers;
	}
	public void setNumberOfAnswers(int numberOfAnswers) {
		this.numberOfAnswers = numberOfAnswers;
	}
	public long getTmpGradeSums() {
		return tmpGradeSums;
	}
	public void setTmpGradeSums(long tmpGradeSums) {
		this.tmpGradeSums = tmpGradeSums;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}


}
