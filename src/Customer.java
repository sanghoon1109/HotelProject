public class Customer extends RoomDTO{
    private String name;
    private String number;
    private String currentTime;
    private String reservationDate;
    private String reservationNumber;
    private int roomId = 0;
    public Customer() {}

    public Customer(String name, String number, String currentTime, String reservationDate, String reservationNumber, int roomId) {
        this.name = name;
        this.number = number;
        this.currentTime = currentTime;
        this.reservationDate = reservationDate;
        this.reservationNumber = reservationNumber;
        this.roomId = roomId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
