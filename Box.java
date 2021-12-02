import java.util.ArrayList;
import java.util.List;

public class Box {
    public Integer id;
    public Integer volume;
    public List<Integer> products;

    public Box(Integer id, Integer volume) {
        this.id = id;
        this.volume = volume;
        this.products = new ArrayList<Integer>();
    }

    public Box(Integer id, Integer volume, List<Integer> products) {
        this.id = id;
        this.volume = volume;
        this.products = products;
    }

    public Integer remainingVolume(){
        Integer sumProduct = 0;
        for (var p : products) {
            sumProduct += p;
        }

        return volume - sumProduct;
    }
}
