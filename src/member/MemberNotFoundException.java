package member;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(int id) {
        super("Medlem med id " + id + " hittades inte");
    }
}