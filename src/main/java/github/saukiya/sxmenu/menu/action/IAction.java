package github.saukiya.sxmenu.menu.action;

public interface IAction {
    default void register() {
        if (this instanceof ICondition var) {
            ICondition.regExecution(var);
        }

        if (this instanceof IExecution var) {
            IExecution.regExecution(var);
        }
    }
}
