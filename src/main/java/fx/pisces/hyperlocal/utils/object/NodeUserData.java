package fx.pisces.hyperlocal.utils.object;

public class NodeUserData {
    private boolean isActiveDraggable;
    public NodeUserData(boolean isActiveDraggable) {
        this.isActiveDraggable = isActiveDraggable;
    }
    public void setActiveDraggable(boolean draggable) {
        isActiveDraggable = draggable;
    }
    public boolean isActiveDraggable() {
        return isActiveDraggable;
    }

}
