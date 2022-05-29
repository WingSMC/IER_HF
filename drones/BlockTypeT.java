package drones;

public class BlockTypeT {
    boolean checked;
    boolean iscave;
    boolean iswall;

    public BlockTypeT() {
        checked = false;
        iscave = false;
        iswall = false;
    }

    public boolean isChecked() { return checked; }

    public boolean isIscave() { return iscave; }

    public boolean isIswall() { return iswall; }

    public void setIscave(boolean iscave) { this.iscave = iscave; }

    public void setChecked(boolean checked) { this.checked = checked; }

    public void setIswall(boolean iswall) { this.iswall = iswall; }
}
