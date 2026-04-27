package com.example.classdiagramsimulation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

abstract class Account {
    protected int id;
    protected String name;

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public abstract void displayInfo();
}

interface Interactable {
    void startSession();
    void endSession();
}

class User extends Account {
    private String email;
    private String passwordHash;
    private LocalDateTime createdAt;

    public User(int id, String name, String email, String passwordHash) {
        super(id, name);
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = LocalDateTime.now();
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public void displayInfo() {
        System.out.println("  [User] ID: " + id + " | Nama: " + name + " | Email: " + email);
    }
}

class Talent extends Account {
    private String bio;
    private String profilePictureUrl;
    private double rating;

    public Talent(int id, String name, String bio, String profilePictureUrl, double rating) {
        super(id, name);
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
        this.rating = rating;
    }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String url) { this.profilePictureUrl = url; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public void displayInfo() {
        System.out.println("  [Talent] ID: " + id + " | Nama: " + name + " | Rating: " + rating + " | Bio: " + bio);
    }
}

class Service {
    private int serviceId;
    private Talent talent;
    private String serviceType;
    private int durationMinutes;
    private double price;

    public Service(int serviceId, Talent talent, String serviceType, int durationMinutes, double price) {
        this.serviceId = serviceId;
        this.talent = talent;
        this.serviceType = serviceType;
        this.durationMinutes = durationMinutes;
        this.price = price;
    }

    public int getServiceId() { return serviceId; }
    public Talent getTalent() { return talent; }
    public String getServiceType() { return serviceType; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getPrice() { return price; }
}

class Schedule {
    private int scheduleId;
    private Talent talent;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked;

    public Schedule(int scheduleId, Talent talent, LocalDateTime startTime, LocalDateTime endTime) {
        this.scheduleId = scheduleId;
        this.talent = talent;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = false;
    }

    public int getScheduleId() { return scheduleId; }
    public Talent getTalent() { return talent; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public boolean getIsBooked() { return isBooked; }

    public void bookSchedule() {
        this.isBooked = true;
        System.out.println("  Schedule #" + scheduleId + " berhasil di-booking.");
    }
}

class Order {
    private int orderId;
    private User user;
    private Service service;
    private Schedule schedule;
    private double totalPrice;
    private String status;
    private String offlineLocation;

    public Order(int orderId, User user, Service service, Schedule schedule, String offlineLocation) {
        this.orderId = orderId;
        this.user = user;
        this.service = service;
        this.schedule = schedule;
        this.totalPrice = service.getPrice();
        this.status = "Active";
        this.offlineLocation = offlineLocation;

        this.schedule.bookSchedule();
    }

    public int getOrderId() { return orderId; }
    public User getUser() { return user; }
    public Service getService() { return service; }
    public Schedule getSchedule() { return schedule; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public String getOfflineLocation() { return offlineLocation; }

    public void setStatus(String status) { this.status = status; }
    public void setOfflineLocation(String offlineLocation) { this.offlineLocation = offlineLocation; }

    public void completeOrder() {
        this.status = "Completed";
        System.out.println("  Order #" + orderId + " telah selesai.");
    }

    public void displayInfo() {
        System.out.println("  [Order] ID: " + orderId
                + " | User: " + user.getName()
                + " | Layanan: " + service.getServiceType()
                + " | Harga: Rp" + totalPrice
                + " | Status: " + status);
    }
}

class InteractionSession implements Interactable {
    private int sessionId;
    private Order order;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private boolean isReadonly;

    public InteractionSession(int sessionId, Order order) {
        this.sessionId = sessionId;
        this.order = order;
        this.isReadonly = false;
    }

    public int getSessionId() { return sessionId; }
    public Order getOrder() { return order; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public LocalDateTime getEndedAt() { return endedAt; }
    public boolean getIsReadonly() { return isReadonly; }

    @Override
    public void startSession() {
        this.startedAt = LocalDateTime.now();
        System.out.println("  >>> Sesi #" + sessionId + " dimulai pada: " + this.startedAt);
    }

    @Override
    public void endSession() {
        this.endedAt = LocalDateTime.now();
        this.isReadonly = true;
        this.order.completeOrder();
        System.out.println("  >>> Sesi #" + sessionId + " berakhir. Chat sekarang Read-Only: " + this.isReadonly);
    }
}

public class Main {

