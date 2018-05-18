package safety.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "instructions", schema = "womansafety", catalog = "")
public class InstructionsEntity {
    private int idInstruction;
    private String title;
    private String instruction;
    private int rangeStart;
    private int rangeEnd;

    @Id
    @Column(name = "id_instruction")
    public int getIdInstruction() {
        return idInstruction;
    }

    public void setIdInstruction(int idInstruction) {
        this.idInstruction = idInstruction;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "instruction")
    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Basic
    @Column(name = "range_start")
    public int getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    @Basic
    @Column(name = "range_end")
    public int getRangeEnd() {
        return rangeEnd;
    }

    public void setRangeEnd(int rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        InstructionsEntity that = (InstructionsEntity) object;
        return idInstruction == that.idInstruction &&
                rangeStart == that.rangeStart &&
                rangeEnd == that.rangeEnd &&
                Objects.equals(title, that.title) &&
                Objects.equals(instruction, that.instruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInstruction, title, instruction, rangeStart, rangeEnd);
    }
}