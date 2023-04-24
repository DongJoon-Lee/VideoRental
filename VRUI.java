import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VRUI {
	private static Scanner scanner = new Scanner(System.in) ;

	private List<Customer> customers = new ArrayList<Customer>() ;
	
	private List<Video> videos = new ArrayList<Video>() ;
	
	public static void main(String[] args) {
		VRUI ui = new VRUI() ;

		boolean quit = false ;
		while ( ! quit ) {
			int command = ui.showCommand() ;
			switch ( command ) {
				case 0: quit = true ; break ;
				case 1: ui.listCustomers() ; break ;
				case 2: ui.listVideos() ; break ;
				case 3: ui.registerCustomer() ; break ;
				case 4: ui.registerVideo(); ; break ;
				case 5: ui.rentVideo() ; break ;
				case 6: ui.returnVideo() ; break ;
				case 7: ui.getCustomerReport() ; break;
				case 8: ui.clearRentals() ; break ;
				case -1: ui.init() ; break ;
				default: break ;
			}
		}
		System.out.println("Bye");
	}

	public int showCommand() {
		System.out.println("\nSelect a command !");
		System.out.println("\t 0. Quit");
		System.out.println("\t 1. List customers");
		System.out.println("\t 2. List videos");
		System.out.println("\t 3. Register customer");
		System.out.println("\t 4. Register video");
		System.out.println("\t 5. Rent video");
		System.out.println("\t 6. Return video");
		System.out.println("\t 7. Show customer report");
		System.out.println("\t 8. Show customer and clear rentals");

		int command = scanner.nextInt() ;

		return command ;
	}

	private void init() {
		Customer james = new Customer("James") ;
		Customer brown = new Customer("Brown") ;
		customers.add(james) ;
		customers.add(brown) ;

		Video v1 = new Video("v1", Video.VideoType.CD, Video.PriceCode.REGULAR, new Date()) ;
		Video v2 = new Video("v2", Video.VideoType.DVD, Video.PriceCode.NEW_RELEASE, new Date()) ;
		videos.add(v1) ;
		videos.add(v2) ;

		Rental r1 = new Rental(v1) ;
		Rental r2 = new Rental(v2) ;

		james.addRental(r1) ;
		james.addRental(r2) ;
	}



	public void listCustomers() {
		System.out.println("List of customers");
		for ( Customer customer: customers ) {
			System.out.println("Name: " + customer.getName() +
				"\tRentals: " + customer.getRentals().size()) ;
			for ( Rental rental: customer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode() + "\n") ;
			}
		}
		System.out.println("End of list");
	}


	public void listVideos() {
		System.out.println("List of videos");
		
		for ( Video video: videos ) {
			System.out.println("Price code: " + video.getPriceCode() +"\tTitle: " + video.getTitle()) ;
		}
		System.out.println("End of list");
	}

	public void registerCustomer() {
		System.out.println("Enter customer name: ") ;
		String name = scanner.next();
		Customer customer = new Customer(name) ;
		customers.add(customer) ;
	}

	private void registerVideo() {
		System.out.println("Enter video title to register: ") ;
		String title = scanner.next() ;
		Video.VideoType videoType = Video.VideoType.VHS;
		Video.PriceCode priceCode= Video.PriceCode.REGULAR;

		System.out.println("Enter video type( 1 for VHS, 2 for CD, 3 for DVD ):") ;
		switch (scanner.nextInt()) {
			case 1: videoType = Video.VideoType.VHS; break;
			case 2: videoType = Video.VideoType.CD; break;
			case 3: videoType = Video.VideoType.DVD; break;
		}

		System.out.println("Enter price code( 1 for Regular, 2 for New Release ):") ;
		switch (scanner.nextInt()) {
			case 1: priceCode = Video.PriceCode.REGULAR; break;
			case 2: priceCode = Video.PriceCode.NEW_RELEASE; break;
		}

		Video video = new Video(title, videoType, priceCode, new Date()) ;
		videos.add(video) ;
	}

	public void rentVideo() {
		Customer foundCustomer = getCustomer();

		if ( foundCustomer == null ) return ;

		System.out.println("Enter video title to rent: ") ;
		String videoTitle = scanner.next() ;

		Video foundVideo = null ;
		for ( Video video: videos ) {
			if ( video.getTitle().equals(videoTitle) && video.isRented() == false ) {
				foundVideo = video ;
				break ;
			}
		}

		if ( foundVideo == null ) return ;

		Rental rental = new Rental(foundVideo) ;
		foundCustomer.addRental(rental);
	}

	public void returnVideo() {
		Customer foundCustomer = getCustomer();
		if (foundCustomer == null) return;

		System.out.println("Enter video title to return: ");
		String videoTitle = scanner.next();

		List<Rental> customerRentals = foundCustomer.getRentals();
		for ( Rental rental : customerRentals ) {
			if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
				rental.returnVideo();
				rental.getVideo().setRented(false);
				break;
			}
		}
	}

	public void getCustomerReport() {
		Customer foundCustomer = getCustomer();

		if ( foundCustomer == null ) {
			System.out.println("No customer found") ;
		} else {
			String result = foundCustomer.getReport() ;
			System.out.println(result);
		}
	}

	public void clearRentals() {
		Customer foundCustomer = getCustomer();

		if ( foundCustomer == null ) {
			System.out.println("No customer found") ;
		} else {
			System.out.println("Name: " + foundCustomer.getName() +
				"\tRentals: " + foundCustomer.getRentals().size()) ;
			for ( Rental rental: foundCustomer.getRentals() ) {
				System.out.print("\tTitle: " + rental.getVideo().getTitle() + " ") ;
				System.out.print("\tPrice Code: " + rental.getVideo().getPriceCode()) ;
			}

			List<Rental> rentals = new ArrayList<Rental>() ;
			foundCustomer.setRentals(rentals);
		}
	}

	private Customer getCustomer() {
		System.out.println("Enter customer name: ");
		String customerName = scanner.next();

		Customer foundCustomer = null;
		for ( Customer customer : customers ) {
			if (customer.getName().equals(customerName)) {
				foundCustomer = customer;
				break;
			}
		}
		return foundCustomer;
	}

}
