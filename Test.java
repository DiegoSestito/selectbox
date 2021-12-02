import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test{

    public static void main(String[] params){

        var productVolumes = new ArrayList<Integer>(Arrays.asList(1, 5, 10, 4, 3));
        Collections.sort(productVolumes, Collections.reverseOrder());
        var boxesRepo = new ArrayList<Box>();
        boxesRepo.add(new Box(1, 2));
        boxesRepo.add(new Box(1, 3));
        boxesRepo.add(new Box(2, 5));
        boxesRepo.add(new Box(1, 7));
        boxesRepo.add(new Box(1, 15));
        
        var boxes = new ArrayList<Box>();

        for (Integer product : productVolumes) {
            Box box;

            if (boxes.size() == 0) {
                box = smallestNewPossibleBox(product, boxesRepo);
                if(box == null){
                    throw new IllegalStateException("Impossible solution");
                }
                boxes.add(box);
            }

            var index = productFitsAnyBox(product, boxes);
            if(index != -1){
                box = boxes.get(index);
                box.products.add(product);
                boxes.set(index, box);
            }else{
                index = 0;
                var foundUpgrade = false;
                box = null;
                do{
                    var currentBox = boxes.get(index);
                    box = fitUpgradedBox(product, currentBox, boxesRepo);

                    if(box != null){
                        foundUpgrade = true;
                    }else{
                        index++;
                    }

                }while(index < boxes.size() && foundUpgrade);

                if(box != null){
                    box.products.add(product);
                    boxes.set(index, box);
                }else{
                    box = smallestNewPossibleBox(product, boxesRepo);
                    box.products.add(product);
                    boxes.add(box);
                }
            }
        }

        boxes = boxes;

    }


    public static Box smallestNewPossibleBox(Integer volumeToAdd, List<Box> boxesRepo){

        for (Box box : boxesRepo) {
            if(volumeToAdd <= box.volume){
                return box;
            }
        }

        return null;
    }

    public static Integer productFitsAnyBox(Integer volumeToAdd, List<Box> boxes){
        var index = 0;
        for(Box box : boxes){
           if(productsFitsExistingBox(volumeToAdd, box)){
            return index;
           }
           index++;
        }

        return -1;
    }

     private static Box fitUpgradedBox(Integer volumeToAdd, Box box, List<Box> boxesRepo) {
         
        for (Box repoBox : boxesRepo) {
            var nextBox = new Box(repoBox.id, repoBox.volume, box.products);
            if(nextBox.remainingVolume() >= volumeToAdd){
                return nextBox;
            }
            
        }

        return null;
    }
    

   

    public static boolean productsFitsExistingBox(Integer volumeToAdd, Box box) {
        return box.remainingVolume() >= volumeToAdd;
    }
}