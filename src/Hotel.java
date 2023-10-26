import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Hotel {

    private double hotelAssetsHeld = 1000.0; // 호텔 보유자산
    private Scanner scan = new Scanner(System.in);
    private double myMoney = 500.0;
    private int memberId = 1;
    ArrayList<RoomDTO> roomList = new ArrayList<>();
    HashMap<Integer, Customer> addReservation = new HashMap<>();

    public void roomInit() {
        roomList.add(new RoomDTO("Standard","기본적인 객실",100.0));
        roomList.add(new RoomDTO("Superior","고급스러움을 갖춘 기본 룸타입",200.0));
        roomList.add(new RoomDTO("Deluxe","룸사이즈가 크며 한층 더 높은 고급스러움",300.0));
        roomList.add(new RoomDTO("Suite","호텔의 최고급 객실이며 별도의 Bar와 고급 뷰",350.0));
    }

    public void view() {
        while(true) {
            System.out.println("현재 보유 금액 : " + myMoney);
            System.out.println("Hotel에 오신것을 환영합니다.");
            System.out.println("1.객실예약\n2.예약 조회\n3.예약 취소\n4.종료");
            System.out.print(": ");
            int choice = scan.nextInt();

            if(choice == 1) {
                reservation();
            }
            else if(choice == 2) {
                reservationCheck();
            }
            else if(choice == 3) {
                removeReservation();
            }
            else if (choice == 4) {
                break;
            } else if(choice == 0) {
                admin();
            }
            else {
                System.out.println("올바른 번호를 입력해주세요.");
                view();
            }
        }
    }

    public void reservation() {
        int num = 1;
        System.out.println("[ Reservation ]");
        for (RoomDTO room:roomList) {
            System.out.println(num++ + ". " + room.getRoom() + " | \t" + room.getDescription() + " | " + room.getRoomPrice());
        }
        int choice = scan.nextInt();
        if(myMoney < roomList.get(choice-1).getRoomPrice()) {
            System.out.println("소지금이 부족합니다. 다음에 이용해주세요\n");
            view();
        }
        System.out.println("[ " + roomList.get(choice-1).getRoom() + " ]");
        System.out.println("이 객실로 선택하시겠습니까? ");
        System.out.println("1. 확인\t2. 취소");
        choice = scan.nextInt();
        if(choice == 1) {
            System.out.println("현재 보유 금액 : " + myMoney);
            System.out.print("이름 : ");
            String myName = scan.next();
            System.out.println("전화번호는 정규 표현식으로 입력해주세요(ex. 010-1234-1234)");
            System.out.print("전화 번호 : ");
            String myNumber = scan.next();
            String pattern2 = "^\\d{3}-\\d{3,4}-\\d{4}$";
            if (!Pattern.matches(pattern2, myNumber)) {
                System.out.println("올바른 휴대전화 형식이 아닙니다.");
                reservation();
            }
            System.out.println("[ 예약 날짜 입력 ]");

            System.out.print("월 : ");
            String monthScan = scan.next();
            int checkMonth = Integer.parseInt(monthScan);
            if (checkMonth > 12 || checkMonth < 1) {
                System.out.println("1~12까지의 숫자를 입력해주세요.");
                view();
            }
            if (checkMonth < 10) {
                monthScan = "0" + monthScan;
            }
            System.out.print("일 : ");
            String dayScan = scan.next();
            int checkDay = Integer.parseInt(dayScan);
            if (checkDay > 31 || checkDay < 1) {
                System.out.println("1~31까지의 숫자를 입력해주세요.");
                view();
            }
            if (checkDay < 10) {
                dayScan = "0" + dayScan;
            }
            String finalScan = monthScan + "월" + dayScan + "일";
            int number = -1;
            for (String str : roomList.get(choice - 1).roomDate) {
                if (str.equals(finalScan)) {
                    System.out.println("이미 예약된 날짜입니다.");
                    number = 1;
                }
            }

            if (number == -1) {
                roomList.get(choice - 1).roomDate.add(finalScan);
                TimeZone timezone = TimeZone.getTimeZone("UTC");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                dateFormat.setTimeZone(timezone);
                String uuid = UUID.randomUUID().toString();
                addReservation.put(memberId, new Customer(myName, myNumber, dateFormat.format(new Date()), finalScan, uuid, choice-1));
                memberId++;
                myMoney -= roomList.get(choice - 1).getRoomPrice();
                hotelAssetsHeld += roomList.get(choice - 1).getRoomPrice();
                System.out.println("예약이 완료되었습니다.");
                System.out.println(myName + "님의 예약 번호는 : " + uuid + "입니다.");
            }

        }
        else if (choice == 2) {
            reservation();
        }

    }

    public void reservationCheck() {
        System.out.print("예약 번호를 입력해주세요 : ");
        String check = scan.next();
        int checkNum = -1;
        for (int key:addReservation.keySet()) {
            if(addReservation.get(key).getReservationNumber().equals(check)) {
                System.out.println("[ " + addReservation.get(key).getName() + "님의 예약 조회 ]");
                int getRoomId = addReservation.get(key).getRoomId();
                System.out.println("이름 : " + addReservation.get(key).getName());
                System.out.println("전화번호 : " + addReservation.get(key).getNumber());
                System.out.println("예약객실 : " + roomList.get(getRoomId).getRoom());
                System.out.println("가격 : " + roomList.get(getRoomId).getRoomPrice());
                System.out.println("예약날짜 : " + addReservation.get(key).getReservationDate());
                System.out.println("예약한 시간 : " + addReservation.get(key).getCurrentTime());
                checkNum = 1;
            }
        }
        if(checkNum == -1) System.out.println("예약번호를 확인해주세요\n");
    }

    public void removeReservation() {
        System.out.print("예약 번호를 입력해주세요 : ");
        String check = scan.next();
        int checkNum = -1;
        for (int key:addReservation.keySet()) {
            if(addReservation.get(key).getReservationNumber().equals(check)) {
                System.out.println(addReservation.get(key).getName() + "님의 예약이 취소되었습니다.");
                addReservation.remove(key);
            }
        }
        if(checkNum == -1) System.out.println("예약번호를 확인해주세요\n");
    }

    public void admin() {
        while(true) {
            System.out.println("[ admin ]");
            System.out.println("현재 호텔 보유자산 : " + hotelAssetsHeld);
            System.out.println("1.예약 목록조회\n2.보유자금 늘리기\n3.객실 추가\n4.객실 삭제\n5.돌아가기");
            System.out.print(": ");
            int choice = scan.nextInt();

            if(choice == 1) {
                System.out.println("[ 예약 목록조회 ]");
                int cnt = 1;
                for (int key:addReservation.keySet()) {
                    int getRoomId = addReservation.get(key).getRoomId();
                    System.out.println(cnt++ + ". " + addReservation.get(key).getName() + " | " + addReservation.get(key).getNumber() + " | " + roomList.get(getRoomId).getRoom() + " | " + addReservation.get(key).getReservationDate());
                }
            }
            else if(choice == 2) {
                System.out.println("보유자금이 늘어났습니다\n");
                myMoney += 100.0;
            }
            else if(choice == 3) {
                System.out.print("객실 이름 : ");
                String roomName = scan.next();
                System.out.print("객실 설명 : ");
                String roomDescription = scan.next();
                System.out.print("객실 가격 : ");
                int roomPrice = scan.nextInt();
                double changeRoomPrice = Double.valueOf(roomPrice);

                roomList.add(new RoomDTO(roomName,roomDescription,changeRoomPrice));
            }
            else if(choice == 4) {
                int num = 1;
                System.out.println("[ 객실 삭제 ]");
                for (RoomDTO room:roomList) {
                    System.out.println(num++ + ". " + room.getRoom());
                }
                int removeRoom = scan.nextInt();
                System.out.println("[ " + roomList.get(removeRoom-1).getRoom() + " ]");
                System.out.println("이 객실로 선택하시겠습니까? ");
                System.out.println("1. 확인\t2. 취소");
                choice = scan.nextInt();
                if(choice == 1) {
                    System.out.println(roomList.get(removeRoom-1).getRoom() + " 객실이 삭제되었습니다.");
                    roomList.remove(removeRoom-1);
                }
                else if(choice == 2) {
                    break;
                }
                else {
                    break;
                }
            }
            else if(choice == 5) {
                break;
            }
            else {
                System.out.println("올바른 번호를 입력해주세요.");
                break;
            }
        }
    }

}
