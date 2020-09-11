package ComponenteConquista;

import java.util.*;

public class Points extends Achievement{

    public Points(String name, float valor) {
        super(name);
        this.valor = valor;
    }

    private float valor;

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void adicionar(String user, Map<String, List<Achievement>> achievementMap){
        List<Achievement> achievementList;
        if(!achievementMap.keySet().contains(user)){
            achievementList = new ArrayList<>();
            achievementList.add(this);
            achievementMap.put(user,achievementList);
        }else{
            achievementList = achievementMap.get(user);
            Optional<Achievement> achievementOptional =
                    achievementList
                    .stream()
                    .filter(a -> a instanceof Points && a.getName().equals(this.getName())).findFirst();
            if(achievementOptional.isPresent()){
                Points p = (Points) achievementOptional.get();
                p.setValor(p.getValor() + this.getValor());
            }else{
                achievementList.add(this);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Points points = (Points) o;
        return Float.compare(points.getValor(), getValor()) == 0
                && Objects.equals(getName(), points.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValor());
    }
}
