import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    private List<String> ingredients;

    public Ingredients(ArrayList<String> ingredients){
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
