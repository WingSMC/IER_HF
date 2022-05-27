package mining;

public class BlockType {
    boolean checked;
    boolean iscave;
    boolean iswall;

    public BlockType() {
        // was the cave generation run on the block
        checked = false;
        // is it a cave (true) or obstacle (false)
        iscave = false;
        // is it an outer block
        iswall = false;
    }

    public boolean isChecked() { return checked; }

    public boolean isIscave() { return iscave; }

    public boolean isIswall() { return iswall; }

    public void setIscave(boolean iscave) { this.iscave = iscave; }

    public void setChecked(boolean checked) { this.checked = checked; }

    public void setIswall(boolean iswall) { this.iswall = iswall; }
}
