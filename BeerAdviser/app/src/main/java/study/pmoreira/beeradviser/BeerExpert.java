package study.pmoreira.beeradviser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pedrofelipemm on 24/08/16.
 */
public class BeerExpert {

    List<String> getBrands(String color) {
        List<String> brands = new ArrayList<String>();
        if (color.equals("amber")) {
            brands.add("Jack Amber");
            brands.add("Red Moose");
        } else {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        return brands;
    }

}
