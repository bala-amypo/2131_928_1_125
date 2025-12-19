@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description; // ✅ ADD THIS

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
