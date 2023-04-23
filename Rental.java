import java.util.Date;

public class Rental {
	private Video video ;
//	private int status ; // 0 for Rented, 1 for Returned
	private Date rentDate ;
	private Date returnDate ;

	public Rental(Video video) {
		this.setVideo(video);
		rentDate = new Date() ;
	}
	
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public void returnVideo() {
		if ( video.isRented() ) {
			video.setRented(true) ;
			returnDate = new Date() ;
		}
	}

	// TODO : Remove unused Methods
	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public int getDaysRented() {
		long diff = 0 ;
		int daysRented ;
		if (!video.isRented()) { // returned Video
			diff = returnDate.getTime() - rentDate.getTime();
		} else { // not yet returned
			diff = new Date().getTime() - rentDate.getTime();
		}

		daysRented = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
		return daysRented ;
	}

	public int getDaysRentedLimit() {
		int limit = 0 ;
		int daysRented = getDaysRented() ;

		if ( daysRented <= 2)
			return 0 ;
		
		switch ( video.getVideoType() ) {
			case VHS: limit = 5 ; break ;
			case CD: limit = 3 ; break ;
			case DVD: limit = 2 ; break ;
		}
		return limit ;	
	}
}