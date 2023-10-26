import java.util.ArrayList;

public class RoomDTO {
    private String room; // 객실

    private String description; // 객실 설명
    private double roomPrice = 0;

    public ArrayList<String> roomDate = new ArrayList<>(); // 객실 날짜
    public RoomDTO() {}

    public RoomDTO (String room, String description, double roomPrice) {
        this.room = room;
        this.description = description;
        this.roomPrice = roomPrice;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
