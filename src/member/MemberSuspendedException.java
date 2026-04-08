package member;

public class MemberSuspendedException extends RuntimeException {
  public MemberSuspendedException(int id) {
    super("Medlem med id " + id + " är avstängd");
  }
}