import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carID;
    private String carBrand;
    private String carModel;
    private int carPriceperDay;
    private boolean isAvailable;

    public Car(String carID, String carBrand, String carModel, int carBasePrice){
        this.carID = carID;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carPriceperDay = carBasePrice;
        isAvailable = true;
    }

    public String getCarID(){
        return carID;
    }

    public String getCarBrand(){
        return carBrand;
    }

    public String getCarModel(){
        return carModel;
    }

    public int calculatePrice(int rentalDays){
        return carPriceperDay*rentalDays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }
    public void rent(){
        isAvailable = false;
    }
    public void returnRent(){
        isAvailable = true;
    }
}

class Customer{
    private String adhaarID;
    private String customerName;
    private String customerNumber;

    public Customer(String adhaarID, String customerName, String customerNumber){
        this.adhaarID = adhaarID;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
    }

    public String getAdhaarID(){
        return adhaarID;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerNumber(){
        return customerNumber;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent!!");
        }
    }

    public void returnCar(Car car){
        Rental rentaltoRemove = null;
        for (Rental rental: rentals) {
            if(rental.getCar()==car){
                rentaltoRemove = rental;
                break;
            }
        }
        car.returnRent();
        if(rentaltoRemove != null){
            rentals.remove(rentaltoRemove);
            System.out.println("Car Returned Successfully!!");
        } else {
            System.out.println("Car was not Rented!!");
        }
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("\n====****// CAR RENTAL SYSTEM //****====");
            System.out.println("1. Rent Car");
            System.out.println("2. Return Car");
            System.out.println("3. Exit");
            System.out.println("Enter Your Choice!!");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1){
                System.out.println("==* Rent a Car *==");
                System.out.println("Please enter your name:");
                String customerName = sc.nextLine();
                System.out.println("Please enter your mobile number");
                String customerNumber = sc.nextLine();
                System.out.println("\nHello "+customerName+" here are the Available Cars:\n");
                for (Car car: cars) {
                    if(car.isAvailable()){
                        System.out.println(car.getCarID()+" "+car.getCarBrand()+" "+car.getCarModel());
                    }
                }
                System.out.println("\nEnter the car_ID you want to rent: ");
                String carID = sc.nextLine();
                System.out.println("\nEnter the number of days for Rental!: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer customer = new Customer("CUS" + (customers.size() + 1), customerName, customerNumber);

                Car selectedCar = null;
                for (Car car: cars) {
                    if(car.getCarID().equals(carID) && car.isAvailable()){
                        selectedCar = car;
                    }
                    break;
                }

                if(selectedCar != null){
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n==* Rental Information *==");
                    System.out.println("CustomerID: " + customer.getAdhaarID());
                    System.out.println("Customer Name: " + customer.getCustomerName());
                    System.out.println("Mobile Number: " + customer.getCustomerNumber());
                    System.out.println("Car: " + selectedCar.getCarBrand() + " " + selectedCar.getCarModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: ₹%.2f%n", totalPrice);
                    System.out.println("Confirm Rental (Y/N): ");
                    String confirms = sc.nextLine();

                    if(confirms.equalsIgnoreCase("Y")){
                        rentCar(selectedCar, customer, rentalDays);
                        System.out.println("\nHooray!!\n Car rented Successfully!!");
                    } else {
                        System.out.println("\n Rental canceled");
                    }
                } else {
                    System.out.println("\nInvalid Selection or Car Not Available For Rent");
                }
            } else if (choice == 2) {
                System.out.println("\n==* Return Car *==");
                System.out.println("Please enter the Car_ID for return");
                String carID = sc.nextLine();

                Car carToReturn = null;
                for (Car car: cars) {
                    if (car.getCarID().equals(carID) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental: rentals) {
                        if(rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("\nCar returned Successfully by: " + customer.getCustomerName());
                    } else {
                        System.out.println("Car was not rented (or) Rental data is missing");
                    }
                } else {
                    System.out.println("Invalid Car ID (or) Car is not rented");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid Input. Please enter a valid choice");
            }
        }
        System.out.println("THANK YOU FOR USING THE CAR RENTAL SYSTEM");
    }
}

public class Main {
    public static void main(String args[]) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("MS001", "Maruti Suzuki", "Swift Dzire", 2500);
        Car car2 = new Car("MS002", "Maruti Suzuki", "Alto", 1800);
        Car car3 = new Car("HY001", "Hyundai", "i10", 2000);
        Car car4 = new Car("MH001", "Mahindra", "Thar", 3000);
        Car car5 = new Car("TY001", "Toyota", "Etios", 2000);
        Car car6 = new Car("TY003", "Toyota", "Inova", 3000);

        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.addCar(car4);
        rentalSystem.addCar(car5);
        rentalSystem.addCar(car6);

        rentalSystem.menu();
    }
}