    static List<Talent> talentDB = new ArrayList<>();
    static List<User>   userDB   = new ArrayList<>();
    static List<Order>  orderDB  = new ArrayList<>();

    static Talent findTalentById(int id) {
        for (Talent t : talentDB) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    static Order findOrderById(int id) {
        for (Order o : orderDB) {
            if (o.getOrderId() == id) return o;
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║   SIMULASI APLIKASI PASANGAN DIGITAL     ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│  1. CREATE – Menambahkan Data           │");
        System.out.println("└─────────────────────────────────────────┘");

        Talent talent1 = new Talent(1, "Huang Luna",  "Hai! Aku Luna, aku suka ngobrol!",    "url_luna.jpg",  4.85);
        Talent talent2 = new Talent(2, "Lee Yuna",  "Partner ngobrol asik, hobi nonton film & kulineran",  "url_lee.jpg", 4.72);
        talentDB.add(talent1);
        talentDB.add(talent2);
        System.out.println("2 Talent berhasil ditambahkan.");

        User user1 = new User(1, "Inhyuk", "inhyuk@email.com", "hash_001");
        userDB.add(user1);
        System.out.println("1 User berhasil ditambahkan.\n");

        Service  service1  = new Service(1, talent1, "Chat", 60, 75000);
        Schedule schedule1 = new Schedule(
                1, talent1,
                LocalDateTime.of(2025, 2, 1, 10, 0),
                LocalDateTime.of(2025, 2, 1, 11, 0)
        );

        System.out.println("Membuat Order baru...");
        Order order1 = new Order(101, user1, service1, schedule1, null);
        orderDB.add(order1);
        System.out.println("Order berhasil dibuat!\n");

        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│  2. READ – Membaca Semua Data           │");
        System.out.println("└─────────────────────────────────────────┘");

        System.out.println("Daftar Talent:");
        for (Talent t : talentDB) t.displayInfo();

        System.out.println("\nDaftar User:");
        for (User u : userDB) u.displayInfo();

        System.out.println("\nDaftar Order:");
        for (Order o : orderDB) o.displayInfo();

        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│  3. UPDATE – Memperbarui Data           │");
        System.out.println("└─────────────────────────────────────────┘");

        Talent talentToUpdate = findTalentById(1);
        if (talentToUpdate != null) {
            System.out.println("Sebelum update -> Bio: " + talentToUpdate.getBio());
            talentToUpdate.setBio("Hai! Aku Luna, suka ngobrol dan dengerin cerita kamu");
            System.out.println("Sesudah update -> Bio: " + talentToUpdate.getBio());
        }

        Order orderToUpdate = findOrderById(101);
        if (orderToUpdate != null) {
            System.out.println("\nSebelum update -> Status Order #101: " + orderToUpdate.getStatus());
            orderToUpdate.setStatus("On Progress");
            System.out.println("Sesudah update -> Status Order #101: " + orderToUpdate.getStatus());
        }

        System.out.println("\n┌──────────────────────────────────────────┐");
        System.out.println("│  DEMO – InteractionSession (Interactable)│");
        System.out.println("└──────────────────────────────────────────┘");

        InteractionSession session1 = new InteractionSession(1, order1);
        session1.startSession();
        System.out.println("  ... User dan Talent sedang berinteraksi ...");
        session1.endSession();

        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│  4. DELETE – Menghapus Data             │");
        System.out.println("└─────────────────────────────────────────┘");

        int deleteId = 2;
        Talent talentToDelete = findTalentById(deleteId);
        if (talentToDelete != null) {
            talentDB.remove(talentToDelete);
            System.out.println("Talent ID " + deleteId + " (" + talentToDelete.getName() + ") berhasil dihapus.");
        }
        System.out.println("Jumlah Talent sekarang: " + talentDB.size());

        int deleteOrderId = 101;
        Order orderToDelete = findOrderById(deleteOrderId);
        if (orderToDelete != null) {
            orderDB.remove(orderToDelete);
            System.out.println("Order #" + deleteOrderId + " berhasil dihapus.");
        }
        System.out.println("Jumlah Order sekarang : " + orderDB.size());

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║         SIMULASI SELESAI                 ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }
}