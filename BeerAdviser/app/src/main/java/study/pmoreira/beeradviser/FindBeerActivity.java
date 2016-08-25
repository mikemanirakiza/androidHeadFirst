package study.pmoreira.beeradviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class FindBeerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_beer);
    }

    public void onClickFindBeer(View view) {
        TextView brands = (TextView) findViewById(R.id.brands);
        Spinner color = (Spinner) findViewById(R.id.color);

        String beerColor = String.valueOf(color.getSelectedItem());
        List<String> brandsList = new BeerExpert().getBrands(beerColor);

        StringBuilder builder = new StringBuilder();
        for (String s : brandsList) {
            builder.append(s).append("\n");
        }

        brands.setText(builder);
    }
}
