package command;

public interface Command
{
    // 명령 실행
    void execute();

    // 되돌리기
    void undo();
}
