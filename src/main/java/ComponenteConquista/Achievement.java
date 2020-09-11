package ComponenteConquista;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Achievement {

    private String name;

    public Achievement(){
    }

    public Achievement(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void adicionar(String user, Map<String, List<Achievement>> achievementMap);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
